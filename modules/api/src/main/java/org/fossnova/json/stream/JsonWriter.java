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
package org.fossnova.json.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * JSON writer.
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 * @see JsonStreamFactory
 */
public interface JsonWriter extends Flushable, Closeable {

    /**
     * Writes JSON <code>object start</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeObjectStart() throws IOException, JsonException;

    /**
     * Writes JSON <code>object end</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeObjectEnd() throws IOException, JsonException;

    /**
     * Writes JSON <code>array start</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeArrayStart() throws IOException, JsonException;

    /**
     * Writes JSON <code>array end</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeArrayEnd() throws IOException, JsonException;

    /**
     * Writes JSON <code>null</code> token.
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeNull() throws IOException, JsonException;

    /**
     * Writes JSON <code>string</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeString( String data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>true</code> or <code>false</code> token.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeBoolean( boolean data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeByte( byte data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeShort( short data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeInt( int data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeLong( long data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeBigInteger( BigInteger data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeBigDecimal( BigDecimal data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeFloat( float data ) throws IOException, JsonException;

    /**
     * Writes JSON <code>number</code>.
     * @param data to encode
     * @return this writer instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonWriter writeDouble( double data ) throws IOException, JsonException;

    /**
     * Writes all cached data.
     * @throws IOException if some I/O error occurs
     */
    @Override
    void flush() throws IOException;

    /**
     * Free resources associated with this writer. Never closes underlying input stream or writer.
     */
    @Override
    void close();
}
