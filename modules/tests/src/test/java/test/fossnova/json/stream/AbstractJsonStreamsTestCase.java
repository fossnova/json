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
package test.fossnova.json.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

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

import static org.fossnova.json.stream.JsonEvent.ARRAY_END;
import static org.fossnova.json.stream.JsonEvent.ARRAY_START;
import static org.fossnova.json.stream.JsonEvent.BOOLEAN;
import static org.fossnova.json.stream.JsonEvent.NULL;
import static org.fossnova.json.stream.JsonEvent.NUMBER;
import static org.fossnova.json.stream.JsonEvent.OBJECT_END;
import static org.fossnova.json.stream.JsonEvent.OBJECT_START;
import static org.fossnova.json.stream.JsonEvent.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
abstract class AbstractJsonStreamsTestCase {

    static void assertClosedState( final JsonWriter writer ) throws IOException, JsonException {
        writer.flush();
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeArrayStart();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeNull();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeString( "" );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeByte( ( byte ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeShort( ( short ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeFloat( 0.0F );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "JSON processing finished", e.getMessage() );
        }
    }

    static void assertObjectStartState( final JsonReader reader ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( OBJECT_START, reader.next() );
        assertTrue( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertFalse( reader.isString() );
        assertNotStringException( reader );
        assertNotNumberException( reader );
        assertNotBooleanException( reader );
    }

    static void assertObjectEndState( final JsonReader reader ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( OBJECT_END, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertTrue( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertFalse( reader.isString() );
        assertNotStringException( reader );
        assertNotNumberException( reader );
        assertNotBooleanException( reader );
    }

    static void assertArrayStartState( final JsonReader reader ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( ARRAY_START, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertTrue( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertFalse( reader.isString() );
        assertNotStringException( reader );
        assertNotNumberException( reader );
        assertNotBooleanException( reader );
    }

    static void assertArrayEndState( final JsonReader reader ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( ARRAY_END, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertTrue( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertFalse( reader.isString() );
        assertNotStringException( reader );
        assertNotNumberException( reader );
        assertNotBooleanException( reader );
    }

    static void assertFinalState( final JsonReader reader ) throws IOException, JsonException {
        assertFalse( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "No more JSON tokens available", e.getMessage() );
        }
    }

    static void assertClosedState( final JsonReader reader ) throws IOException, JsonException {
        try {
            reader.getBoolean();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getByte();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getShort();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getInt();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getLong();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getFloat();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getDouble();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getBigInteger();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getBigDecimal();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getString();
            fail();
        } catch ( final IllegalStateException e ) {}
        assertFalse(reader.hasNext());
        try {
            reader.next();
            fail();
        } catch ( final IllegalStateException e ) {
            assertEquals( "JSON reader have been closed", e.getMessage() );
        }
        assertFalse(reader.isArrayEnd());
        assertFalse(reader.isArrayStart());
        assertFalse(reader.isObjectEnd());
        assertFalse(reader.isObjectStart());
        assertFalse(reader.isNull());
        assertFalse(reader.isBoolean());
        assertFalse(reader.isNumber());
        assertFalse(reader.isString());
    }

    static void assertStringState( final JsonReader reader, final String expected ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( STRING, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertTrue( reader.isString() );
        assertEquals( expected, reader.getString() );
        assertNotNumberException( reader );
        assertNotBooleanException( reader );
    }

    static void assertJsonException( final JsonReader reader, final String expected ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        try {
            reader.next();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( expected, e.getMessage() );
        }
    }

    static void assertByteState( final JsonReader reader, final byte expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertEquals( expected, reader.getByte() );
    }

    static void assertShortState( final JsonReader reader, final short expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertEquals( expected, reader.getShort() );
    }

    static void assertIntState( final JsonReader reader, final int expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertEquals( expected, reader.getInt() );
    }

    static void assertLongState( final JsonReader reader, final long expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertEquals( expected, reader.getLong() );
    }

    static void assertBigIntegerState( final JsonReader reader, final BigInteger expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertEquals( expected, reader.getBigInteger() );
    }

    static void assertBigDecimalState( final JsonReader reader, final BigDecimal expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertEquals( expected, reader.getBigDecimal() );
    }

    static void assertFloatState( final JsonReader reader, final float expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertTrue( Math.abs( reader.getFloat() - expected ) <= 0.0000001 );
    }

    static void assertDoubleState( final JsonReader reader, final double expected ) throws IOException, JsonException {
        assertNumberState( reader );
        assertTrue( Math.abs( reader.getDouble() - expected ) <= 0.0000001 );
    }

    static void assertBooleanState( final JsonReader reader, final boolean expected ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( BOOLEAN, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertTrue( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertFalse( reader.isString() );
        assertEquals( expected, reader.getBoolean() );
        assertNotStringException( reader );
        assertNotNumberException( reader );
    }

    static void assertNullState( final JsonReader reader ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( NULL, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertTrue( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertFalse( reader.isNumber() );
        assertFalse( reader.isString() );
        assertNotStringException( reader );
        assertNotNumberException( reader );
        assertNotBooleanException( reader );
    }

    private static void assertNumberState( final JsonReader reader ) throws IOException, JsonException {
        assertTrue( reader.hasNext() );
        assertEquals( NUMBER, reader.next() );
        assertFalse( reader.isObjectStart() );
        assertFalse( reader.isObjectEnd() );
        assertFalse( reader.isArrayStart() );
        assertFalse( reader.isArrayEnd() );
        assertFalse( reader.isNull() );
        assertFalse( reader.isBoolean() );
        assertTrue( reader.isNumber() );
        assertFalse( reader.isString() );
        assertNotStringException( reader );
        assertNotBooleanException( reader );
    }

    private static void assertNotStringException( final JsonReader reader ) throws IOException {
        try {
            reader.getString();
            fail();
        } catch ( final IllegalStateException e ) {}
    }

    private static void assertNotBooleanException( final JsonReader reader ) throws IOException {
        try {
            reader.getBoolean();
            fail();
        } catch ( final IllegalStateException e ) {}
    }

    private static void assertNotNumberException( final JsonReader reader ) throws IOException {
        try {
            reader.getByte();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getShort();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getInt();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getLong();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getBigInteger();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getBigDecimal();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getFloat();
            fail();
        } catch ( final IllegalStateException e ) {}
        try {
            reader.getDouble();
            fail();
        } catch ( final IllegalStateException e ) {}
    }

    static JsonReader getJsonReader( final String data ) throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( data.getBytes() );
        return JsonStreamFactory.getInstance().newJsonReader( bais );
    }

    static JsonWriter getJsonWriter() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return JsonStreamFactory.getInstance().newJsonWriter( baos );
    }

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
        final JsonWriter jsonWriter = JsonStreamFactory.getInstance().newJsonWriter( baos );
        jsonObject.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    static String serializeJson( final JsonArray jsonArray ) throws IOException, JsonException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter jsonWriter = JsonStreamFactory.getInstance().newJsonWriter( baos );
        jsonArray.writeTo( jsonWriter );
        jsonWriter.close();
        return new String( baos.toByteArray() );
    }

    static JsonValue deserializeJson( final String jsonString ) throws IOException, JsonException {
        final ByteArrayInputStream bais = new ByteArrayInputStream( jsonString.getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.getInstance().newJsonReader( bais );
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
        return jsonFactory.readFrom( jsonReader );
    }

    static JsonArray createSimpleArray() {
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
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
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
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
        final JsonValueFactory jsonFactory = JsonValueFactory.getInstance();
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

    static void assertJsonString( final JsonObject o, final String key, final String expectedValue ) {
        final JsonValue jsonValue = o.get( key );
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

    static void assertJsonNumber( final JsonObject o, final String key, final int expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        assertTrue( jsonValue instanceof JsonNumber );
        final JsonNumber jsonNumber = ( JsonNumber ) jsonValue;
        assertEquals( expectedValue, jsonNumber.getInt() );
    }

    static void assertJsonNull( final JsonObject o, final String key ) {
        assertTrue( o.keySet().contains( key ) );
        assertNull( o.get( key ) );
    }
}
