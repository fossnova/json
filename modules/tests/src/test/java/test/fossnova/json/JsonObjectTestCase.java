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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

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
import org.fossnova.json.stream.JsonReader;
import org.fossnova.json.stream.JsonStreamFactory;
import org.fossnova.json.stream.JsonWriter;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonObjectTestCase extends AbstractJsonTestCase {

    private static final JsonArray SIMPLE_ARRAY = createSimpleArray();

    private static final JsonObject SIMPLE_OBJECT = createSimpleObject();

    private static final JsonObject COMPLEX_OBJECT = createComplexObject();

    @Test
    public void complexObject() throws IOException {
        assertJsonString( COMPLEX_OBJECT, "1", "b1" );
        assertJsonNull( COMPLEX_OBJECT, "2" );
        assertJsonBoolean( COMPLEX_OBJECT, "3", true );
        assertJsonNumber( COMPLEX_OBJECT, "4", 1 );
        assertTrue( COMPLEX_OBJECT.containsKey( "4" ) );
        assertTrue( COMPLEX_OBJECT.containsValue( 1 ) );
        assertTrue( COMPLEX_OBJECT.containsValue( true ) );
        assertTrue( COMPLEX_OBJECT.containsValue( ( Boolean ) null ) );
        assertTrue( COMPLEX_OBJECT.containsValue( SIMPLE_ARRAY ) );
        assertTrue( COMPLEX_OBJECT.containsValue( SIMPLE_OBJECT ) );
    }

    @Test
    public void createJsonObjectFromStream() throws IOException {
        final JsonValueFactory jsonStructureFactory = JsonValueFactory.newInstance();
        final ByteArrayInputStream bais = new ByteArrayInputStream( "{\"1\":\"b1\",\"2\":null,\"3\":true,\"4\":false,\"5\":1,\"6\":[],\"7\":{}}".getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.newInstance().newJsonReader( bais );
        final JsonObject o = ( JsonObject ) jsonStructureFactory.readFrom( jsonReader );
        final JsonArray a = jsonStructureFactory.newJsonArray();
        final JsonObject o1 = jsonStructureFactory.newJsonObject();
        assertJsonString( o, "1", "b1" );
        assertJsonNull( o, "2" );
        assertJsonBoolean( o, "3", true );
        assertJsonBoolean( o, "4", false );
        assertJsonNumber( o, "5", 1 );
        assertTrue( o.containsKey( "4" ) );
        assertTrue( o.containsValue( 1 ) );
        assertTrue( o.containsValue( true ) );
        assertTrue( o.containsValue( false ) );
        assertTrue( o.containsValue( ( Boolean ) null ) );
        assertTrue( o.containsValue( a ) );
        assertTrue( o.containsValue( o1 ) );
    }

    @Test
    public void roundTrip() throws IOException {
        assertRoundTrip( SIMPLE_ARRAY );
        assertRoundTrip( SIMPLE_OBJECT );
        assertRoundTrip( COMPLEX_OBJECT );
    }

    private static void assertRoundTrip( final JsonObject jsonObject ) throws IOException {
        final String serializedJsonObject = serializeJson( jsonObject );
        final JsonValue deserializedJsonObject = deserializeJson( serializedJsonObject );
        assertEquals( jsonObject, deserializedJsonObject );
    }

    private static void assertRoundTrip( final JsonArray jsonArray ) throws IOException {
        final String serializedJsonObject = serializeJson( jsonArray );
        final JsonValue deserializedJsonObject = deserializeJson( serializedJsonObject );
        assertEquals( jsonArray, deserializedJsonObject );
    }

    private static String serializeJson( final JsonObject jsonObject ) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.newInstance().newJsonWriter( baos );
        jsonObject.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    private static String serializeJson( final JsonArray jsonArray ) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.newInstance().newJsonWriter( baos );
        jsonArray.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    private static JsonValue deserializeJson( final String jsonString ) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( jsonString.getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.newInstance().newJsonReader( bais );
        final JsonValueFactory jsonFactory = JsonValueFactory.newInstance();
        return jsonFactory.readFrom( jsonReader );
    }

    private static JsonArray createSimpleArray() {
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

    private static JsonObject createSimpleObject() {
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

    private static JsonObject createComplexObject() {
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

    private static void assertJsonString( final JsonObject o, final String key, final String expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonString );
        final JsonString jsonString = ( JsonString ) jsonValue;
        assertEquals( expectedValue, jsonString.getString() );
    }

    private static void assertJsonBoolean( final JsonObject o, final String key, final boolean expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonBoolean );
        final JsonBoolean jsonBoolean = ( JsonBoolean ) jsonValue;
        assertEquals( expectedValue, jsonBoolean.getBoolean() );
    }

    private static void assertJsonNumber( final JsonObject o, final String key, final int expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonNumber );
        final JsonNumber jsonNumber = ( JsonNumber ) jsonValue;
        assertEquals( expectedValue, jsonNumber.getInt() );
    }

    private static void assertJsonNull( final JsonObject o, final String key ) {
        assertTrue( o.keySet().contains( key ) );
        assertNull( o.get( key ) );
    }
}
