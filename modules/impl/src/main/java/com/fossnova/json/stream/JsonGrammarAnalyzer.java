/*
 * Copyright (c) 2012-2020, FOSS Nova Software foundation (FNSF),
 * and individual contributors as indicated by the @author tags.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.fossnova.json.stream;

import org.fossnova.json.stream.JsonEvent;
import org.fossnova.json.stream.JsonException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class JsonGrammarAnalyzer {

    private static final byte OBJECT_START = 1;
    private static final byte ARRAY_START = 2;
    private static final byte STRING = 4;
    private static final byte COLON = 8;
    private final Deque< Set< String >> jsonKeys = new ArrayDeque<>();
    private byte[] stack = new byte[ 8 ];
    private boolean canWriteColon;
    private boolean canWriteComma;
    private int index;
    JsonEvent currentEvent;
    boolean finished;

    JsonGrammarAnalyzer() {
    }

    boolean isColonExpected() {
        return canWriteColon;
    }

    boolean isCommaExpected() {
        return canWriteComma;
    }

    void putObjectEnd() throws JsonException {
        // preconditions
        if ( finished || index == 0 || stack[ index - 1 ] != OBJECT_START || currentEvent == null ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.OBJECT_END;
        index--;
        if ( index > 0 ) {
            if ( stack[ index - 1 ] == COLON ) {
                index -= 2;
                canWriteComma = true;
            } else if ( stack[ index - 1 ] == ARRAY_START ) {
                canWriteComma = true;
            }
        }
        jsonKeys.removeLast();
        if ( index == 0 ) {
            finished = true;
        }
    }

    void putArrayEnd() throws JsonException {
        // preconditions
        if ( finished || index == 0 || stack[ index - 1 ] != ARRAY_START || currentEvent == null ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.ARRAY_END;
        index--;
        if ( index > 0 ) {
            if ( stack[ index - 1 ] == COLON ) {
                index -= 2;
                canWriteComma = true;
            } else if ( stack[ index - 1 ] == ARRAY_START ) {
                canWriteComma = true;
            }
        } else {
            finished = true;
        }
    }

    void putNumber() throws JsonException {
        // preconditions
        if ( finished || canWriteComma || index != 0 && ( stack[ index - 1 ] & ( ARRAY_START | COLON ) ) == 0 ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.NUMBER;
        if ( index == 0 ) {
            finished = true;
            return;
        }
        if ( stack[ index - 1 ] == COLON ) {
            index -= 2;
        }
        canWriteComma = true;
    }

    void putBoolean() throws JsonException {
        // preconditions
        if ( finished || canWriteComma || index != 0 && ( stack[ index - 1 ] & ( ARRAY_START | COLON ) ) == 0 ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.BOOLEAN;
        if ( index == 0 ) {
            finished = true;
            return;
        }
        if ( stack[ index - 1 ] == COLON ) {
            index -= 2;
        }
        canWriteComma = true;
    }

    void putNull() throws JsonException {
        // preconditions
        if ( finished || canWriteComma || index != 0 && ( stack[ index - 1 ] & ( ARRAY_START | COLON ) ) == 0 ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.NULL;
        if ( index == 0 ) {
            finished = true;
            return;
        }
        if ( stack[ index - 1 ] == COLON ) {
            index -= 2;
        }
        canWriteComma = true;
    }

    void putString() throws JsonException {
        // preconditions
        if ( finished || canWriteComma || index != 0 && ( stack[ index - 1 ] & ( OBJECT_START | ARRAY_START | COLON ) ) == 0 ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.STRING;
        if ( index == 0 ) {
            finished = true;
            return;
        }
        if ( stack[ index - 1 ] == OBJECT_START ) {
            if ( index == stack.length ) doubleStack();
            stack[ index++ ] = STRING;
            canWriteColon = true;
            return;
        }
        if ( stack[ index - 1 ] == COLON ) {
            index -= 2;
        }
        canWriteComma = true;
    }

    void putKey( final String jsonKey ) throws JsonException {
        if ( stack[ index - 1 ] == STRING ) {
            if ( !jsonKeys.getLast().add( jsonKey ) ) {
                throw newJsonException( "JSON keys have to be unique. The key '" + jsonKey + "' already exists" );
            }
        }
    }

    void putObjectStart() throws JsonException {
        // preconditions
        if ( finished || canWriteComma || index != 0 && ( stack[ index - 1 ] & ( ARRAY_START | COLON ) ) == 0 ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.OBJECT_START;
        if ( index == stack.length ) doubleStack();
        stack[ index++ ] = OBJECT_START;
        jsonKeys.addLast( new HashSet<>() );
    }

    void putArrayStart() throws JsonException {
        // preconditions
        if ( finished || canWriteComma || index != 0 && ( stack[ index - 1 ] & ( ARRAY_START | COLON ) ) == 0 ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = JsonEvent.ARRAY_START;
        if ( index == stack.length ) doubleStack();
        stack[ index++ ] = ARRAY_START;
    }

    void putColon() throws JsonException {
        // preconditions
        if ( finished || !canWriteColon ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = null;
        if ( index == stack.length ) doubleStack();
        stack[ index++ ] = COLON;
        canWriteColon = false;
    }

    void putComma() throws JsonException {
        // preconditions
        if ( finished || !canWriteComma ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        currentEvent = null;
        canWriteComma = false;
    }

    private String getExpectingTokensMessage() {
        if ( index == 0 ) {
            if ( !finished ) {
                return "Expecting " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START;
            } else {
                return "JSON processing finished";
            }
        }
        if ( stack[ index - 1 ] == OBJECT_START ) {
            if ( !canWriteComma ) {
                return "Expecting " + JsonConstants.OBJECT_END + " " + JsonConstants.STRING;
            } else {
                return "Expecting " + JsonConstants.COMMA + " " + JsonConstants.OBJECT_END;
            }
        }
        if ( stack[ index - 1 ] == ARRAY_START ) {
            if ( !canWriteComma ) {
                if ( currentEvent != null ) {
                    return "Expecting " + JsonConstants.ARRAY_END + " " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START + " " + JsonConstants.STRING + " "
                        + JsonConstants.NUMBER + " " + JsonConstants.TRUE + " " + JsonConstants.FALSE + " " + JsonConstants.NULL;
                } else {
                    return "Expecting " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START + " " + JsonConstants.STRING + " " + JsonConstants.NUMBER + " "
                        + JsonConstants.TRUE + " " + JsonConstants.FALSE + " " + JsonConstants.NULL;
                }
            } else {
                return "Expecting " + JsonConstants.COMMA + " " + JsonConstants.ARRAY_END;
            }
        }
        if ( stack[ index - 1 ] == COLON ) {
            return "Expecting " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START + " " + JsonConstants.STRING + " " + JsonConstants.NUMBER + " " + JsonConstants.TRUE
                + " " + JsonConstants.FALSE + " " + JsonConstants.NULL;
        }
        if ( stack[ index - 1 ] == STRING ) {
            return "Expecting " + JsonConstants.COLON;
        }
        throw new IllegalStateException();
    }

    private void doubleStack() {
        final byte[] oldData = stack;
        stack = new byte[ oldData.length * 2 ];
        System.arraycopy( oldData, 0, stack, 0, oldData.length );
    }

    void close( final boolean write ) throws JsonException {
        if ( !finished ) {
            throw newJsonException( "Detected attempt to " + ( write ? "write" : "read" ) + " uncomplete JSON stream" );
        }
        currentEvent = null;
    }

    JsonException newJsonException( final String s ) {
        finished = true;
        currentEvent = null;
        return new JsonException( s );
    }

}
