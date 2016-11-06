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
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;

import org.fossnova.json.JsonValue;
import org.fossnova.json.stream.JsonEvent;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonStreamFactory;

import com.fossnova.json.stream.JsonReader;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class JsonValueFactory extends org.fossnova.json.JsonValueFactory {

    public JsonValueFactory() {
    }

    @Override
    public JsonObject newJsonObject() {
        return new JsonObject();
    }

    @Override
    public JsonArray newJsonArray() {
        return new JsonArray();
    }

    @Override
    public JsonBoolean newJsonBoolean( final Boolean value ) {
        assertNotNullParameter( value );
        return new JsonBoolean( value );
    }

    @Override
    public JsonNumber newJsonNumber( final Number value ) {
        assertNotNullParameter( value );
        if ( value instanceof Byte ) {
            return new JsonNumber( ( Byte ) value );
        } else if ( value instanceof Short ) {
            return new JsonNumber( ( Short ) value );
        } else if ( value instanceof Integer ) {
            return new JsonNumber( ( Integer ) value );
        } else if ( value instanceof Long ) {
            return new JsonNumber( ( Long ) value );
        } else if ( value instanceof Float ) {
            return new JsonNumber( ( Float ) value );
        } else if ( value instanceof Double ) {
            return new JsonNumber( ( Double ) value );
        } else if ( value instanceof BigInteger ) {
            return new JsonNumber( ( BigInteger ) value );
        } else if ( value instanceof BigDecimal ) {
            return new JsonNumber( ( BigDecimal ) value );
        }
        throw new IllegalStateException();
    }

    @Override
    public JsonString newJsonString( final String value ) {
        assertNotNullParameter( value );
        return new JsonString( value );
    }

    @Override
    public JsonValue readFrom( final org.fossnova.json.stream.JsonReader input ) throws IOException, JsonException {
        assertNotNullParameter( input );
        return readFrom( ( JsonReader ) input );
    }

    @Override
    public JsonValue readFrom( final Reader input ) throws IOException, JsonException {
        assertNotNullParameter( input );
        return readFrom( JsonStreamFactory.getInstance().newJsonReader( input ) );
    }

    @Override
    public JsonValue readFrom( final String input ) throws IOException, JsonException {
        assertNotNullParameter( input );
        return readFrom( new StringReader( input ) );
    }

    @Override
    public JsonValue readFrom( final InputStream input ) throws IOException, JsonException {
        assertNotNullParameter( input );
        return readFrom( JsonStreamFactory.getInstance().newJsonReader( input ) );
    }

    @Override
    public JsonValue readFrom( final InputStream input, final Charset charset ) throws IOException, JsonException {
        assertNotNullParameter( input );
        assertNotNullParameter( charset );
        return readFrom( JsonStreamFactory.getInstance().newJsonReader( input, charset ) );
    }

    private JsonValue readFrom( final JsonReader jsonReader ) throws IOException, JsonException {
        final JsonEvent jsonEvent = jsonReader.next();
        if ( jsonEvent == OBJECT_START ) {
            return readJsonObjectFrom( jsonReader );
        } else if ( jsonEvent == ARRAY_START ) {
            return readJsonArrayFrom( jsonReader );
        } else if ( jsonEvent == STRING ) {
            return new JsonString( jsonReader.getString() );
        } else if ( jsonEvent == NUMBER ) {
            return new JsonNumber( jsonReader.getNumber() );
        } else if ( jsonEvent == BOOLEAN ) {
            return new JsonBoolean( jsonReader.getBoolean() );
        } else if ( jsonEvent == NULL ) {
            return null;
        }
        throw new IllegalStateException();
    }

    private JsonObject readJsonObjectFrom( final JsonReader jsonReader ) throws IOException, JsonException {
        final JsonObject jsonObject = newJsonObject();
        JsonEvent jsonEvent = jsonReader.next();
        String jsonKey;
        JsonValue jsonValue;
        while ( jsonEvent != OBJECT_END ) {
            jsonKey = jsonReader.getString();
            jsonEvent = jsonReader.next();
            if ( jsonEvent == STRING ) {
                jsonValue = new JsonString( jsonReader.getString() );
            } else if ( jsonEvent == NUMBER ) {
                jsonValue = new JsonNumber( jsonReader.getNumber() );
            } else if ( jsonEvent == BOOLEAN ) {
                jsonValue = new JsonBoolean( jsonReader.getBoolean() );
            } else if ( jsonEvent == NULL ) {
                jsonValue = null;
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

    private JsonArray readJsonArrayFrom( final JsonReader jsonReader ) throws IOException, JsonException {
        final JsonArray jsonArray = newJsonArray();
        JsonEvent jsonEvent = jsonReader.next();
        JsonValue jsonValue;
        while ( jsonEvent != ARRAY_END ) {
            if ( jsonEvent == STRING ) {
                jsonValue = new JsonString( jsonReader.getString() );
            } else if ( jsonEvent == NUMBER ) {
                jsonValue = new JsonNumber( jsonReader.getNumber() );
            } else if ( jsonEvent == BOOLEAN ) {
                jsonValue = new JsonBoolean( jsonReader.getBoolean() );
            } else if ( jsonEvent == NULL ) {
                jsonValue = null;
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
            throw new NullPointerException( "Parameter cannot be null" );
        }
    }
}
