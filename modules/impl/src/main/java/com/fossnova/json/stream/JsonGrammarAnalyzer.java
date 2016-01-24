/*
 * Copyright (c) 2012, FOSS Nova Software foundation (FNSF),
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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import org.fossnova.json.stream.JsonEvent;
import org.fossnova.json.stream.JsonException;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonGrammarAnalyzer {

    static final byte OBJECT_START = 1;
    static final byte OBJECT_END = 2;
    static final byte ARRAY_START = 3;
    static final byte ARRAY_END = 4;
    static final byte STRING = 5;
    static final byte NUMBER = 6;
    static final byte BOOLEAN = 7;
    static final byte NULL = 8;
    static final byte COLON = 9;
    static final byte COMMA = 10;

    private boolean canWriteComma;
    private JsonEvent currentEvent;
    private final Deque< Set< String >> jsonKeys = new ArrayDeque< Set< String > >();
    private byte[] stack = new byte[ 16 ];
    private int index;
    boolean finished;

    JsonGrammarAnalyzer() {
    }

    void push( final byte event ) throws JsonException {
        ensureCanContinue();
        if ( event == OBJECT_END ) {
            putObjectEnd();
            currentEvent = JsonEvent.OBJECT_END;
        } else if ( event == ARRAY_END ) {
            putArrayEnd();
            currentEvent = JsonEvent.ARRAY_END;
        } else if ( event == NUMBER ) {
            putValue();
            currentEvent = JsonEvent.NUMBER;
        } else if ( event == NULL ) {
            putValue();
            currentEvent = JsonEvent.NULL;
        } else if ( event == BOOLEAN ) {
            putValue();
            currentEvent = JsonEvent.BOOLEAN;
        } else if ( event == STRING ) {
            putString();
            currentEvent = JsonEvent.STRING;
        } else if ( event == OBJECT_START ) {
            putObjectStart();
            currentEvent = JsonEvent.OBJECT_START;
        } else if ( event == ARRAY_START ) {
            putArrayStart();
            currentEvent = JsonEvent.ARRAY_START;
        } else if ( event == COLON ) {
            putColon();
            currentEvent = null;
        } else if ( event == COMMA ) {
            putComma();
            currentEvent = null;
        } else {
            throw new IllegalStateException();
        }
    }

    void pushString( final String jsonKey ) throws JsonException {
        if ( index > 0 && stack[ index - 1 ] == STRING ) {
            final boolean containsKey = !jsonKeys.getLast().add( jsonKey );
            if ( containsKey ) {
                throw newJsonException( "JSON keys have to be unique. The key '" + jsonKey + "' already exists" );
            }
        }
    }

    JsonEvent getCurrentEvent() {
        return currentEvent;
    }

    boolean isColonExpected() {
        return index >= 2 && stack[ index - 2 ] == OBJECT_START && stack[ index - 1 ] == STRING;
    }

    boolean isCommaExpected() {
        return index > 0 && ( stack[ index - 1 ] == OBJECT_START  || stack[ index - 1 ] == ARRAY_START ) && canWriteComma;
    }

    void ensureCanContinue() throws JsonException {
        if ( finished ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
    }

    private void putObjectEnd() throws JsonException {
        // preconditions
        if ( index == 0 || stack[ index - 1 ] != OBJECT_START || currentEvent == null ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        index--;
        if ( index > 0 ) {
            if (stack[ index - 1 ] == COLON) {
                index -= 2;
                canWriteComma = true;
            } else if ( stack[ index - 1 ] == ARRAY_START) {
                canWriteComma = true;
            }
        }
        jsonKeys.removeLast();
        if ( index == 0 ) {
            finished = true;
        }
    }

    private void putArrayEnd() throws JsonException {
        // preconditions
        if ( index == 0 || stack[ index - 1 ] != ARRAY_START || currentEvent == null ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
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

    private void putValue() throws JsonException {
        // preconditions
        if ( canWriteComma || index == 0 || stack[ index - 1 ] != ARRAY_START && stack[ index - 1 ] != COLON ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( index > 0 && stack[ index - 1 ] == COLON ) {
            index -= 2;
        }
        canWriteComma = true;
    }

    private void putString() throws JsonException {
        // preconditions
        if ( canWriteComma || index == 0 || stack[ index - 1 ] != OBJECT_START && stack[ index - 1 ] != ARRAY_START && stack[ index - 1 ] != COLON ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( index > 0 ) {
            if ( stack[ index - 1 ] == OBJECT_START ) {
                if ( index == stack.length ) doubleStack();
                stack[ index++ ] = STRING;
                return;
            }
            if ( stack[ index - 1 ] == COLON ) {
                index -= 2;
            }
        }
        canWriteComma = true;
    }

    private void putObjectStart() throws JsonException {
        // preconditions
        if ( canWriteComma || ( index != 0 && stack[ index - 1 ] != ARRAY_START && stack[ index - 1 ] != COLON ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( index == stack.length ) doubleStack();
        stack[ index++ ] = OBJECT_START;
        jsonKeys.addLast( new HashSet< String >() );
    }

    private void putArrayStart() throws JsonException {
        // preconditions
        if ( canWriteComma || ( index != 0 && stack[ index - 1 ] != ARRAY_START && stack[ index - 1 ] != COLON ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( index == stack.length ) doubleStack();
        stack[ index++ ] = ARRAY_START;
    }

    private void putColon() throws JsonException {
        // preconditions
        if ( index == 0 || stack[ index - 1 ] != STRING ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( index == stack.length ) doubleStack();
        stack[ index++ ] = COLON;
    }

    private void putComma() throws JsonException {
        // preconditions
        if ( !canWriteComma ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        canWriteComma = false;
    }

    private String getExpectingTokensMessage() {
        if ( index == 0 ) {
            if ( !finished ) {
                return "Expecting " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START;
            } else {
                return "Expecting EOF";
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

    private JsonException newJsonException( final String s ) {
        finished = true;
        return new JsonException( s );
    }

}
