/*
 * Copyright (c) 2012-2020, FOSS Nova Software foundation (FNSF),
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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * JSON reader.
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 * @see JsonStreamFactory
 */
public interface JsonReader extends AutoCloseable {

    /**
     * Detects if there is next JSON parsing event available.
     * Users should call this method before calling {@link #next()} method.
     * @return true if there are more JSON parsing events, false otherwise
     */
    boolean hasNext();

    /**
     * Returns next JSON parsing event.
     * Users should call {@link #hasNext()} before calling this method.
     * @return JsonEvent next event
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    JsonEvent next() throws IOException, JsonException;

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>object start</code> token, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to JSON object start token, false otherwise
     */
    boolean isObjectStart();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>object end</code> token, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to JSON object end token, false otherwise
     */
    boolean isObjectEnd();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>array start</code> token, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to JSON array start token, false otherwise
     */
    boolean isArrayStart();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>array end</code> token, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to JSON array end token, false otherwise
     */
    boolean isArrayEnd();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>null</code> token, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to JSON null token, false otherwise
     */
    boolean isNull();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>string</code>, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor position points to JSON string, false otherwise
     */
    boolean isString();

    /**
     * Converts available context data to <code>String</code>.
     * Users have to call {@link #next()} and should call {@link #isString()} before calling this method.
     * @return string the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to JSON String
     */
    String getString();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>boolean</code> token, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor points to JSON boolean tokens, false otherwise
     */
    boolean isBoolean();

    /**
     * Converts available context data to <code>boolean</code>.
     * Users have to call {@link #next()} and should call {@link #isBoolean()} before calling this method.
     * @return boolean value the parsing cursor is pointing to
     * @exception IllegalStateException if cursor isn't pointing to JSON boolean token
     */
    boolean getBoolean();

    /**
     * Returns <code>true</code> if current JSON parsing event is JSON <code>number</code>, false otherwise.
     * Users have to call {@link #next()} before calling this method.
     * @return true if the parsing cursor points to JSON number, false otherwise
     */
    boolean isNumber();

    /**
     * Converts available context data to <code>byte</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return byte value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>byte</code>
     */
    byte getByte();

    /**
     * Converts available context data to <code>short</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return short value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>short</code>
     */
    short getShort();

    /**
     * Converts available context data to <code>int</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return integer value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>int</code>
     */
    int getInt();

    /**
     * Converts available context data to <code>long</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return long value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>long</code>
     */
    long getLong();

    /**
     * Converts available context data to <code>float</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return float value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>float</code>
     */
    float getFloat();

    /**
     * Converts available context data to <code>double</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return double value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>double</code>
     */
    double getDouble();

    /**
     * Converts available context data to <code>BigInteger</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return BigInteger value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>BigInteger</code>
     */
    BigInteger getBigInteger();

    /**
     * Converts available context data to <code>BigDecimal</code>.
     * Users have to call {@link #next()} and should call {@link #isNumber()} before calling this method.
     * @return BigDecimal value the parsing cursor is pointing to
     * @exception NumberFormatException if JSON number is not convertible to <code>BigDecimal</code>
     */
    BigDecimal getBigDecimal();

    /**
     * Free resources associated with this reader. Closes underlying input stream or reader.
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    @Override
    void close() throws IOException, JsonException;

}
