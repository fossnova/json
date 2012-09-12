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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import junit.framework.Assert;

import org.fossnova.json.JsonArray;
import org.fossnova.json.JsonBoolean;
import org.fossnova.json.JsonNumber;
import org.fossnova.json.JsonObject;
import org.fossnova.json.JsonString;
import org.fossnova.json.JsonStructureFactory;
import org.fossnova.json.JsonValue;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonObjectTestCase extends AbstractJsonTestCase {

    @Test
    public void createJsonObjectFromScratch() throws IOException {
        JsonStructureFactory jsonStructureFactory = JsonStructureFactory.newInstance();
        JsonObject o = jsonStructureFactory.newJsonObject();
        o.put( "1", "b1" );
        o.put( "2", (String)null );
        o.put("3", true );
        o.put( "4", false );
        o.put( "5", 1 );
        JsonArray a = jsonStructureFactory.newJsonArray();
        o.put("6", a);
        JsonObject o1 = jsonStructureFactory.newJsonObject();
        o.put("7", o1);
        assertJsonString( o, "1", "b1");
        assertJsonNull( o, "2");
        assertJsonBoolean( o, "3", true );
        assertJsonBoolean( o, "4", false );
        assertJsonNumber( o, "5", 1 );
        Assert.assertTrue( o.containsKey( "4" ) );
        Assert.assertTrue( o.containsValue( 1 ) );
        Assert.assertTrue( o.containsValue( true ) );
        Assert.assertTrue( o.containsValue( false ) );
        Assert.assertTrue( o.containsValue( (Boolean) null ) );
        Assert.assertTrue( o.containsValue( a ) );
        Assert.assertTrue( o.containsValue( o1 ) );
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        o.writeTo( baos );
        System.out.println("--- START ---");
        System.out.println( new String(baos.toByteArray()));
        System.out.println("--- END ---");
    }
    
    @Test
    public void createJsonObjectFromStream() throws IOException {
        final JsonStructureFactory jsonStructureFactory = JsonStructureFactory.newInstance();
        final ByteArrayInputStream bais = new ByteArrayInputStream("{\"1\":\"b1\",\"2\":null,\"3\":true,\"4\":false,\"5\":1,\"6\":[],\"7\":{}}".getBytes());
        final JsonObject o = (JsonObject) jsonStructureFactory.readFrom( bais );
        JsonArray a = jsonStructureFactory.newJsonArray();
        JsonObject o1 = jsonStructureFactory.newJsonObject();
        assertJsonString( o, "1", "b1");
        assertJsonNull( o, "2");
        assertJsonBoolean( o, "3", true );
        assertJsonBoolean( o, "4", false );
        assertJsonNumber( o, "5", 1 );
        Assert.assertTrue( o.containsKey( "4" ) );
        Assert.assertTrue( o.containsValue( 1 ) );
        Assert.assertTrue( o.containsValue( true ) );
        Assert.assertTrue( o.containsValue( false ) );
        Assert.assertTrue( o.containsValue( (Boolean) null ) );
        Assert.assertTrue( o.containsValue( a ) );
        Assert.assertTrue( o.containsValue( o1 ) );
    }
    
    private static void assertJsonString( final JsonObject o, final String key, final String expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        Assert.assertTrue( jsonValue instanceof JsonString );
        final JsonString jsonString = (JsonString)jsonValue;
        Assert.assertEquals( expectedValue, jsonString.getString() );
    }
    
    private static void assertJsonBoolean( final JsonObject o, final String key, final boolean expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        Assert.assertTrue( jsonValue instanceof JsonBoolean );
        final JsonBoolean jsonBoolean = (JsonBoolean)jsonValue;
        Assert.assertEquals( expectedValue, jsonBoolean.getBoolean() );
    }
    
    private static void assertJsonNumber( final JsonObject o, final String key, final int expectedValue ) {
        final JsonValue jsonValue = o.get( key );
        Assert.assertTrue( jsonValue instanceof JsonNumber );
        final JsonNumber jsonNumber = (JsonNumber)jsonValue;
        Assert.assertEquals( expectedValue, jsonNumber.getInt() );
    }
    
    private static void assertJsonNull( final JsonObject o, final String key ) {
        Assert.assertTrue( o.keySet().contains( key ) );
        Assert.assertNull( o.get( key ) );
    }
    
}
