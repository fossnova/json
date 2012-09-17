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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public interface JsonNumber extends JsonValue {

    void setByte( byte b );

    byte getByte();

    void setShort( short s );

    short getShort();

    void setInt( int i );

    int getInt();

    void setLong( long l );

    long getLong();

    void setFloat( float f );

    float getFloat();

    void setDouble( double d );

    double getDouble();

    void setBigInteger( BigInteger bi );

    BigInteger getBigInteger();

    void setBigDecimal( BigDecimal bd );

    BigDecimal getBigDecimal();

    JsonNumber clone();
}
