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
package test.fossnova.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.fossnova.json.JsonArray;
import org.fossnova.json.JsonBoolean;
import org.fossnova.json.JsonNumber;
import org.fossnova.json.JsonObject;
import org.fossnova.json.JsonString;
import org.fossnova.json.JsonValue;
import org.fossnova.json.JsonValueFactory;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonReader;
import org.fossnova.json.stream.JsonStreamFactory;
import org.fossnova.json.stream.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
abstract class AbstractJsonValuesTestCase {

    static void assertRoundTrip( final JsonObject jsonObject ) throws IOException, JsonException {
        final String serializedJsonObject = serializeJson( jsonObject );
        final JsonValue deserializedJsonObject = deserializeJson( serializedJsonObject );
        assertEquals( jsonObject, deserializedJsonObject );
    }

    static void assertRoundTrip( final JsonArray jsonArray ) throws IOException, JsonException {
        final String serializedJsonObject = serializeJson( jsonArray );
        final JsonValue deserializedJsonObject = deserializeJson( serializedJsonObject );
        assertEquals( jsonArray, deserializedJsonObject );
    }

    static String serializeJson( final JsonObject jsonObject ) throws IOException, JsonException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.newInstance().newJsonWriter( baos );
        jsonObject.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    static String serializeJson( final JsonArray jsonArray ) throws IOException, JsonException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.newInstance().newJsonWriter( baos );
        jsonArray.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    static JsonValue deserializeJson( final String jsonString ) throws IOException, JsonException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( jsonString.getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.newInstance().newJsonReader( bais );
        final JsonValueFactory jsonFactory = JsonValueFactory.newInstance();
        return jsonFactory.readFrom( jsonReader );
    }

    static JsonArray createSimpleArray() {
        final JsonValueFactory jsonFactory = JsonValueFactory.newInstance();
        final JsonArray jsonArray = jsonFactory.newJsonArray();
        jsonArray.add( ( String ) null );
        jsonArray.add( true );
        jsonArray.add( 0 );
        jsonArray.add( "foo" );
        jsonArray.add( jsonFactory.newJsonArray() );
        jsonArray.add( jsonFactory.newJsonObject() );
        return jsonArray;
    }

    static JsonObject createSimpleObject() {
        final JsonValueFactory jsonFactory = JsonValueFactory.newInstance();
        final JsonObject jsonObject = jsonFactory.newJsonObject();
        jsonObject.put( "1", ( String ) null );
        jsonObject.put( "2", true );
        jsonObject.put( "3", 1 );
        jsonObject.put( "4", "bar" );
        jsonObject.put( "5", jsonFactory.newJsonArray() );
        jsonObject.put( "6", jsonFactory.newJsonObject() );
        return jsonObject;
    }

    static JsonObject createComplexObject() {
        final JsonValueFactory jsonFactory = JsonValueFactory.newInstance();
        final JsonObject jsonObject = jsonFactory.newJsonObject();
        jsonObject.put( "1", "b1" );
        jsonObject.put( "2", ( String ) null );
        jsonObject.put( "3", true );
        jsonObject.put( "4", 1 );
        final JsonArray simpleArray = createSimpleArray();
        jsonObject.put( "5", simpleArray );
        final JsonObject simpleObject = createSimpleObject();
        jsonObject.put( "6", simpleObject );
        return jsonObject;
    }

    static JsonArray createComplexArray() {
        final JsonValueFactory jsonFactory = JsonValueFactory.newInstance();
        final JsonArray jsonArray = jsonFactory.newJsonArray();
        jsonArray.add( ( Boolean ) null );
        jsonArray.add( false );
        jsonArray.add( 1 );
        jsonArray.add( "2" );
        final JsonArray simpleArray = createSimpleArray();
        jsonArray.add( simpleArray );
        final JsonObject simpleObject = createSimpleObject();
        jsonArray.add( simpleObject );
        return jsonArray;
    }

    static void assertJsonString( final JsonObject o, final String key, final String expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonString );
        final JsonString jsonString = ( JsonString ) jsonValue;
        assertEquals( expectedValue, jsonString.getString() );
    }

    static void assertJsonString( final JsonArray a, final int index, final String expectedValue ) {
        final JsonValue jsonValue = a.get( index );
        assertTrue( jsonValue instanceof JsonString );
        final JsonString jsonString = ( JsonString ) jsonValue;
        assertEquals( expectedValue, jsonString.getString() );
    }

    static void assertJsonBoolean( final JsonObject o, final String key, final boolean expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonBoolean );
        final JsonBoolean jsonBoolean = ( JsonBoolean ) jsonValue;
        assertEquals( expectedValue, jsonBoolean.getBoolean() );
    }

    static void assertJsonBoolean( final JsonArray a, final int index, final boolean expectedValue ) {
        final JsonValue jsonValue = a.get( index );
        assertTrue( jsonValue instanceof JsonBoolean );
        final JsonBoolean jsonBoolean = ( JsonBoolean ) jsonValue;
        assertEquals( expectedValue, jsonBoolean.getBoolean() );
    }

    static void assertJsonNumber( final JsonObject o, final String key, final int expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonNumber );
        final JsonNumber jsonNumber = ( JsonNumber ) jsonValue;
        assertEquals( expectedValue, jsonNumber.getInt() );
    }

    static void assertJsonNumber( final JsonArray a, final int index, final int expectedValue ) {
        final JsonValue jsonValue = a.get( index );
        assertTrue( jsonValue instanceof JsonNumber );
        final JsonNumber jsonNumber = ( JsonNumber ) jsonValue;
        assertEquals( expectedValue, jsonNumber.getInt() );
    }

    static void assertJsonNull( final JsonObject o, final String key ) {
        assertTrue( o.keySet().contains( key ) );
        assertNull( o.get( key ) );
    }

    static void assertJsonNull( final JsonArray a, final int index ) {
        assertNull( a.get( index ) );
    }
}
