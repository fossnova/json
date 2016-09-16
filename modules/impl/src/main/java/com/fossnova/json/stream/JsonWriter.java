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

import static java.lang.Math.min;
import static java.lang.String.valueOf;
import static com.fossnova.json.stream.JsonConstants.ARRAY_END;
import static com.fossnova.json.stream.JsonConstants.ARRAY_START;
import static com.fossnova.json.stream.JsonConstants.BACKSLASH;
import static com.fossnova.json.stream.JsonConstants.BACKSPACE;
import static com.fossnova.json.stream.JsonConstants.COLON;
import static com.fossnova.json.stream.JsonConstants.COMMA;
import static com.fossnova.json.stream.JsonConstants.CR;
import static com.fossnova.json.stream.JsonConstants.FORMFEED;
import static com.fossnova.json.stream.JsonConstants.QUOTE;
import static com.fossnova.json.stream.JsonConstants.OBJECT_END;
import static com.fossnova.json.stream.JsonConstants.OBJECT_START;
import static com.fossnova.json.stream.JsonConstants.NL;
import static com.fossnova.json.stream.JsonConstants.NULL;
import static com.fossnova.json.stream.JsonConstants.TAB;
import static com.fossnova.json.stream.Utils.isControl;
import static com.fossnova.json.stream.Utils.stringSizeOf;
import static com.fossnova.json.stream.Utils.ONES;
import static com.fossnova.json.stream.Utils.TENS;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonException;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonWriter implements org.fossnova.json.stream.JsonWriter {

    private final JsonGrammarAnalyzer analyzer;
    private final Writer out;
    private final char[] buffer = new char[ 1024 ];
    private int limit;
    private boolean closed;

    JsonWriter( final Writer out ) {
        this.out = out;
        analyzer = new JsonGrammarAnalyzer();
    }

    @Override
    public void close() {
        if ( limit > 0 ) throw new IllegalStateException( "Flush method must be called before closing" );
        closed = true;
    }

    @Override
    public void flush() throws IOException {
        ensureOpen();
        if ( limit > 0 ) {
            out.write( buffer, 0, limit );
            limit = 0;
        }
        out.flush();
    }

    @Override
    public JsonWriter writeObjectStart() throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putObjectStart();
        write( OBJECT_START );
        return this;
    }

    @Override
    public JsonWriter writeObjectEnd() throws IOException, JsonException {
        ensureOpen();
        analyzer.putObjectEnd();
        write( OBJECT_END );
        return this;
    }

    @Override
    public JsonWriter writeArrayStart() throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putArrayStart();
        write( ARRAY_START );
        return this;
    }

    @Override
    public JsonWriter writeArrayEnd() throws IOException, JsonException {
        ensureOpen();
        analyzer.putArrayEnd();
        write( ARRAY_END );
        return this;
    }

    @Override
    public JsonWriter writeString( final String data ) throws IOException, JsonException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putString();
        encode( data );
        return this;
    }

    @Override
    public JsonWriter writeNull() throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putNull();
        write( NULL, 0, NULL.length() );
        return this;
    }

    @Override
    public JsonWriter writeBoolean( final boolean data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putBoolean();
        if ( limit + 5 > buffer.length ) {
            out.write( buffer, 0, limit );
            limit = 0;
        }
        if ( data ) {
            buffer[ limit++ ] = 't';
            buffer[ limit++ ] = 'r';
            buffer[ limit++ ] = 'u';
            buffer[ limit++ ] = 'e';
        } else {
            buffer[ limit++ ] = 'f';
            buffer[ limit++ ] = 'a';
            buffer[ limit++ ] = 'l';
            buffer[ limit++ ] = 's';
            buffer[ limit++ ] = 'e';
        }
        return this;
    }

    @Override
    public JsonWriter writeByte( final byte data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putNumber();
        encode( data );
        return this;
    }

    @Override
    public JsonWriter writeShort( final short data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putNumber();
        encode( data );
        return this;
    }

    @Override
    public JsonWriter writeInt( final int data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putNumber();
        encode( data );
        return this;
    }

    @Override
    public JsonWriter writeLong( final long data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putNumber();
        encode( data );
        return this;
    }

    @Override
    public JsonWriter writeFloat( final float data ) throws IOException, JsonException {
        return writeNumber( valueOf( data ) );
    }

    @Override
    public JsonWriter writeDouble( final double data ) throws IOException, JsonException {
        return writeNumber( valueOf( data ) );
    }

    @Override
    public JsonWriter writeBigInteger( final BigInteger data ) throws IOException, JsonException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        return writeNumber( valueOf( data ) );
    }

    @Override
    public JsonWriter writeBigDecimal( final BigDecimal data ) throws IOException, JsonException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        return writeNumber( valueOf( data ) );
    }

    public JsonWriter writeNumber( final String data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.putNumber();
        write( data, 0, data.length() );
        return this;
    }

    private void writeOptionalColonOrComma() throws IOException, JsonException {
        if ( analyzer.isColonExpected() ) {
            analyzer.putColon();
            write( COLON );
        } else if ( analyzer.isCommaExpected() ) {
            analyzer.putComma();
            write( COMMA );
        }
    }

    private void write( final char c ) throws IOException {
        if ( limit == buffer.length ) {
            out.write( buffer, 0, limit );
            limit = 0;
        }

        buffer[ limit++ ] = c;
    }

    private void write( final String data, int dataBegin, final int dataEnd ) throws IOException {
        int count;
        while ( dataBegin < dataEnd ) {
            count = min( dataEnd - dataBegin, buffer.length - limit );
            data.getChars( dataBegin, dataBegin + count, buffer, limit );
            dataBegin += count;
            limit += count;
            if ( limit == buffer.length )  {
                out.write( buffer, 0, buffer.length );
                limit = 0;
            }
        }
    }

    private void encode( final String s ) throws IOException {
        char c;
        write( QUOTE );
        int dataBegin = 0;
        for ( int dataEnd = 0; dataEnd < s.length(); dataEnd++ ) {
            c = s.charAt( dataEnd );
            // identify unescaped string sequence
            while ( c != BACKSLASH && c != QUOTE && !isControl( c ) ) {
                if ( ++dataEnd < s.length() ) { c = s.charAt( dataEnd ); } else break;
            }
            // write unescaped characters
            if ( dataBegin < dataEnd ) {
                write( s, dataBegin, dataEnd );
                if ( dataEnd == s.length() ) break;
            }
            // escape characters
            dataBegin = dataEnd + 1;
            write( BACKSLASH );
            if ( c == BACKSLASH || c == QUOTE ) write( c );
            else if ( c == BACKSPACE ) write( 'b' );
            else if ( c == FORMFEED ) write( 'f' );
            else if ( c == NL ) write( 'n' );
            else if ( c == CR ) write( 'r' );
            else if ( c == TAB ) write( 't' );
            else {
                write( 'u' );
                final String hexString = Integer.toHexString( c );
                for ( int j = 0; j < ( 4 - hexString.length() ); j++ ) {
                    write( '0' );
                }
                write( hexString, 0, hexString.length() );
            }
        }
        write( QUOTE );
    }

    private void encode( long l ) throws IOException {
        // cannot write all possible long values if less than 20 chars is remaining
        if ( buffer.length - limit < 20 ) {
            out.write( buffer, 0, limit );
            limit = 0;
        }

        // compute bounds
        long longQuotient;
        int remainder;
        int writeIndex = limit + stringSizeOf( l );
        limit = writeIndex;

        // always convert to negative number
        final boolean negative = l < 0;
        if ( !negative ) {
            l = -l;
        }

        // processing upper 32 bits (long operations are slower on CPU)
        while ( l < Integer.MIN_VALUE ) {
            longQuotient = l / 100;
            remainder = ( int ) ( ( longQuotient * 100 ) - l );
            l = longQuotient;
            buffer[ --writeIndex ] = ONES[ remainder ];
            buffer[ --writeIndex ] = TENS[ remainder ];
        }

        // processing lower 32 bits (int operations are faster on CPU)
        int intQuotient;
        int i = ( int ) l;
        while ( i <= -100 ) {
            intQuotient = i / 100;
            remainder  = ( intQuotient * 100 ) - i;
            i = intQuotient;
            buffer[ --writeIndex ] = ONES[ remainder ];
            buffer[ --writeIndex ] = TENS[ remainder ];
        }

        // processing remaining digits
        intQuotient = i / 10;
        remainder  = ( intQuotient * 10 ) - i;
        buffer[ --writeIndex ] = ( char ) ( '0' + remainder );

        if ( intQuotient < 0 ) {
            buffer[ --writeIndex ] = ( char ) ( '0' - intQuotient );
        }

        // processing sign
        if ( negative ) {
            buffer[ --writeIndex ] = '-';
        }
    }

    private void encode( int i ) throws IOException {
        // cannot write all possible int values if less than 11 chars is remaining
        if ( buffer.length - limit < 11 ) {
            out.write( buffer, 0, limit );
            limit = 0;
        }

        // compute bounds
        int quotient;
        int remainder;
        int writeIndex = limit + stringSizeOf( i );
        limit = writeIndex;

        // always convert to negative number
        boolean negative = i < 0;
        if ( !negative ) {
            i = -i;
        }

        // processing lower 32 bits (int operations are faster on CPU)
        while ( i <= -100 ) {
            quotient = i / 100;
            remainder = ( quotient * 100 ) - i;
            i = quotient;
            buffer[ --writeIndex ] = ONES[ remainder ];
            buffer[ --writeIndex ] = TENS[ remainder ];
        }

        // processing remaining digits
        quotient = i / 10;
        remainder = ( quotient * 10 ) - i;
        buffer[ --writeIndex ] = ( char ) ( '0' + remainder );

        if ( quotient < 0 ) {
            buffer[ --writeIndex ] = ( char ) ( '0' - quotient );
        }

        // processing sign
        if ( negative ) {
            buffer[ --writeIndex ] = '-';
        }
    }

    private void ensureOpen() {
        if ( closed ) {
            throw new IllegalStateException( "JSON writer have been closed" );
        }
    }

}
