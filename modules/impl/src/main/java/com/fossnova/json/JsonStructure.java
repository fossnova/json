/*
 * Copyright (c) 2012-2017, FOSS Nova Software foundation (FNSF),
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
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.fossnova.json.JsonValue;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonStreamFactory;

import com.fossnova.json.stream.JsonWriter;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
abstract class JsonStructure implements JsonValue {

    private static final long serialVersionUID = 1L;

    public final void writeTo( final org.fossnova.json.stream.JsonWriter output ) throws IOException, JsonException {
        assertNotNullParameter( output );
        writeTo( ( JsonWriter ) output );
    }

    public final void writeTo( final Writer output ) throws IOException, JsonException {
        assertNotNullParameter( output );
        writeTo( JsonStreamFactory.getInstance().newJsonWriter( output ) );
    }

    public final void writeTo( final OutputStream output ) throws IOException, JsonException {
        assertNotNullParameter( output );
        writeTo( JsonStreamFactory.getInstance().newJsonWriter( output ) );
    }

    public final void writeTo( final OutputStream output, final Charset charset ) throws IOException, JsonException {
        assertNotNullParameter( output );
        assertNotNullParameter( charset );
        writeTo( JsonStreamFactory.getInstance().newJsonWriter( output, charset ) );
    }

    protected abstract void writeTo( final JsonWriter jsonWriter ) throws IOException, JsonException;

    final JsonString toJsonString( final String value ) {
        return value != null ? new JsonString( value ) : null;
    }

    final JsonBoolean toJsonBoolean( final Boolean value ) {
        return value != null ? new JsonBoolean( value ) : null;
    }

    final JsonNumber toJsonNumber( final Number value ) {
        if ( value instanceof Byte ) {
            return new JsonNumber( value.byteValue() );
        }
        if ( value instanceof Short ) {
            return new JsonNumber( value.shortValue() );
        }
        if ( value instanceof Integer ) {
            return new JsonNumber( value.intValue() );
        }
        if ( value instanceof Long ) {
            return new JsonNumber( value.longValue() );
        }
        if ( value instanceof Float ) {
            return new JsonNumber( value.floatValue() );
        }
        if ( value instanceof Double ) {
            return new JsonNumber( value.doubleValue() );
        }
        if ( value instanceof BigInteger ) {
            return new JsonNumber( ( BigInteger ) value );
        }
        if ( value instanceof BigDecimal ) {
            return new JsonNumber( ( BigDecimal ) value );
        }
        if ( value == null ) {
            return null;
        }
        throw new IllegalStateException();
    }

    final Collection< JsonValue > toJsonValuesCollection( final Collection< ? > values ) {
        if ( values == null ) return null;
        final HashSet< JsonValue > jsonValues = new HashSet<>();
        final Iterator< ? > i = values.iterator();
        Object value;
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
    public JsonStructure clone() {
        throw new UnsupportedOperationException();
    }

    private static void assertNotNullParameter( final Object o ) {
        if ( o == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
    }
}
