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
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.fossnova.json.JsonArray;
import org.fossnova.json.JsonObject;
import org.fossnova.json.JsonValueFactory;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonReader;
import org.fossnova.json.stream.JsonStreamFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonArrayTestCase extends AbstractJsonValuesTestCase {

    @Test
    public void jsonStructureFactoryNewObject() {
        final JsonArray complexArray = createComplexArray();
        assertJsonNull( complexArray, 0 );
        assertJsonBoolean( complexArray, 1, false );
        assertJsonNumber( complexArray, 2, 1 );
        assertJsonString( complexArray, 3, "2" );
        assertTrue( complexArray.contains( ( Boolean ) null ) );
        assertTrue( complexArray.contains( false ) );
        assertTrue( complexArray.contains( 1 ) );
        assertTrue( complexArray.contains( "2" ) );
        assertTrue( complexArray.contains( createSimpleArray() ) );
        assertTrue( complexArray.contains( createSimpleObject() ) );
    }

    @Test
    public void jsonStructureFactoryReadFrom() throws IOException, JsonException {
        final JsonValueFactory jsonValueFactory = JsonValueFactory.getInstance();
        final ByteArrayInputStream bais = new ByteArrayInputStream(
            "[null,false,1,\"2\",[null,true,0,\"foo\",[],{}],{\"1\":null,\"2\":true,\"3\":1,\"4\":\"bar\",\"5\":[],\"6\":{}}]".getBytes() );
        final JsonReader jsonReader = JsonStreamFactory.getInstance().newJsonReader( bais );
        final JsonArray complexArray = ( JsonArray ) jsonValueFactory.readFrom( jsonReader );
        assertJsonNull( complexArray, 0 );
        assertJsonBoolean( complexArray, 1, false );
        assertJsonNumber( complexArray, 2, 1 );
        assertJsonString( complexArray, 3, "2" );
        assertTrue( complexArray.contains( ( Boolean ) null ) );
        assertTrue( complexArray.contains( false ) );
        assertTrue( complexArray.contains( 1 ) );
        assertTrue( complexArray.contains( "2" ) );
        assertTrue( complexArray.contains( createSimpleArray() ) );
        assertTrue( complexArray.contains( createSimpleObject() ) );
    }

    @Test
    public void jsonSerializationRoundTrip() throws IOException, JsonException {
        assertJsonSerializationRoundTrip( createSimpleArray() );
        assertJsonSerializationRoundTrip( createComplexArray() );
    }

    @Test
    public void javaSerializationRoundTrip() throws IOException, ClassNotFoundException {
        assertJavaSerializationRoundTrip( createSimpleArray() );
        assertJavaSerializationRoundTrip( createComplexArray() );
    }

    @Test
    public void cloneMethod() {
        final JsonArray orig = createComplexArray();
        final JsonArray clone = orig.clone();
        assertNotSame( orig, clone );
        assertSame( orig.get( 0 ), clone.get( 0 ) );
        assertEquals( null, clone.get( 0 ) );
        assertNotSame( orig.get( 1 ), clone.get( 1 ) );
        assertEquals( orig.get( 1 ), clone.get( 1 ) );
        assertNotSame( orig.get( 2 ), clone.get( 2 ) );
        assertEquals( orig.get( 2 ), clone.get( 2 ) );
        assertNotSame( orig.get( 3 ), clone.get( 3 ) );
        assertEquals( orig.get( 3 ), clone.get( 3 ) );
        final JsonArray origArray = ( JsonArray ) orig.get( 4 );
        final JsonArray cloneArray = ( JsonArray ) clone.get( 4 );
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
        final JsonObject origObject = ( JsonObject ) orig.get( 5 );
        final JsonObject cloneObject = ( JsonObject ) clone.get( 5 );
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
        orig.add( 0, 1 );
        assertFalse( orig.equals( clone ) );
    }

    @Test
    public void add() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = createComplexArray();
        jsonArray.add( 6, ( String ) null );
        jsonArray.add( 7, "bar" );
        jsonArray.add( 8, 2 );
        jsonArray.add( 9, createSimpleArray() );
        jsonArray.add( 10, createSimpleObject() );
        assertNull( jsonArray.get( 6 ) );
        assertEquals( jsonArray.get( 7 ), factory.newJsonString( "bar" ) );
        assertEquals( jsonArray.get( 8 ), factory.newJsonNumber( 2 ) );
        assertEquals( jsonArray.get( 9 ), createSimpleArray() );
        assertEquals( jsonArray.get( 10 ), createSimpleObject() );
        assertTrue( jsonArray.size() == 11 );
    }

    @Test
    public void addAll() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = createComplexArray();
        jsonArray.addAll( createSimpleArray() );
        assertNull( jsonArray.get( 0 ) );
        assertEquals( jsonArray.get( 1 ), factory.newJsonBoolean( false ) );
        assertEquals( jsonArray.get( 2 ), factory.newJsonNumber( 1 ) );
        assertEquals( jsonArray.get( 3 ), factory.newJsonString( "2" ) );
        assertEquals( jsonArray.get( 4 ), createSimpleArray() );
        assertEquals( jsonArray.get( 5 ), createSimpleObject() );
        assertNull( jsonArray.get( 6 ) );
        assertEquals( jsonArray.get( 7 ), factory.newJsonBoolean( true ) );
        assertEquals( jsonArray.get( 8 ), factory.newJsonNumber( 0 ) );
        assertEquals( jsonArray.get( 9 ), factory.newJsonString( "foo" ) );
        assertEquals( jsonArray.get( 10 ), factory.newJsonArray() );
        assertEquals( jsonArray.get( 11 ), factory.newJsonObject() );
        assertTrue( jsonArray.size() == 12 );
        jsonArray.addAll( 2, createSimpleArray() );
        assertNull( jsonArray.get( 0 ) );
        assertEquals( jsonArray.get( 1 ), factory.newJsonBoolean( false ) );
        assertNull( jsonArray.get( 2 ) );
        assertEquals( jsonArray.get( 3 ), factory.newJsonBoolean( true ) );
        assertEquals( jsonArray.get( 4 ), factory.newJsonNumber( 0 ) );
        assertEquals( jsonArray.get( 5 ), factory.newJsonString( "foo" ) );
        assertEquals( jsonArray.get( 6 ), factory.newJsonArray() );
        assertEquals( jsonArray.get( 7 ), factory.newJsonObject() );
        assertEquals( jsonArray.get( 8 ), factory.newJsonNumber( 1 ) );
        assertEquals( jsonArray.get( 9 ), factory.newJsonString( "2" ) );
        assertEquals( jsonArray.get( 10 ), createSimpleArray() );
        assertEquals( jsonArray.get( 11 ), createSimpleObject() );
        assertNull( jsonArray.get( 12 ) );
        assertEquals( jsonArray.get( 13 ), factory.newJsonBoolean( true ) );
        assertEquals( jsonArray.get( 14 ), factory.newJsonNumber( 0 ) );
        assertEquals( jsonArray.get( 15 ), factory.newJsonString( "foo" ) );
        assertEquals( jsonArray.get( 16 ), factory.newJsonArray() );
        assertEquals( jsonArray.get( 17 ), factory.newJsonObject() );
        assertTrue( jsonArray.size() == 18 );
    }

    @Test
    public void contains() {
        final JsonArray jsonArray = createComplexArray();
        assertTrue( jsonArray.contains( ( String ) null ) );
        assertTrue( jsonArray.contains( false ) );
        assertTrue( jsonArray.contains( 1 ) );
        assertTrue( jsonArray.contains( "2" ) );
        assertTrue( jsonArray.contains( createSimpleArray() ) );
        assertTrue( jsonArray.contains( createSimpleObject() ) );
        assertTrue( jsonArray.contains( ( Object ) null ) );
        assertTrue( jsonArray.contains( ( Object ) false ) );
        assertTrue( jsonArray.contains( ( Object ) 1 ) );
        assertTrue( jsonArray.contains( ( Object ) "2" ) );
        assertTrue( jsonArray.contains( ( Object ) createSimpleArray() ) );
        assertTrue( jsonArray.contains( ( Object ) createSimpleObject() ) );
    }

    @Test
    public void containsAll() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = createComplexArray();
        final Set< Object > simpleValues = new HashSet< Object >();
        simpleValues.add( null );
        simpleValues.add( false );
        simpleValues.add( 1 );
        simpleValues.add( "2" );
        assertTrue( jsonArray.containsAll( simpleValues ) );
        final Set< Object > wrappedValues = new HashSet< Object >();
        wrappedValues.add( null );
        wrappedValues.add( factory.newJsonBoolean( false ) );
        wrappedValues.add( factory.newJsonNumber( 1 ) );
        wrappedValues.add( factory.newJsonString( "2" ) );
        assertTrue( jsonArray.containsAll( wrappedValues ) );
    }

    @Test
    public void indexOf() {
        final JsonArray jsonArray = createComplexArray();
        assertTrue( jsonArray.indexOf( ( String ) null ) == 0 );
        assertTrue( jsonArray.indexOf( false ) == 1 );
        assertTrue( jsonArray.indexOf( 1 ) == 2 );
        assertTrue( jsonArray.indexOf( "2" ) == 3 );
        assertTrue( jsonArray.indexOf( createSimpleArray() ) == 4 );
        assertTrue( jsonArray.indexOf( createSimpleObject() ) == 5 );
        assertTrue( jsonArray.indexOf( ( Object ) null ) == 0 );
        assertTrue( jsonArray.indexOf( ( Object ) false ) == 1 );
        assertTrue( jsonArray.indexOf( ( Object ) 1 ) == 2 );
        assertTrue( jsonArray.indexOf( ( Object ) "2" ) == 3 );
        assertTrue( jsonArray.indexOf( ( Object ) createSimpleArray() ) == 4 );
        assertTrue( jsonArray.indexOf( ( Object ) createSimpleObject() ) == 5 );
    }

    @Test
    public void lastIndexOf() {
        final JsonArray jsonArray = createComplexArray();
        jsonArray.addAll( createComplexArray() );
        assertTrue( jsonArray.lastIndexOf( ( String ) null ) == 6 );
        assertTrue( jsonArray.lastIndexOf( false ) == 7 );
        assertTrue( jsonArray.lastIndexOf( 1 ) == 8 );
        assertTrue( jsonArray.lastIndexOf( "2" ) == 9 );
        assertTrue( jsonArray.lastIndexOf( createSimpleArray() ) == 10 );
        assertTrue( jsonArray.lastIndexOf( createSimpleObject() ) == 11 );
        assertTrue( jsonArray.lastIndexOf( ( Object ) null ) == 6 );
        assertTrue( jsonArray.lastIndexOf( ( Object ) false ) == 7 );
        assertTrue( jsonArray.lastIndexOf( ( Object ) 1 ) == 8 );
        assertTrue( jsonArray.lastIndexOf( ( Object ) "2" ) == 9 );
        assertTrue( jsonArray.lastIndexOf( ( Object ) createSimpleArray() ) == 10 );
        assertTrue( jsonArray.lastIndexOf( ( Object ) createSimpleObject() ) == 11 );
    }

    @Test
    public void remove() {
        final JsonArray jsonArray = createComplexArray();
        jsonArray.remove( ( String ) null );
        jsonArray.remove( false );
        jsonArray.remove( ( Number ) 1 );
        jsonArray.remove( "2" );
        jsonArray.remove( createSimpleArray() );
        jsonArray.remove( createSimpleObject() );
        assertTrue( jsonArray.isEmpty() );
    }

    @Test
    public void removeObject() {
        final JsonArray jsonArray = createComplexArray();
        jsonArray.remove( ( Object ) null );
        jsonArray.remove( ( Object ) false );
        jsonArray.remove( ( Object ) 1 );
        jsonArray.remove( ( Object ) "2" );
        jsonArray.remove( ( Object ) createSimpleArray() );
        jsonArray.remove( ( Object ) createSimpleObject() );
        assertTrue( jsonArray.isEmpty() );
    }

    @Test
    public void removeAll() {
        final JsonArray jsonArray = createComplexArray();
        final Set< Object > values = new HashSet< Object >();
        values.add( null );
        values.add( false );
        values.add( 1 );
        values.add( "2" );
        values.add( createSimpleArray() );
        values.add( createSimpleObject() );
        jsonArray.removeAll( values );
        assertTrue( jsonArray.isEmpty() );
    }

    @Test
    public void retainAll() {
        final JsonArray jsonArray = createComplexArray();
        final Set< Object > values = new HashSet< Object >();
        values.add( null );
        values.add( false );
        values.add( 1 );
        values.add( "2" );
        values.add( createSimpleArray() );
        jsonArray.retainAll( values );
        assertTrue( jsonArray.size() == 5 );
        assertFalse( jsonArray.contains( createSimpleObject() ) );
    }

    @Test
    public void removeAllByObjects() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = createComplexArray();
        final Set< Object > values = new HashSet< Object >();
        values.add( null );
        values.add( factory.newJsonBoolean( false ) );
        values.add( factory.newJsonNumber( 1 ) );
        values.add( factory.newJsonString( "2" ) );
        values.add( createSimpleArray() );
        values.add( createSimpleObject() );
        jsonArray.removeAll( values );
        assertTrue( jsonArray.isEmpty() );
    }

    @Test
    public void removeAtIndex() {
        final JsonArray jsonArray = createComplexArray();
        jsonArray.remove( 5 );
        jsonArray.remove( 4 );
        jsonArray.remove( 3 );
        jsonArray.remove( 2 );
        jsonArray.remove( 1 );
        jsonArray.remove( 0 );
        assertTrue( jsonArray.isEmpty() );
    }

    @Test
    public void set() {
        final JsonValueFactory factory = JsonValueFactory.getInstance();
        final JsonArray jsonArray = createComplexArray();
        jsonArray.set( 0, createSimpleArray() );
        jsonArray.set( 1, 1 );
        jsonArray.set( 2, false );
        jsonArray.set( 3, "foo" );
        assertTrue( jsonArray.size() == 6 );
        assertEquals( jsonArray.get( 0 ), createSimpleArray() );
        assertEquals( jsonArray.get( 1 ), factory.newJsonNumber( 1 ) );
        assertEquals( jsonArray.get( 2 ), factory.newJsonBoolean( false ) );
        assertEquals( jsonArray.get( 3 ), factory.newJsonString( "foo" ) );
        assertEquals( jsonArray.get( 4 ), createSimpleArray() );
        assertEquals( jsonArray.get( 5 ), createSimpleObject() );
    }
}
