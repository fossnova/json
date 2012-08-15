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
package com.fossnova.json;

import static com.fossnova.json.JsonGrammarToken.ARRAY_END;
import static com.fossnova.json.JsonGrammarToken.ARRAY_START;
import static com.fossnova.json.JsonGrammarToken.BOOLEAN;
import static com.fossnova.json.JsonGrammarToken.COLON;
import static com.fossnova.json.JsonGrammarToken.COMMA;
import static com.fossnova.json.JsonGrammarToken.NULL;
import static com.fossnova.json.JsonGrammarToken.NUMBER;
import static com.fossnova.json.JsonGrammarToken.OBJECT_END;
import static com.fossnova.json.JsonGrammarToken.OBJECT_START;
import static com.fossnova.json.JsonGrammarToken.STRING;

import java.util.LinkedList;

import org.fossnova.json.JsonEvent;
import org.fossnova.json.JsonException;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonGrammarAnalyzer {

    private boolean canWriteComma;

    private JsonEvent currentEvent;

    private boolean finished;

    private final LinkedList< JsonGrammarToken > stack = new LinkedList< JsonGrammarToken >();

    void push( final JsonGrammarToken event ) {
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
        } else if ( event == COMMA ) {
            putComma();
        } else {
            throw new IllegalStateException();
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

    private void putObjectEnd() {
        // preconditions
        if ( !isLastOnStack( OBJECT_START ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.removeLast();
        if ( isLastOnStack( COLON ) ) {
            stack.removeLast();
            stack.removeLast();
            canWriteComma = true;
        }
        if ( isEmpty() ) {
            setCannotContinue();
        }
    }

    private void putArrayEnd() {
        // preconditions
        if ( !isLastOnStack( ARRAY_START ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.removeLast();
        if ( isLastOnStack( COLON ) ) {
            stack.removeLast();
            stack.removeLast();
            canWriteComma = true;
        }
        if ( isEmpty() ) {
            setCannotContinue();
        }
    }

    private void putValue() {
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

    private void putString() {
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

    private void putObjectStart() {
        // preconditions
        if ( canWriteComma || ( !isEmpty() && !isLastOnStack( ARRAY_START ) && !isLastOnStack( COLON ) ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.add( OBJECT_START );
    }

    private void putArrayStart() {
        // preconditions
        if ( canWriteComma || ( !isEmpty() && !isLastOnStack( ARRAY_START ) && !isLastOnStack( COLON ) ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.add( ARRAY_START );
    }

    private void putColon() {
        // preconditions
        if ( !isLastOnStack( STRING ) ) {
            throw newJsonException( getExpectingTokensMessage() );
        }
        // implementation
        stack.add( COLON );
    }

    private void putComma() {
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
                return "Expecting: '" + JsonConstants.OBJECT_START + "', '" + JsonConstants.ARRAY_START + "'";
            } else {
                return "Expecting: EOF";
            }
        }
        if ( isLastOnStack( OBJECT_START ) ) {
            if ( !canWriteComma ) {
                return "Expecting: '" + JsonConstants.OBJECT_END + "', '" + JsonConstants.STRING + "'";
            } else {
                return "Expecting: '" + JsonConstants.COMMA + "', " + JsonConstants.OBJECT_END + "'";
            }
        }
        if ( isLastOnStack( ARRAY_START ) ) {
            if ( !canWriteComma ) {
                return "Expecting: '" + JsonConstants.ARRAY_END + "', '" + JsonConstants.OBJECT_START + "', '" + JsonConstants.ARRAY_START + "', '" + JsonConstants.STRING + "', '"
                    + JsonConstants.NUMBER + "', '" + JsonConstants.TRUE + "', '" + JsonConstants.FALSE + "', '" + JsonConstants.NULL + "'";
            } else {
                return "Expecting: '" + JsonConstants.COMMA + "', '" + JsonConstants.ARRAY_END + "'";
            }
        }
        if ( isLastOnStack( COLON ) ) {
            return "Expecting: '" + JsonConstants.OBJECT_START + "', '" + JsonConstants.ARRAY_START + "', '" + JsonConstants.STRING + "', '" + JsonConstants.NUMBER + "', '"
                + JsonConstants.TRUE + "', '" + JsonConstants.FALSE + "', '" + JsonConstants.NULL + "'";
        }
        if ( isLastOnStack( STRING ) ) {
            return "Expecting: '" + JsonConstants.COLON + "'";
        }
        throw new IllegalStateException();
    }

    private boolean isLastOnStack( final JsonGrammarToken event ) {
        return !isEmpty() && ( stack.getLast() == event );
    }

    private boolean isLastButOneOnStack( final JsonGrammarToken event ) {
        return ( stack.size() >= 2 ) && ( stack.get( stack.size() - 2 ) == event );
    }

    void ensureCanContinue() {
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

    private JsonException newJsonException( final String s ) {
        setCannotContinue();
        return new JsonException( s );
    }

    private void setCannotContinue() {
        finished = true;
    }
}
