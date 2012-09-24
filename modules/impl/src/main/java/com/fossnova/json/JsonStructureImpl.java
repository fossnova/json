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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.fossnova.json.JsonValue;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonWriter;

import com.fossnova.json.stream.JsonWriterImpl;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
abstract class JsonStructureImpl implements JsonValue {

    private static final long serialVersionUID = 1L;

    public final void writeTo( final JsonWriter jsonWriter ) throws IOException, JsonException {
        if ( jsonWriter == null ) {
            throw new NullPointerException( "JSON writer cannot be null" );
        }
        writeTo( ( JsonWriterImpl ) jsonWriter );
    }

    protected abstract void writeTo( final JsonWriterImpl jsonWriter ) throws IOException, JsonException;

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
        return new JsonBooleanImpl( value );
    }

    protected final JsonNumberImpl toJsonNumber( final Number value ) {
        if ( value == null ) {
            return null;
        }
        if ( value instanceof Byte ) {
            return new JsonNumberImpl( value.byteValue() );
        }
        if ( value instanceof Short ) {
            return new JsonNumberImpl( value.shortValue() );
        }
        if ( value instanceof Integer ) {
            return new JsonNumberImpl( value.intValue() );
        }
        if ( value instanceof Long ) {
            return new JsonNumberImpl( value.longValue() );
        }
        if ( value instanceof Float ) {
            return new JsonNumberImpl( value.floatValue() );
        }
        if ( value instanceof Double ) {
            return new JsonNumberImpl( value.doubleValue() );
        }
        if ( value instanceof BigInteger ) {
            return new JsonNumberImpl( ( BigInteger ) value );
        }
        if ( value instanceof BigDecimal ) {
            return new JsonNumberImpl( ( BigDecimal ) value );
        }
        throw new IllegalStateException();
    }

    protected final Collection< JsonValue > toJsonValuesCollection( final Collection< ? > values ) {
        if ( values == null ) return null;
        final HashSet< JsonValue > jsonValues = new HashSet< JsonValue >();
        final Iterator< ? > i = values.iterator();
        Object value = null;
        while ( i.hasNext() ) {
            value = i.next();
            if ( value instanceof String ) {
                jsonValues.add( toJsonString( ( String ) value ) );
            } else if ( value instanceof Number ) {
                jsonValues.add( toJsonNumber( ( Number ) value ) );
            } else if ( value instanceof Boolean ) {
                jsonValues.add( toJsonBoolean( ( Boolean ) value ) );
            } else {
                jsonValues.add( ( JsonValue ) value );
            }
        }
        return jsonValues;
    }

    @Override
    public JsonStructureImpl clone() {
        throw new UnsupportedOperationException();
    }
}
