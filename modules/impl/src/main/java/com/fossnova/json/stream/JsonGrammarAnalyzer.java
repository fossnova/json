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

import java.util.HashSet;
import java.util.LinkedList;
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

    private boolean finished;

    private final LinkedList< Set< String >> jsonKeys = new LinkedList< Set< String >>();

    private final LinkedList< Byte > stack = new LinkedList< Byte >();

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
        if ( isLastOnStack( STRING ) ) {
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
        return isLastButOneOnStack( OBJECT_START ) && isLastOnStack( STRING );
    }

    boolean isCommaExpected() {
        return ( isLastOnStack( OBJECT_START ) || isLastOnStack( ARRAY_START ) ) && canWriteComma;
    }

    void ensureCanContinue() throws JsonException {
        if ( finished ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
    }

    boolean isFinished() {
        return finished;
    }

    boolean isEmpty() {
        return stack.size() == 0;
    }

    void setCannotContinue() {
        finished = true;
    }

    private void putObjectEnd() throws JsonException {
        // preconditions
        if ( !isLastOnStack( OBJECT_START ) || ( currentEvent == null ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.removeLast();
        if ( isLastOnStack( COLON ) ) {
            stack.removeLast();
            stack.removeLast();
            canWriteComma = true;
        } else if ( isLastOnStack( ARRAY_START ) ) {
            canWriteComma = true;
        }
        jsonKeys.getLast().clear();
        jsonKeys.removeLast();
        if ( isEmpty() ) {
            setCannotContinue();
        }
    }

    private void putArrayEnd() throws JsonException {
        // preconditions
        if ( !isLastOnStack( ARRAY_START ) || ( currentEvent == null ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.removeLast();
        if ( isLastOnStack( COLON ) ) {
            stack.removeLast();
            stack.removeLast();
            canWriteComma = true;
        } else if ( isLastOnStack( ARRAY_START ) ) {
            canWriteComma = true;
        }
        if ( isEmpty() ) {
            setCannotContinue();
        }
    }

    private void putValue() throws JsonException {
        // preconditions
        if ( canWriteComma || ( !isLastOnStack( ARRAY_START ) && !isLastOnStack( COLON ) ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( isLastOnStack( COLON ) ) {
            stack.removeLast();
            stack.removeLast();
        }
        canWriteComma = true;
    }

    private void putString() throws JsonException {
        // preconditions
        if ( canWriteComma || ( !isLastOnStack( OBJECT_START ) && !isLastOnStack( ARRAY_START ) && !isLastOnStack( COLON ) ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        if ( isLastOnStack( OBJECT_START ) ) {
            stack.add( STRING );
            return;
        }
        if ( isLastOnStack( COLON ) ) {
            stack.removeLast();
            stack.removeLast();
        }
        canWriteComma = true;
    }

    private void putObjectStart() throws JsonException {
        // preconditions
        if ( canWriteComma || ( !isEmpty() && !isLastOnStack( ARRAY_START ) && !isLastOnStack( COLON ) ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.add( OBJECT_START );
        jsonKeys.addLast( new HashSet< String >() );
    }

    private void putArrayStart() throws JsonException {
        // preconditions
        if ( canWriteComma || ( !isEmpty() && !isLastOnStack( ARRAY_START ) && !isLastOnStack( COLON ) ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.add( ARRAY_START );
    }

    private void putColon() throws JsonException {
        // preconditions
        if ( !isLastOnStack( STRING ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.add( COLON );
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
        if ( isEmpty() ) {
            if ( !finished ) {
                return "Expecting " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START;
            } else {
                return "Expecting EOF";
            }
        }
        if ( isLastOnStack( OBJECT_START ) ) {
            if ( !canWriteComma ) {
                return "Expecting " + JsonConstants.OBJECT_END + " " + JsonConstants.STRING;
            } else {
                return "Expecting " + JsonConstants.COMMA + " " + JsonConstants.OBJECT_END;
            }
        }
        if ( isLastOnStack( ARRAY_START ) ) {
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
        if ( isLastOnStack( COLON ) ) {
            return "Expecting " + JsonConstants.OBJECT_START + " " + JsonConstants.ARRAY_START + " " + JsonConstants.STRING + " " + JsonConstants.NUMBER + " " + JsonConstants.TRUE
                + " " + JsonConstants.FALSE + " " + JsonConstants.NULL;
        }
        if ( isLastOnStack( STRING ) ) {
            return "Expecting " + JsonConstants.COLON;
        }
        throw new IllegalStateException();
    }

    private boolean isLastOnStack( final byte event ) {
        return !isEmpty() && ( stack.getLast() == event );
    }

    private boolean isLastButOneOnStack( final byte event ) {
        return ( stack.size() >= 2 ) && ( stack.get( stack.size() - 2 ) == event );
    }

    private JsonException newJsonException( final String s ) {
        setCannotContinue();
        return new JsonException( s );
    }
}
