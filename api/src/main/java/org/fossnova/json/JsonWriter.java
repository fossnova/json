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
package org.fossnova.json;

import java.io.Closeable;
import java.io.IOException;

/**
 * JSON writer.
 * 
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 * @see JsonFactory
 * @see JsonBuilder
 * @see JsonReader
 */
public interface JsonWriter extends Closeable {

    /**
     * Writes JSON <code>object start</code> token.
     *
     * @throws IOException if I/O error occurs
     */
    void writeObjectStart() throws IOException;

    /**
     * Writes JSON <code>object end</code> token.
     * 
     * @throws IOException if I/O error occurs
     */
    void writeObjectEnd() throws IOException;

    /**
     * Writes JSON <code>array start</code> token.
     *
     * @throws IOException if I/O error occurs
     */
    void writeArrayStart() throws IOException;

    /**
     * Writes JSON <code>array end</code> token.
     * 
     * @throws IOException if I/O error occurs
     */
    void writeArrayEnd() throws IOException;

    /**
     * Writes JSON <code>null</code> token.
     * 
     * @throws IOException if I/O error occurs
     */
    void writeNull() throws IOException;

    /**
     * Writes JSON <code>string</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeString( String data ) throws IOException;

    /**
     * Writes JSON <code>true</code> or <code>false</code> token.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeBoolean( boolean data ) throws IOException;

    /**
     * Writes JSON <code>number</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeByte( byte data ) throws IOException;

    /**
     * Writes JSON <code>number</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeShort( short data ) throws IOException;

    /**
     * Writes JSON <code>number</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeInt( int data ) throws IOException;

    /**
     * Writes JSON <code>number</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeLong( long data ) throws IOException;

    /**
     * Writes JSON <code>number</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeFloat( float data ) throws IOException;

    /**
     * Writes JSON <code>number</code>.
     * 
     * @param data to encode
     * @throws IOException if I/O error occurs
     */
    void writeDouble( double data ) throws IOException;

    /**
     * Writes all cached data.
     * 
     * @throws IOException if I/O error occurs
     */
    void flush() throws IOException;

    /**
     * Free resources associated with this writer. Never closes underlying stream.
     *
     * @throws IOException if I/O error occurs
     */
    void close();
}
