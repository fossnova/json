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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.JsonStructure;
import org.fossnova.json.stream.JsonWriter;

import com.fossnova.json.stream.JsonWriterImpl;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
abstract class JsonStructureImpl implements JsonStructure {

    public final void writeTo( final JsonWriter jsonWriter ) throws IOException {
        if ( jsonWriter == null ) {
            throw new IllegalArgumentException( "JSON writer cannot be null" );
        }
        writeTo( (JsonWriterImpl) jsonWriter );
    }

    protected abstract void writeTo( final JsonWriterImpl jsonWriter ) throws IOException;

    protected final JsonStringImpl toJsonString( final String value ) {
        if ( value == null ) {
            return null;
        }
        return new JsonStringImpl( value );
    }

    protected final JsonBooleanImpl toJsonBoolean( final Boolean value ) {
        if ( value == null ) {
            return null;
        }
        return value.booleanValue() ? JsonBooleanImpl.TRUE : JsonBooleanImpl.FALSE;
    }

    protected final JsonNumberImpl toJsonNumber( final Number value ) {
        if ( value == null ) {
            return null;
        }
        if ( value instanceof Byte ) {
            return new JsonNumberImpl( ( Byte ) value.byteValue() );
        }
        if ( value instanceof Short ) {
            return new JsonNumberImpl( ( Short ) value.shortValue() );
        }
        if ( value instanceof Integer ) {
            return new JsonNumberImpl( ( Integer ) value.intValue() );
        }
        if ( value instanceof Long ) {
            return new JsonNumberImpl( ( Long ) value.longValue() );
        }
        if ( value instanceof Float ) {
            return new JsonNumberImpl( ( Float ) value.floatValue() );
        }
        if ( value instanceof Double ) {
            return new JsonNumberImpl( ( Double ) value.doubleValue() );
        }
        if ( value instanceof BigInteger ) {
            return new JsonNumberImpl( ( BigInteger ) value );
        }
        if ( value instanceof BigDecimal ) {
            return new JsonNumberImpl( ( BigDecimal ) value );
        }
        throw new IllegalStateException();
    }
}
