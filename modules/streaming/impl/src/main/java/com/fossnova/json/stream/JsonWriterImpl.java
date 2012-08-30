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
import static com.fossnova.json.stream.JsonConstants.BACKSPACE;
import static com.fossnova.json.stream.JsonConstants.COLON;
import static com.fossnova.json.stream.JsonConstants.COMMA;
import static com.fossnova.json.stream.JsonConstants.CR;
import static com.fossnova.json.stream.JsonConstants.FORMFEED;
import static com.fossnova.json.stream.JsonConstants.NL;
import static com.fossnova.json.stream.JsonConstants.NULL;
import static com.fossnova.json.stream.JsonConstants.OBJECT_END;
import static com.fossnova.json.stream.JsonConstants.OBJECT_START;
import static com.fossnova.json.stream.JsonConstants.QUOTE;
import static com.fossnova.json.stream.JsonConstants.SOLIDUS;
import static com.fossnova.json.stream.JsonConstants.TAB;
import static com.fossnova.json.stream.Utils.isControl;
import static com.fossnova.json.stream.Utils.toUnicodeString;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonWriterImpl implements JsonWriter {

    private JsonGrammarAnalyzer analyzer = new JsonGrammarAnalyzer();

    private Writer out;

    private boolean closed;

    JsonWriterImpl( final Writer out ) {
        this.out = out;
    }

    private void ensureOpen() {
        if ( closed ) {
            throw new UnsupportedOperationException( "JSON writer have been closed" );
        }
    }

    public void close() {
        analyzer = null;
        out = null;
        closed = true;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public JsonWriter flush() throws IOException {
        ensureOpen();
        out.flush();
        return this;
    }

    public JsonWriter writeObjectStart() throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.OBJECT_START );
        out.write( OBJECT_START );
        return this;
    }

    public JsonWriter writeObjectEnd() throws IOException {
        ensureOpen();
        analyzer.push( JsonGrammarToken.OBJECT_END );
        out.write( OBJECT_END );
        return this;
    }

    public JsonWriter writeArrayStart() throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.ARRAY_START );
        out.write( ARRAY_START );
        return this;
    }

    public JsonWriter writeArrayEnd() throws IOException {
        ensureOpen();
        analyzer.push( JsonGrammarToken.ARRAY_END );
        out.write( ARRAY_END );
        return this;
    }

    public JsonWriter writeString( final String data ) throws IOException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.STRING );
        out.write( encode( data ) );
        return this;
    }

    public JsonWriter writeNull() throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NULL );
        out.write( NULL );
        return this;
    }

    public JsonWriter writeBoolean( final boolean data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.BOOLEAN );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeByte( final byte data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeShort( final short data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeInt( final int data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeLong( final long data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeBigInteger( final BigInteger data ) throws IOException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeBigDecimal( final BigDecimal data ) throws IOException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeFloat( final float data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeDouble( final double data ) throws IOException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ) );
        return this;
    }

    private void writeOptionalColonOrComma() throws IOException {
        if ( analyzer.isColonExpected() ) {
            writeColon();
        } else if ( analyzer.isCommaExpected() ) {
            writeComma();
        }
    }

    private void writeColon() throws IOException {
        analyzer.push( JsonGrammarToken.COLON );
        out.write( COLON );
    }

    private void writeComma() throws IOException {
        analyzer.push( JsonGrammarToken.COMMA );
        out.write( COMMA );
    }

    private static String encode( final String s ) {
        final int length = s.length();
        final StringBuilder retVal = new StringBuilder( length + 16 );
        retVal.append( QUOTE );
        for ( int i = 0; i < length; i = s.offsetByCodePoints( i, 1 ) ) {
            final int character = s.codePointAt( i );
            switch ( character ) {
                case QUOTE:
                    retVal.append( BACKSLASH ).append( QUOTE );
                    break;
                case BACKSLASH:
                    retVal.append( BACKSLASH ).append( BACKSLASH );
                    break;
                case SOLIDUS:
                    retVal.append( BACKSLASH ).append( SOLIDUS );
                    break;
                case BACKSPACE:
                    retVal.append( BACKSLASH ).append( 'b' );
                    break;
                case FORMFEED:
                    retVal.append( BACKSLASH ).append( 'f' );
                    break;
                case NL:
                    retVal.append( BACKSLASH ).append( 'n' );
                    break;
                case CR:
                    retVal.append( BACKSLASH ).append( 'r' );
                    break;
                case TAB:
                    retVal.append( BACKSLASH ).append( 't' );
                    break;
                default:
                    if ( isControl( character ) ) {
                        retVal.append( toUnicodeString( character ) );
                    } else {
                        retVal.appendCodePoint( character );
                    }
                    break;
            }
        }
        retVal.append( QUOTE );
        return retVal.toString();
    }
}
