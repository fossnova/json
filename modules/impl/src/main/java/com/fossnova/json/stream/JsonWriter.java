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
import static com.fossnova.json.stream.JsonConstants.COLON;
import static com.fossnova.json.stream.JsonConstants.COMMA;
import static com.fossnova.json.stream.JsonConstants.NULL;
import static com.fossnova.json.stream.JsonConstants.OBJECT_END;
import static com.fossnova.json.stream.JsonConstants.OBJECT_START;
import static com.fossnova.json.stream.Utils.encode;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonException;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonWriter implements org.fossnova.json.stream.JsonWriter {

    private JsonGrammarAnalyzer analyzer;

    private Writer out;

    private boolean closed;

    JsonWriter( final Writer out ) {
        this.out = out;
        analyzer = new JsonGrammarAnalyzer();
    }

    public void close() {
        analyzer = null;
        out = null;
        closed = true;
    }

    public void flush() throws IOException {
        ensureOpen();
        out.flush();
    }

    public JsonWriter writeObjectStart() throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.OBJECT_START );
        out.write( OBJECT_START );
        return this;
    }

    public JsonWriter writeObjectEnd() throws IOException, JsonException {
        ensureOpen();
        analyzer.push( JsonGrammarToken.OBJECT_END );
        out.write( OBJECT_END );
        return this;
    }

    public JsonWriter writeArrayStart() throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.ARRAY_START );
        out.write( ARRAY_START );
        return this;
    }

    public JsonWriter writeArrayEnd() throws IOException, JsonException {
        ensureOpen();
        analyzer.push( JsonGrammarToken.ARRAY_END );
        out.write( ARRAY_END );
        return this;
    }

    public JsonWriter writeString( final String data ) throws IOException, JsonException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.STRING );
        analyzer.pushString( data );
        out.write( encode( data ) );
        return this;
    }

    public JsonWriter writeNull() throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NULL );
        out.write( NULL );
        return this;
    }

    public JsonWriter writeBoolean( final boolean data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.BOOLEAN );
        out.write( String.valueOf( data ) );
        return this;
    }

    public JsonWriter writeByte( final byte data ) throws IOException, JsonException {
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeShort( final short data ) throws IOException, JsonException {
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeInt( final int data ) throws IOException, JsonException {
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeLong( final long data ) throws IOException, JsonException {
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeFloat( final float data ) throws IOException, JsonException {
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeDouble( final double data ) throws IOException, JsonException {
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeBigInteger( final BigInteger data ) throws IOException, JsonException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeBigDecimal( final BigDecimal data ) throws IOException, JsonException {
        if ( data == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
        return writeNumber( String.valueOf( data ) );
    }

    public JsonWriter writeNumber( final String data ) throws IOException, JsonException {
        ensureOpen();
        writeOptionalColonOrComma();
        analyzer.push( JsonGrammarToken.NUMBER );
        out.write( data );
        return this;
    }

    private void writeOptionalColonOrComma() throws IOException, JsonException {
        if ( analyzer.isColonExpected() ) {
            writeColon();
        } else if ( analyzer.isCommaExpected() ) {
            writeComma();
        }
    }

    private void writeColon() throws IOException, JsonException {
        analyzer.push( JsonGrammarToken.COLON );
        out.write( COLON );
    }

    private void writeComma() throws IOException, JsonException {
        analyzer.push( JsonGrammarToken.COMMA );
        out.write( COMMA );
    }

    private void ensureOpen() {
        if ( closed ) {
            throw new IllegalStateException( "JSON writer have been closed" );
        }
    }
}
