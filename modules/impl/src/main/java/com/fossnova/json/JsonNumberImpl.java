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
package com.fossnova.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.JsonNumber;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonNumberImpl implements JsonNumber {

    private final String value;

    JsonNumberImpl( final String value ) {
        this.value = value;
    }

    JsonNumberImpl( final byte data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final short data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final int data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final long data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final float data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final double data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final BigInteger data ) {
        value = String.valueOf( data );
    }

    JsonNumberImpl( final BigDecimal data ) {
        value = String.valueOf( data );
    }

    public byte getByte() {
        return Byte.parseByte( value );
    }

    public short getShort() {
        return Short.parseShort( value );
    }

    public int getInt() {
        return Integer.parseInt( value );
    }

    public long getLong() {
        return Long.parseLong( value );
    }

    public float getFloat() {
        return Float.parseFloat( value );
    }

    public double getDouble() {
        return Double.parseDouble( value );
    }

    public BigInteger getBigInteger() {
        return new BigInteger( value );
    }

    public BigDecimal getBigDecimal() {
        return new BigDecimal( value );
    }

    // implemented java.lang.Object methods
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonNumberImpl ) ) return false;
        final JsonNumberImpl n = ( JsonNumberImpl ) o;
        return value.equals( n.value );
    }

    @Override
    public String toString() {
        return value;
    }
}
