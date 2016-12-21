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
package test.fossnova.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
abstract class AbstractJsonValuesTestCase {

    static void assertJsonSerializationRoundTrip( final JsonObject jsonObject ) throws IOException, JsonException {
        final String serializedJsonObject = jsonSerialization( jsonObject );
        final JsonValue deserializedJsonObject = jsonDeserialization( serializedJsonObject );
        assertEquals( jsonObject, deserializedJsonObject );
    }

    static void assertJsonSerializationRoundTrip( final JsonArray jsonArray ) throws IOException, JsonException {
        final String serializedJsonObject = jsonSerialization( jsonArray );
        final JsonValue deserializedJsonObject = jsonDeserialization( serializedJsonObject );
        assertEquals( jsonArray, deserializedJsonObject );
    }

    static void assertJavaSerializationRoundTrip( final JsonObject jsonObject ) throws IOException, ClassNotFoundException {
        final byte[] serializedJsonObject = javaSerialization( jsonObject );
        final JsonValue deserializedJsonObject = javaDeserialization( serializedJsonObject );
        assertEquals( jsonObject, deserializedJsonObject );
    }

    static void assertJavaSerializationRoundTrip( final JsonArray jsonArray ) throws IOException, ClassNotFoundException {
        final byte[] serializedJsonObject = javaSerialization( jsonArray );
        final JsonValue deserializedJsonObject = javaDeserialization( serializedJsonObject );
        assertEquals( jsonArray, deserializedJsonObject );
    }

    private static String jsonSerialization( final JsonObject jsonObject ) throws IOException, JsonException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.getInstance().newJsonWriter( baos );
        jsonObject.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    private static String jsonSerialization( final JsonArray jsonArray ) throws IOException, JsonException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.getInstance().newJsonWriter( baos );
        jsonArray.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    private static JsonValue jsonDeserialization( final String jsonString ) throws IOException, JsonException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( jsonString.getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.getInstance().newJsonReader( bais );
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
        return jsonFactory.readFrom( jsonReader );
    }

    private static JsonValue javaDeserialization( final byte[] jsonData ) throws IOException, ClassNotFoundException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( jsonData );
        final ObjectInputStream ois = new ObjectInputStream( bais );
        return ( JsonValue ) ois.readObject();
    }

    private static byte[] javaSerialization( final JsonObject jsonObject ) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( jsonObject );
        oos.close();
        return baos.toByteArray();
    }

    private static byte[] javaSerialization( final JsonArray jsonArray ) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( jsonArray );
        oos.close();
        return baos.toByteArray();
    }

    static JsonArray createSimpleArray() {
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = jsonFactory.newJsonArray();
        jsonArray.addNull();
        jsonArray.add( true );
        jsonArray.add( 0 );
        jsonArray.add( "foo" );
        jsonArray.add( jsonFactory.newJsonArray() );
        jsonArray.add( jsonFactory.newJsonObject() );
        return jsonArray;
    }

    static JsonObject createSimpleObject() {
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
        final JsonObject jsonObject = jsonFactory.newJsonObject();
        jsonObject.putNull( "1" );
        jsonObject.put( "2", true );
        jsonObject.put( "3", 1 );
        jsonObject.put( "4", "bar" );
        jsonObject.put( "5", jsonFactory.newJsonArray() );
        jsonObject.put( "6", jsonFactory.newJsonObject() );
        return jsonObject;
    }

    static JsonObject createComplexObject() {
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
        final JsonObject jsonObject = jsonFactory.newJsonObject();
        jsonObject.put( "1", "b1" );
        jsonObject.putNull( "2" );
        jsonObject.put( "3", true );
        jsonObject.put( "4", 1 );
        final JsonArray simpleArray = createSimpleArray();
        jsonObject.put( "5", simpleArray );
        final JsonObject simpleObject = createSimpleObject();
        jsonObject.put( "6", simpleObject );
        return jsonObject;
    }

    static JsonArray createComplexArray() {
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = jsonFactory.newJsonArray();
        jsonArray.addNull();
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
