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

import static com.fossnova.json.stream.JsonConstants.BACKSLASH;
import static com.fossnova.json.stream.Utils.isControl;
import static com.fossnova.json.stream.Utils.isWhitespace;
import static com.fossnova.json.stream.Utils.toUnicodeString;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonEvent;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonReader;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonReaderImpl implements JsonReader {

    private PushbackReader in;

    private JsonGrammarAnalyzer analyzer = new JsonGrammarAnalyzer();

    private String lastNumber;

    private String lastString;

    private boolean lastBoolean;

    JsonReaderImpl( final Reader in ) {
        this.in = new PushbackReader( in );
    }

    public void close() {
        lastNumber = null;
        lastString = null;
        analyzer = null;
        in = null;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public String getNumber() {
        if ( !isCurrentEvent( JsonEvent.NUMBER ) ) {
            throw new IllegalStateException( "Current event isn't number" );
        }
        return lastNumber;
    }

    public String getString() {
        if ( !isCurrentEvent( JsonEvent.STRING ) ) {
            throw new IllegalStateException( "Current event isn't string" );
        }
        return lastString;
    }

    public boolean hasNext() throws IOException {
        final int nextChar = in.read();
        if ( nextChar != -1 ) {
            in.unread( nextChar );
            return true;
        }
        return !analyzer.isFinished();
    }

    public JsonEvent next() throws IOException {
        if ( !hasNext() ) {
            throw new JsonException( "No more tokens available" );
        }
        lastNumber = null;
        lastString = null;
        analyzer.ensureCanContinue();
        int nextCharacter = -1;
        boolean exitLoop = false;
        while ( !exitLoop ) {
            switch ( nextCharacter = in.read() ) {
                case JsonConstants.ARRAY_START: {
                    analyzer.push( JsonGrammarToken.ARRAY_START );
                    exitLoop = true;
                }
                    break;
                case JsonConstants.OBJECT_START: {
                    analyzer.push( JsonGrammarToken.OBJECT_START );
                    exitLoop = true;
                }
                    break;
                case JsonConstants.ARRAY_END: {
                    analyzer.push( JsonGrammarToken.ARRAY_END );
                    exitLoop = true;
                }
                    break;
                case JsonConstants.OBJECT_END: {
                    analyzer.push( JsonGrammarToken.OBJECT_END );
                    exitLoop = true;
                }
                    break;
                case JsonConstants.COLON: {
                    analyzer.push( JsonGrammarToken.COLON );
                }
                    break;
                case JsonConstants.COMMA: {
                    analyzer.push( JsonGrammarToken.COMMA );
                }
                    break;
                case 'f':
                case 't': {
                    analyzer.push( JsonGrammarToken.BOOLEAN );
                    in.unread( nextCharacter );
                    readBoolean();
                    exitLoop = true;
                }
                    break;
                case 'n': {
                    analyzer.push( JsonGrammarToken.NULL );
                    in.unread( nextCharacter );
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
                case JsonConstants.MINUS: {
                    analyzer.push( JsonGrammarToken.NUMBER );
                    in.unread( nextCharacter );
                    readNumber();
                    exitLoop = true;
                }
                    break;
                case JsonConstants.QUOTE: {
                    analyzer.push( JsonGrammarToken.STRING );
                    in.unread( nextCharacter );
                    readString();
                    exitLoop = true;
                }
                    break;
                default: {
                    if ( isWhitespace( nextCharacter ) ) {
                        continue;
                    }
                    if ( nextCharacter >= 0 ) {
                        throw new JsonException( "Unexpected character '" + toUnicodeString( nextCharacter ) + "' while reading JSON stream" );
                    } else {
                        throw new JsonException( "Unexpected EOF while reading JSON stream" );
                    }
                }
            }
        }
        return analyzer.getCurrentEvent();
    }

    public boolean isArrayEnd() {
        return isCurrentEvent( JsonEvent.ARRAY_END );
    }

    public boolean isArrayStart() {
        return isCurrentEvent( JsonEvent.ARRAY_START );
    }

    public boolean isNumber() {
        return isCurrentEvent( JsonEvent.NUMBER );
    }

    public boolean isObjectEnd() {
        return isCurrentEvent( JsonEvent.OBJECT_END );
    }

    public boolean isObjectStart() {
        return isCurrentEvent( JsonEvent.OBJECT_START );
    }

    public boolean isString() {
        return isCurrentEvent( JsonEvent.STRING );
    }

    public boolean isNull() {
        return isCurrentEvent( JsonEvent.NULL );
    }

    public boolean isBoolean() {
        return isCurrentEvent( JsonEvent.BOOLEAN );
    }

    private void readString() throws IOException {
        final StringBuilder retVal = new StringBuilder();
        int previousChar = -1;
        int currentChar = -1;
        in.read();
        boolean stringEndFound = false;
        while ( ( currentChar = in.read() ) != -1 ) {
            if ( isStringEnd( previousChar, currentChar ) ) {
                stringEndFound = true;
                break;
            }
            if ( isControl( currentChar ) ) {
                throw new JsonException( "Unexpected control character '" + toUnicodeString( currentChar ) + "' while reading JSON string" );
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
            throw new JsonException( "Unexpected EOF while reading JSON string" );
        }
        lastString = retVal.toString();
    }

    private void readBoolean() throws IOException {
        final int firstChar = in.read();
        int currentChar = -1;
        boolean correctBooleanValue = false;
        if ( firstChar == 't' ) {
            if ( ( ( currentChar = in.read() ) == 'r' ) && ( ( currentChar = in.read() ) == 'u' ) && ( ( currentChar = in.read() ) == 'e' ) ) {
                lastBoolean = true;
                correctBooleanValue = true;
            }
        } else {
            if ( ( ( currentChar = in.read() ) == 'a' ) && ( ( currentChar = in.read() ) == 'l' ) && ( ( currentChar = in.read() ) == 's' )
                && ( ( currentChar = in.read() ) == 'e' ) ) {
                lastBoolean = false;
                correctBooleanValue = true;
            }
        }
        if ( !correctBooleanValue ) {
            if ( currentChar == -1 ) {
                throw new JsonException( "Unexpected EOF while reading JSON " + ( firstChar == 't' ) + " token" );
            }
            throw new JsonException( "Unexpected character '" + toUnicodeString( currentChar ) + "' while reading JSON " + ( firstChar == 't' ) + " token" );
        }
    }

    private void readNull() throws IOException {
        int currentChar = -1;
        if ( ( ( currentChar = in.read() ) == 'n' ) && ( ( currentChar = in.read() ) == 'u' ) && ( ( currentChar = in.read() ) == 'l' ) && ( ( currentChar = in.read() ) == 'l' ) ) {
            return;
        }
        if ( currentChar == -1 ) {
            throw new JsonException( "Unexpected EOF while reading JSON null token" );
        }
        throw new JsonException( "Unexpected character '" + toUnicodeString( currentChar ) + "' while reading JSON null token" );
    }

    private void readNumber() throws IOException {
        final StringBuilder retVal = new StringBuilder();
        int currentChar = -1;
        while ( ( currentChar = in.read() ) != -1 ) {
            if ( !isNumberCharacter( currentChar ) ) {
                in.unread( currentChar );
                break;
            }
            retVal.appendCodePoint( currentChar );
        }
        if ( currentChar == -1 ) {
            throw new JsonException( "Unexpected EOF while reading JSON number" );
        }
        lastNumber = retVal.toString();
    }

    private boolean isNumberCharacter( final int character ) {
        if ( ( '0' <= character ) && ( character <= '9' ) ) return true;
        if ( character == '-' ) return true;
        if ( character == '+' ) return true;
        if ( character == '.' ) return true;
        if ( character == 'e' ) return true;
        if ( character == 'E' ) return true;
        return false;
    }

    private boolean isStringEnd( final int first, final int second ) {
        return ( first != BACKSLASH ) && ( second == JsonConstants.QUOTE );
    }

    private boolean isCurrentEvent( final JsonEvent event ) {
        return analyzer.getCurrentEvent() == event;
    }

    public boolean getBoolean() {
        if ( !isCurrentEvent( JsonEvent.BOOLEAN ) ) {
            throw new IllegalStateException( "Current event isn't boolean" );
        }
        return lastBoolean;
    }

    public byte getByte() {
        return Byte.parseByte( getNumber() );
    }

    public short getShort() {
        return Short.parseShort( getNumber() );
    }

    public int getInt() {
        return Integer.parseInt( getNumber() );
    }

    public long getLong() {
        return Long.parseLong( getNumber() );
    }

    public BigInteger getBigInteger() {
        return new BigInteger( getNumber() );
    }

    public BigDecimal getBigDecimal() {
        return new BigDecimal( getNumber() );
    }

    public float getFloat() {
        return Float.parseFloat( getNumber() );
    }

    public double getDouble() {
        return Double.parseDouble( getNumber() );
    }
}
