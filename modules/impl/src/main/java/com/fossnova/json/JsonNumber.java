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

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class JsonNumber implements org.fossnova.json.JsonNumber {

    private static final long serialVersionUID = 1L;

    private String value;

    JsonNumber( final String value ) {
        this.value = value;
    }

    JsonNumber( final byte value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final short value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final int value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final long value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final float value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final double value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final BigInteger value ) {
        this.value = String.valueOf( value );
    }

    JsonNumber( final BigDecimal value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setByte( final byte value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setShort( final short value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setInt( final int value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setLong( final long value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setFloat( final float value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setDouble( final double value ) {
        this.value = String.valueOf( value );
    }

    @Override
    public void setBigInteger( final BigInteger value ) {
        if ( value == null ) {
            throw new NullPointerException( "Parameter cannot be null " );
        }
        this.value = String.valueOf( value );
    }

    @Override
    public void setBigDecimal( final BigDecimal value ) {
        if ( value == null ) {
            throw new NullPointerException( "Parameter cannot be null " );
        }
        this.value = String.valueOf( value );
    }

    @Override
    public byte getByte() {
        return Byte.parseByte( value );
    }

    @Override
    public short getShort() {
        return Short.parseShort( value );
    }

    @Override
    public int getInt() {
        return Integer.parseInt( value );
    }

    @Override
    public long getLong() {
        return Long.parseLong( value );
    }

    @Override
    public float getFloat() {
        return Float.parseFloat( value );
    }

    @Override
    public double getDouble() {
        return Double.parseDouble( value );
    }

    @Override
    public BigInteger getBigInteger() {
        return new BigInteger( value );
    }

    @Override
    public BigDecimal getBigDecimal() {
        return new BigDecimal( value );
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonNumber ) ) return false;
        final JsonNumber n = ( JsonNumber ) o;
        return value.equals( n.value );
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public JsonNumber clone() {
        return new JsonNumber( value );
    }
}
