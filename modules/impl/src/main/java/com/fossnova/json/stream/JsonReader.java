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

import static com.fossnova.json.stream.JsonConstants.ARRAY_END;
import static com.fossnova.json.stream.JsonConstants.ARRAY_START;
import static com.fossnova.json.stream.JsonConstants.BACKSLASH;
import static com.fossnova.json.stream.JsonConstants.COLON;
import static com.fossnova.json.stream.JsonConstants.COMMA;
import static com.fossnova.json.stream.JsonConstants.FALSE;
import static com.fossnova.json.stream.JsonConstants.MINUS;
import static com.fossnova.json.stream.JsonConstants.NULL;
import static com.fossnova.json.stream.JsonConstants.OBJECT_END;
import static com.fossnova.json.stream.JsonConstants.OBJECT_START;
import static com.fossnova.json.stream.JsonConstants.QUOTE;
import static com.fossnova.json.stream.JsonConstants.TRUE;
import static com.fossnova.json.stream.Utils.isControl;
import static com.fossnova.json.stream.Utils.isNumberCharacter;
import static com.fossnova.json.stream.Utils.isNumberString;
import static com.fossnova.json.stream.Utils.isStringEnd;
import static com.fossnova.json.stream.Utils.isWhitespace;
import static com.fossnova.json.stream.Utils.toUnicodeString;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonEvent;
import org.fossnova.json.stream.JsonException;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonReader implements org.fossnova.json.stream.JsonReader {

    private PushbackReader in;

    private JsonGrammarAnalyzer analyzer;

    private String jsonNumber;

    private String jsonString;

    private boolean jsonBoolean;

    private boolean closed;

    private int currentChar;

    JsonReader( final Reader in ) {
        this.in = new PushbackReader( in );
        analyzer = new JsonGrammarAnalyzer();
    }

    @Override
    public void close() {
        analyzer = null;
        in = null;
        closed = true;
        jsonNumber = null;
        jsonString = null;
    }

    @Override
    public boolean getBoolean() {
        if ( !isCurrentEvent( JsonEvent.BOOLEAN ) ) {
            throw new IllegalStateException( "Current event isn't boolean" );
        }
        return jsonBoolean;
    }

    public String getNumber() {
        if ( !isCurrentEvent( JsonEvent.NUMBER ) ) {
            throw new IllegalStateException( "Current event isn't number" );
        }
        return jsonNumber;
    }

    @Override
    public String getString() {
        if ( !isCurrentEvent( JsonEvent.STRING ) ) {
            throw new IllegalStateException( "Current event isn't string" );
        }
        return jsonString;
    }

    @Override
    public byte getByte() {
        return Byte.parseByte( getNumber() );
    }

    @Override
    public short getShort() {
        return Short.parseShort( getNumber() );
    }

    @Override
    public int getInt() {
        return Integer.parseInt( getNumber() );
    }

    @Override
    public long getLong() {
        return Long.parseLong( getNumber() );
    }

    @Override
    public BigInteger getBigInteger() {
        return new BigInteger( getNumber() );
    }

    @Override
    public BigDecimal getBigDecimal() {
        return new BigDecimal( getNumber() );
    }

    @Override
    public float getFloat() {
        return Float.parseFloat( getNumber() );
    }

    @Override
    public double getDouble() {
        return Double.parseDouble( getNumber() );
    }

    @Override
    public boolean isArrayEnd() {
        return isCurrentEvent( JsonEvent.ARRAY_END );
    }

    @Override
    public boolean isArrayStart() {
        return isCurrentEvent( JsonEvent.ARRAY_START );
    }

    @Override
    public boolean isNumber() {
        return isCurrentEvent( JsonEvent.NUMBER );
    }

    @Override
    public boolean isObjectEnd() {
        return isCurrentEvent( JsonEvent.OBJECT_END );
    }

    @Override
    public boolean isObjectStart() {
        return isCurrentEvent( JsonEvent.OBJECT_START );
    }

    @Override
    public boolean isString() {
        return isCurrentEvent( JsonEvent.STRING );
    }

    @Override
    public boolean isNull() {
        return isCurrentEvent( JsonEvent.NULL );
    }

    @Override
    public boolean isBoolean() {
        return isCurrentEvent( JsonEvent.BOOLEAN );
    }

    private boolean isCurrentEvent( final JsonEvent event ) {
        ensureOpen();
        return analyzer.getCurrentEvent() == event;
    }

    @Override
    public boolean hasNext() throws IOException, JsonException {
        ensureOpen();
        return !analyzer.isFinished();
    }

    @Override
    public JsonEvent next() throws IOException, JsonException {
        if ( !hasNext() ) {
            throw new IllegalStateException( "No more JSON tokens available" );
        }
        analyzer.ensureCanContinue();
        boolean exitLoop = false;
        while ( !exitLoop ) {
            currentChar = in.read();
            switch ( currentChar ) {
                case ARRAY_START: {
                    analyzer.push( JsonGrammarToken.ARRAY_START );
                    exitLoop = true;
                }
                    break;
                case OBJECT_START: {
                    analyzer.push( JsonGrammarToken.OBJECT_START );
                    exitLoop = true;
                }
                    break;
                case ARRAY_END: {
                    analyzer.push( JsonGrammarToken.ARRAY_END );
                    exitLoop = true;
                }
                    break;
                case OBJECT_END: {
                    analyzer.push( JsonGrammarToken.OBJECT_END );
                    exitLoop = true;
                }
                    break;
                case COLON: {
                    analyzer.push( JsonGrammarToken.COLON );
                }
                    break;
                case COMMA: {
                    analyzer.push( JsonGrammarToken.COMMA );
                }
                    break;
                case 'f':
                case 't': {
                    analyzer.push( JsonGrammarToken.BOOLEAN );
                    in.unread( currentChar );
                    readBoolean( currentChar == 't' );
                    exitLoop = true;
                }
                    break;
                case 'n': {
                    analyzer.push( JsonGrammarToken.NULL );
                    in.unread( currentChar );
                    readNull();
                    exitLoop = true;
                }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case MINUS: {
                    analyzer.push( JsonGrammarToken.NUMBER );
                    in.unread( currentChar );
                    readNumber();
                    exitLoop = true;
                }
                    break;
                case QUOTE: {
                    analyzer.push( JsonGrammarToken.STRING );
                    in.unread( currentChar );
                    readString();
                    analyzer.pushString( jsonString );
                    exitLoop = true;
                }
                    break;
                default: {
                    if ( isWhitespace( currentChar ) ) {
                        continue;
                    }
                    if ( currentChar >= 0 ) {
                        throw newJsonException( "Unexpected character '" + toUnicodeString( currentChar ) + "' while reading JSON stream" );
                    } else {
                        throw newJsonException( "Unexpected EOF while reading JSON stream" );
                    }
                }
            }
        }
        return analyzer.getCurrentEvent();
    }

    private void readString() throws IOException, JsonException {
        final StringBuilder retVal = new StringBuilder();
        int previousChar = -1;
        in.read();
        boolean stringEndFound = false;
        while ( ( currentChar = in.read() ) != -1 ) {
            if ( isStringEnd( previousChar, currentChar ) ) {
                stringEndFound = true;
                break;
            }
            if ( isControl( currentChar ) ) {
                throw newJsonException( "Unexpected control character '" + toUnicodeString( currentChar ) + "' while reading JSON string" );
            }
            if ( ( currentChar == BACKSLASH ) && ( previousChar != BACKSLASH ) ) {
                previousChar = currentChar;
                continue;
            }
            if ( ( previousChar == BACKSLASH ) && ( currentChar == 'b' ) ) {
                retVal.appendCodePoint( '\b' );
            } else if ( ( previousChar == BACKSLASH ) && ( currentChar == 'f' ) ) {
                retVal.appendCodePoint( '\f' );
            } else if ( ( previousChar == BACKSLASH ) && ( currentChar == 'n' ) ) {
                retVal.appendCodePoint( '\n' );
            } else if ( ( previousChar == BACKSLASH ) && ( currentChar == 'r' ) ) {
                retVal.appendCodePoint( '\r' );
            } else if ( ( previousChar == BACKSLASH ) && ( currentChar == 't' ) ) {
                retVal.appendCodePoint( '\t' );
            } else if ( ( previousChar == BACKSLASH ) && ( currentChar == 'u' ) ) {
                final StringBuilder sb = new StringBuilder( 4 );
                for ( int i = 0; i < 4; i++ ) {
                    sb.append( ( char ) in.read() );
                }
                final String hexValue = sb.toString();
                retVal.appendCodePoint( Integer.parseInt( hexValue, 16 ) );
                currentChar = hexValue.charAt( 3 );
            } else {
                retVal.appendCodePoint( currentChar );
            }
            previousChar = ( char ) -1;
        }
        if ( !stringEndFound ) {
            throw newJsonException( "Unexpected EOF while reading JSON string" );
        }
        jsonString = retVal.toString();
    }

    private void readBoolean( final boolean b ) throws IOException, JsonException {
        if ( !readString( b ? TRUE : FALSE ) ) {
            if ( currentChar == -1 ) {
                throw newJsonException( "Unexpected EOF while reading JSON " + b + " token" );
            }
            throw newJsonException( "Unexpected character '" + toUnicodeString( currentChar ) + "' while reading JSON " + b + " token" );
        }
        jsonBoolean = b;
    }

    private void readNull() throws IOException, JsonException {
        if ( !readString( NULL ) ) {
            if ( currentChar == -1 ) {
                throw newJsonException( "Unexpected EOF while reading JSON null token" );
            }
            throw newJsonException( "Unexpected character '" + toUnicodeString( currentChar ) + "' while reading JSON null token" );
        }
    }

    private boolean readString( final String s ) throws IOException {
        for ( int i = 0; i < s.length(); i++ ) {
            currentChar = in.read();
            if ( currentChar != s.codePointAt( i ) ) return false;
        }
        return true;
    }

    private void readNumber() throws IOException, JsonException {
        final StringBuilder retVal = new StringBuilder();
        while ( ( currentChar = in.read() ) != -1 ) {
            if ( !isNumberCharacter( currentChar ) ) {
                in.unread( currentChar );
                break;
            }
            retVal.appendCodePoint( currentChar );
        }
        if ( currentChar == -1 ) {
            throw newJsonException( "Unexpected EOF while reading JSON number" );
        }
        jsonNumber = retVal.toString();
        if ( !isNumberString( jsonNumber ) ) {
            throw newJsonException( "Incorrect JSON number: '" + jsonNumber + "'" );
        }
    }

    private JsonException newJsonException( final String message ) {
        analyzer.setCannotContinue();
        return new JsonException( message );
    }

    private void ensureOpen() {
        if ( closed ) {
            throw new IllegalStateException( "JSON reader have been closed" );
        }
    }
}
