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
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

import org.fossnova.json.JsonArray;
import org.fossnova.json.JsonObject;
import org.fossnova.json.JsonValue;
import org.fossnova.json.JsonValueFactory;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonReader;
import org.fossnova.json.stream.JsonStreamFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class JsonObjectTestCase extends AbstractJsonValuesTestCase {

    @Test
    public void jsonStructureFactoryNewObject() {
        final JsonObject complexObject = createComplexObject();
        assertJsonString( complexObject, "1", "b1" );
        assertJsonNull( complexObject, "2" );
        assertJsonBoolean( complexObject, "3", true );
        assertJsonNumber( complexObject, "4", 1 );
        assertTrue( complexObject.containsKey( "4" ) );
        assertTrue( complexObject.containsValue( 1 ) );
        assertTrue( complexObject.containsValue( true ) );
        assertTrue( complexObject.containsValue( ( Boolean ) null ) );
        assertTrue( complexObject.containsNullValue() );
        assertTrue( complexObject.containsValue( createSimpleArray() ) );
        assertTrue( complexObject.containsValue( createSimpleObject() ) );
    }

    @Test
    public void jsonStructureFactoryReadFrom() throws IOException, JsonException {
        final JsonValueFactory jsonValueFactory = JsonValueFactory.getInstance();
        final ByteArrayInputStream bais = new ByteArrayInputStream( "{\"1\":\"b1\",\"2\":null,\"3\":true,\"4\":false,\"5\":1,\"6\":[],\"7\":{}}".getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.getInstance().newJsonReader( bais );
        final JsonObject o = ( JsonObject ) jsonValueFactory.readFrom( jsonReader );
        final JsonArray a = jsonValueFactory.newJsonArray();
        final JsonObject o1 = jsonValueFactory.newJsonObject();
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
        assertTrue( o.containsNullValue() );
        assertTrue( o.containsValue( a ) );
        assertTrue( o.containsValue( o1 ) );
    }

    @Test
    public void jsonSerializationRoundTrip() throws IOException, JsonException {
        assertJsonSerializationRoundTrip( createSimpleObject() );
        assertJsonSerializationRoundTrip( createComplexObject() );
    }

    @Test
    public void javaSerializationRoundTrip() throws IOException, ClassNotFoundException {
        assertJavaSerializationRoundTrip( createSimpleObject() );
        assertJavaSerializationRoundTrip( createComplexObject() );
    }

    @Test
    public void containsKey() {
        final JsonObject jsonObject = createSimpleObject();
        assertTrue( jsonObject.containsKey( "1" ) );
        assertFalse( jsonObject.containsKey( "7" ) );
        assertTrue( jsonObject.containsKey( ( Object ) "1" ) );
        assertFalse( jsonObject.containsKey( ( Object ) "7" ) );
    }

    @Test
    public void containsValue() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonObject jsonObject = createSimpleObject();
        assertTrue( jsonObject.containsNullValue() );
        assertTrue( jsonObject.containsValue( ( String ) null ) );
        assertTrue( jsonObject.containsValue( true ) );
        assertTrue( jsonObject.containsValue( 1 ) );
        assertTrue( jsonObject.containsValue( "bar" ) );
        assertFalse( jsonObject.containsValue( false ) );
        assertFalse( jsonObject.containsValue( 0 ) );
        assertFalse( jsonObject.containsValue( "foo" ) );
        assertTrue( jsonObject.containsValue( ( JsonValue ) null ) );
        assertTrue( jsonObject.containsValue( factory.newJsonBoolean( true ) ) );
        assertTrue( jsonObject.containsValue( factory.newJsonNumber( 1 ) ) );
        assertTrue( jsonObject.containsValue( factory.newJsonString( "bar" ) ) );
        assertFalse( jsonObject.containsValue( factory.newJsonBoolean( false ) ) );
        assertFalse( jsonObject.containsValue( factory.newJsonNumber( 0 ) ) );
        assertFalse( jsonObject.containsValue( factory.newJsonString( "foo" ) ) );
        assertTrue( jsonObject.containsValue( ( Object ) null ) );
        assertTrue( jsonObject.containsValue( ( Object ) true ) );
        assertTrue( jsonObject.containsValue( ( Object ) 1 ) );
        assertTrue( jsonObject.containsValue( ( Object ) "bar" ) );
        assertFalse( jsonObject.containsValue( ( Object ) false ) );
        assertFalse( jsonObject.containsValue( ( Object ) 0 ) );
        assertFalse( jsonObject.containsValue( ( Object ) "foo" ) );
        assertTrue( jsonObject.containsValue( ( Object ) null ) );
        assertTrue( jsonObject.containsValue( ( Object ) factory.newJsonBoolean( true ) ) );
        assertTrue( jsonObject.containsValue( ( Object ) factory.newJsonNumber( 1 ) ) );
        assertTrue( jsonObject.containsValue( ( Object ) factory.newJsonString( "bar" ) ) );
        assertFalse( jsonObject.containsValue( ( Object ) factory.newJsonBoolean( false ) ) );
        assertFalse( jsonObject.containsValue( ( Object ) factory.newJsonNumber( 0 ) ) );
        assertFalse( jsonObject.containsValue( ( Object ) factory.newJsonString( "foo" ) ) );
    }

    @Test
    public void get() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonObject jsonObject = createComplexObject();
        assertEquals( jsonObject.get( "1" ), factory.newJsonString( "b1" ) );
        assertEquals( jsonObject.get( "2" ), null );
        assertEquals( jsonObject.get( "3" ), factory.newJsonBoolean( true ) );
        assertEquals( jsonObject.get( "4" ), factory.newJsonNumber( 1 ) );
        assertEquals( jsonObject.get( "5" ), createSimpleArray() );
        assertEquals( jsonObject.get( "6" ), createSimpleObject() );
        assertEquals( jsonObject.get( "7" ), null );
        assertEquals( jsonObject.get( ( Object ) "1" ), factory.newJsonString( "b1" ) );
        assertEquals( jsonObject.get( ( Object ) "2" ), null );
        assertEquals( jsonObject.get( ( Object ) "3" ), factory.newJsonBoolean( true ) );
        assertEquals( jsonObject.get( ( Object ) "4" ), factory.newJsonNumber( 1 ) );
        assertEquals( jsonObject.get( ( Object ) "5" ), createSimpleArray() );
        assertEquals( jsonObject.get( ( Object ) "6" ), createSimpleObject() );
        assertEquals( jsonObject.get( ( Object ) "7" ), null );
    }

    @Test
    public void entrySet() {
        final JsonObject jsonObject = createComplexObject();
        final Set< Entry< String, JsonValue > > entrySet = jsonObject.entrySet();
        assertNotNull( entrySet );
        assertTrue( entrySet.size() == 6 );
    }

    @Test
    public void keySet() {
        final JsonObject jsonObject = createComplexObject();
        final Set< String > keySet = jsonObject.keySet();
        assertNotNull( keySet );
        assertTrue( keySet.size() == 6 );
    }

    @Test
    public void values() {
        final JsonObject jsonObject = createComplexObject();
        final Collection< JsonValue > values = jsonObject.values();
        assertNotNull( values );
        assertTrue( values.size() == 6 );
    }

    @Test
    public void sizeClearIsEmpty() {
        final JsonObject jsonObject = createComplexObject();
        assertTrue( 6 == jsonObject.size() );
        assertFalse( jsonObject.isEmpty() );
        jsonObject.clear();
        assertTrue( 0 == jsonObject.size() );
        assertTrue( jsonObject.isEmpty() );
    }

    @Test
    public void remove() {
        final JsonObject jsonObject = createComplexObject();
        assertTrue( 6 == jsonObject.size() );
        assertTrue( jsonObject.containsKey( "1" ) );
        assertTrue( jsonObject.containsKey( "2" ) );
        assertTrue( jsonObject.containsValue( "b1" ) );
        assertTrue( jsonObject.containsValue( ( String ) null ) );
        assertTrue( jsonObject.containsNullValue() );
        jsonObject.remove( ( Object ) "1" );
        jsonObject.remove( "2" );
        assertTrue( 4 == jsonObject.size() );
        assertFalse( jsonObject.containsKey( "1" ) );
        assertFalse( jsonObject.containsKey( "2" ) );
        assertFalse( jsonObject.containsValue( "b1" ) );
        assertFalse( jsonObject.containsValue( ( String ) null ) );
        assertFalse( jsonObject.containsNullValue() );
    }

    @Test
    public void putAll() {
        final JsonObject jsonObject1 = JsonValueFactory.getInstance().newJsonObject();
        final JsonObject jsonObject2 = createSimpleObject();
        jsonObject1.putAll( jsonObject2 );
        assertEquals( jsonObject1, jsonObject2 );
    }

    @Test
    public void cloneMethod() {
        final JsonObject orig = createComplexObject();
        final JsonObject clone = orig.clone();
        assertNotSame( orig, clone );
        assertNotSame( orig.get( "1" ), clone.get( "1" ) );
        assertEquals( orig.get( "1" ), clone.get( "1" ) );
        assertSame( orig.get( "2" ), clone.get( "2" ) );
        assertEquals( null, clone.get( "2" ) );
        assertNotSame( orig.get( "3" ), clone.get( "3" ) );
        assertEquals( orig.get( "3" ), clone.get( "3" ) );
        assertNotSame( orig.get( "4" ), clone.get( "4" ) );
        assertEquals( orig.get( "4" ), clone.get( "4" ) );
        assertNotSame( orig.get( "5" ), clone.get( "5" ) );
        assertEquals( orig.get( "5" ), clone.get( "5" ) );
        assertNotSame( orig.get( "6" ), clone.get( "6" ) );
        assertEquals( orig.get( "6" ), clone.get( "6" ) );
        final JsonArray origArray = ( JsonArray ) orig.get( "5" );
        final JsonArray cloneArray = ( JsonArray ) clone.get( "5" );
        assertTrue( origArray.get( 0 ) == cloneArray.get( 0 ) );
        assertTrue( cloneArray.get( 0 ) == null );
        assertNotSame( origArray.get( 1 ), cloneArray.get( 1 ) );
        assertEquals( origArray.get( 1 ), cloneArray.get( 1 ) );
        assertNotSame( origArray.get( 2 ), cloneArray.get( 2 ) );
        assertEquals( origArray.get( 2 ), cloneArray.get( 2 ) );
        assertNotSame( origArray.get( 3 ), cloneArray.get( 3 ) );
        assertEquals( origArray.get( 3 ), cloneArray.get( 3 ) );
        assertNotSame( origArray.get( 4 ), cloneArray.get( 4 ) );
        assertEquals( origArray.get( 4 ), cloneArray.get( 4 ) );
        assertNotSame( origArray.get( 5 ), cloneArray.get( 5 ) );
        assertEquals( origArray.get( 5 ), cloneArray.get( 5 ) );
        final JsonObject origObject = ( JsonObject ) orig.get( "6" );
        final JsonObject cloneObject = ( JsonObject ) clone.get( "6" );
        assertTrue( origObject.get( "1" ) == cloneObject.get( "1" ) );
        assertTrue( cloneObject.get( "1" ) == null );
        assertNotSame( origObject.get( "2" ), cloneObject.get( "2" ) );
        assertEquals( origObject.get( "2" ), cloneObject.get( "2" ) );
        assertNotSame( origObject.get( "3" ), cloneObject.get( "3" ) );
        assertEquals( origObject.get( "3" ), cloneObject.get( "3" ) );
        assertNotSame( origObject.get( "4" ), cloneObject.get( "4" ) );
        assertEquals( origObject.get( "4" ), cloneObject.get( "4" ) );
        assertNotSame( origObject.get( "5" ), cloneObject.get( "5" ) );
        assertEquals( origObject.get( "5" ), cloneObject.get( "5" ) );
        assertNotSame( origObject.get( "6" ), cloneObject.get( "6" ) );
        assertEquals( origObject.get( "6" ), cloneObject.get( "6" ) );
        orig.put( "1", 1 );
        assertFalse( orig.equals( clone ) );
    }
}
