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
package org.fossnova.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * JSON number.
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 * @see JsonValue
 * @see JsonValueFactory
 */
public interface JsonNumber extends JsonValue {

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setByte( byte value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>byte</code>
     */
    byte getByte();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setShort( short value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>short</code>
     */
    short getShort();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setInt( int value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>int</code>
     */
    int getInt();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setLong( long value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>long</code>
     */
    long getLong();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setFloat( float value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>float</code>
     */
    float getFloat();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setDouble( double value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>double</code>
     */
    double getDouble();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setBigInteger( BigInteger value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>BigInteger</code>
     */
    BigInteger getBigInteger();

    /**
     * Sets new number value.
     * @param value new number value
     */
    void setBigDecimal( BigDecimal value );

    /**
     * Gets number value.
     * @return number value
     * @exception NumberFormatException if JSON number is not convertible to <code>BigDecimal</code>
     */
    BigDecimal getBigDecimal();

    /**
     * Clones this JSON number.
     * @return new JSON number clone.
     */
    @Override
    JsonNumber clone();
}
