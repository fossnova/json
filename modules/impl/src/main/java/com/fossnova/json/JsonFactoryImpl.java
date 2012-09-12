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
import java.io.UnsupportedEncodingException;

import org.fossnova.json.JsonStructure;
import org.fossnova.json.JsonFactory;
import org.fossnova.json.JsonValue;
import org.fossnova.json.stream.JsonEvent;

import com.fossnova.json.stream.JsonReaderImpl;
import com.fossnova.json.stream.JsonStreamFactoryImpl;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonFactoryImpl extends JsonFactory {

    @Override
    public JsonObjectImpl newJsonObject() {
        return new JsonObjectImpl();
    }

    @Override
    public JsonArrayImpl newJsonArray() {
        return new JsonArrayImpl();
    }

    @Override
    public JsonStructure readFrom( final Reader reader ) throws IOException {
        final JsonReaderImpl jsonReader = new JsonStreamFactoryImpl().newJsonReader( reader );
        return readFrom( jsonReader );
    }

    @Override
    public JsonStructure readFrom( final InputStream stream ) throws UnsupportedEncodingException, IOException {
        final JsonReaderImpl jsonReader = new JsonStreamFactoryImpl().newJsonReader( stream );
        return readFrom( jsonReader );
    }

    @Override
    public JsonStructure readFrom( final InputStream stream, final String charsetName ) throws UnsupportedEncodingException, IOException {
        final JsonReaderImpl jsonReader = new JsonStreamFactoryImpl().newJsonReader( stream, charsetName );
        return readFrom( jsonReader );
    }

    private JsonStructure readFrom( final JsonReaderImpl jsonReader ) throws IOException {
        if ( jsonReader.next() == OBJECT_START ) {
            return readJsonObjectFrom( jsonReader );
        } else {
            return readJsonArrayFrom( jsonReader );
        }
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
                jsonValue = jsonReader.getBoolean() ? JsonBooleanImpl.TRUE : JsonBooleanImpl.FALSE;
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
                jsonValue = jsonReader.getBoolean() ? JsonBooleanImpl.TRUE : JsonBooleanImpl.FALSE;
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
}
