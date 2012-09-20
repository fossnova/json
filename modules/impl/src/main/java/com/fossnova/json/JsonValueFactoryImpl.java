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

import static org.fossnova.json.stream.JsonEvent.ARRAY_END;
import static org.fossnova.json.stream.JsonEvent.ARRAY_START;
import static org.fossnova.json.stream.JsonEvent.BOOLEAN;
import static org.fossnova.json.stream.JsonEvent.NULL;
import static org.fossnova.json.stream.JsonEvent.NUMBER;
import static org.fossnova.json.stream.JsonEvent.OBJECT_END;
import static org.fossnova.json.stream.JsonEvent.OBJECT_START;
import static org.fossnova.json.stream.JsonEvent.STRING;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.JsonValue;
import org.fossnova.json.JsonValueFactory;
import org.fossnova.json.stream.JsonEvent;
import org.fossnova.json.stream.JsonReader;

import com.fossnova.json.stream.JsonReaderImpl;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonValueFactoryImpl extends JsonValueFactory {

    public JsonValueFactoryImpl() {
    }

    @Override
    public JsonObjectImpl newJsonObject() {
        return new JsonObjectImpl();
    }

    @Override
    public JsonArrayImpl newJsonArray() {
        return new JsonArrayImpl();
    }

    @Override
    public JsonBooleanImpl newJsonBoolean( final Boolean value ) {
        assertNotNullParameter( value );
        return new JsonBooleanImpl( value );
    }

    @Override
    public JsonNumberImpl newJsonNumber( final Number value ) {
        assertNotNullParameter( value );
        if ( value instanceof Byte ) {
            return new JsonNumberImpl( ( Byte ) value );
        } else if ( value instanceof Short ) {
            return new JsonNumberImpl( ( Short ) value );
        } else if ( value instanceof Integer ) {
            return new JsonNumberImpl( ( Integer ) value );
        } else if ( value instanceof Long ) {
            return new JsonNumberImpl( ( Long ) value );
        } else if ( value instanceof Float ) {
            return new JsonNumberImpl( ( Float ) value );
        } else if ( value instanceof Double ) {
            return new JsonNumberImpl( ( Double ) value );
        } else if ( value instanceof BigInteger ) {
            return new JsonNumberImpl( ( BigInteger ) value );
        } else if ( value instanceof BigDecimal ) {
            return new JsonNumberImpl( ( BigDecimal ) value );
        }
        throw new IllegalStateException();
    }

    @Override
    public JsonStringImpl newJsonString( final String value ) {
        assertNotNullParameter( value );
        return new JsonStringImpl( value );
    }

    @Override
    public JsonValue readFrom( final JsonReader jsonReader ) throws IOException {
        assertNotNullParameter( jsonReader );
        return readFrom( ( JsonReaderImpl ) jsonReader );
    }

    private JsonValue readFrom( final JsonReaderImpl jsonReader ) throws IOException {
        final JsonEvent jsonEvent = jsonReader.next();
        if ( jsonEvent == OBJECT_START ) {
            return readJsonObjectFrom( jsonReader );
        } else if ( jsonEvent == ARRAY_START ) {
            return readJsonArrayFrom( jsonReader );
        }
        throw new IllegalStateException( "JSON reader have to point to array or object" );
    }

    private JsonObjectImpl readJsonObjectFrom( final JsonReaderImpl jsonReader ) throws IOException {
        final JsonObjectImpl jsonObject = newJsonObject();
        JsonEvent jsonEvent = jsonReader.next();
        String jsonKey = null;
        JsonValue jsonValue = null;
        while ( jsonEvent != OBJECT_END ) {
            jsonKey = jsonReader.getString();
            jsonEvent = jsonReader.next();
            if ( jsonEvent == NULL ) {
                jsonValue = null;
            } else if ( jsonEvent == BOOLEAN ) {
                jsonValue = jsonReader.getBoolean() ? new JsonBooleanImpl( true ) : new JsonBooleanImpl( false );
            } else if ( jsonEvent == NUMBER ) {
                jsonValue = new JsonNumberImpl( jsonReader.getNumber() );
            } else if ( jsonEvent == STRING ) {
                jsonValue = new JsonStringImpl( jsonReader.getString() );
            } else if ( jsonEvent == OBJECT_START ) {
                jsonValue = readJsonObjectFrom( jsonReader );
            } else if ( jsonEvent == ARRAY_START ) {
                jsonValue = readJsonArrayFrom( jsonReader );
            } else {
                throw new IllegalStateException();
            }
            jsonObject.putInternal( jsonKey, jsonValue );
            jsonEvent = jsonReader.next();
        }
        return jsonObject;
    }

    private JsonArrayImpl readJsonArrayFrom( final JsonReaderImpl jsonReader ) throws IOException {
        final JsonArrayImpl jsonArray = newJsonArray();
        JsonEvent jsonEvent = jsonReader.next();
        JsonValue jsonValue = null;
        while ( jsonEvent != ARRAY_END ) {
            if ( jsonEvent == NULL ) {
                jsonValue = null;
            } else if ( jsonEvent == BOOLEAN ) {
                jsonValue = jsonReader.getBoolean() ? new JsonBooleanImpl( true ) : new JsonBooleanImpl( false );
            } else if ( jsonEvent == NUMBER ) {
                jsonValue = new JsonNumberImpl( jsonReader.getNumber() );
            } else if ( jsonEvent == STRING ) {
                jsonValue = new JsonStringImpl( jsonReader.getString() );
            } else if ( jsonEvent == OBJECT_START ) {
                jsonValue = readJsonObjectFrom( jsonReader );
            } else if ( jsonEvent == ARRAY_START ) {
                jsonValue = readJsonArrayFrom( jsonReader );
            } else {
                throw new IllegalStateException();
            }
            jsonArray.addInternal( jsonValue );
            jsonEvent = jsonReader.next();
        }
        return jsonArray;
    }

    private static void assertNotNullParameter( final Object o ) {
        if ( o == null ) {
            throw new IllegalArgumentException( "Parameter cannot be null" );
        }
    }
}
