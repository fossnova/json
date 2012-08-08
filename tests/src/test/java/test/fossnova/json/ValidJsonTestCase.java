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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.fossnova.json.JsonFactory;
import org.fossnova.json.JsonWriter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
// TODO: implement writer.close(), reader.close(), builder.close() and call it in tests when necessary
public final class ValidJsonTestCase {

    private ByteArrayOutputStream baos;

    private JsonWriter writer;

    @Before
    public void init() {
        baos = new ByteArrayOutputStream();
        writer = JsonFactory.newInstance().newJsonWriter( baos );
    }

    @After
    public void destroy() {
        baos = null;
        writer = null;
    }

    @Test
    public void emptyObject() throws IOException {
        writer.writeObjectStart();
        writer.writeObjectEnd();
        Assert.assertEquals( "{}", getWriterOutput() );
    }

    @Test
    public void emptyArray() throws IOException {
        writer.writeArrayStart();
        writer.writeArrayEnd();
        Assert.assertEquals( "[]", getWriterOutput() );
    }

    @Test
    public void moreComplexArray() throws IOException {
        writer.writeArrayStart();
        writer.writeString( "0" );
        writer.writeByte( ( byte ) 1 );
        writer.writeShort( ( short ) 2 );
        writer.writeInt( 3 );
        writer.writeLong( 4L );
        writer.writeFloat( 5F );
        writer.writeDouble( 6.0 );
        writer.writeBoolean( true );
        writer.writeNull();
        writer.writeArrayEnd();
        Assert.assertEquals( "[\"0\",1,2,3,4,5.0,6.0,true,null]", getWriterOutput() );
    }

    @Test
    public void theMostComplexArray() throws IOException {
        writer.writeArrayStart();
        writer.writeString( "0" );
        writer.writeObjectStart();
        writer.writeString( "String" );
        writer.writeString( "s" );
        writer.writeString( "boolean" );
        writer.writeBoolean( false );
        writer.writeObjectEnd();
        writer.writeString( "1" );
        writer.writeArrayStart();
        writer.writeNull();
        writer.writeBoolean( true );
        writer.writeInt( 7 );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeArrayStart();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        writer.writeArrayEnd();
        writer.writeArrayEnd();
        writer.writeArrayEnd();
        Assert.assertEquals( "[\"0\",{\"String\":\"s\",\"boolean\":false},\"1\",[null,true,7,{}[[]]]]", getWriterOutput() );
    }

    @Test
    public void simpleObject() throws IOException {
        writer.writeObjectStart();
        writer.writeString( "a" );
        writer.writeString( "b" );
        writer.writeObjectEnd();
        Assert.assertEquals( "{\"a\":\"b\"}", getWriterOutput() );
    }

    @Test
    public void moreComplexObject() throws IOException {
        writer.writeObjectStart();
        writer.writeString( "0" );
        writer.writeString( "0" );
        writer.writeString( "1" );
        writer.writeByte( ( byte ) 1 );
        writer.writeString( "2" );
        writer.writeShort( ( short ) 2 );
        writer.writeString( "3" );
        writer.writeInt( 3 );
        writer.writeString( "4" );
        writer.writeLong( 4L );
        writer.writeString( "5" );
        writer.writeFloat( 5F );
        writer.writeString( "6" );
        writer.writeDouble( 6.0 );
        writer.writeString( "7" );
        writer.writeBoolean( true );
        writer.writeString( "8" );
        writer.writeNull();
        writer.writeObjectEnd();
        Assert.assertEquals( "{\"0\":\"0\",\"1\":1,\"2\":2,\"3\":3,\"4\":4,\"5\":5.0,\"6\":6.0,\"7\":true,\"8\":null}", getWriterOutput() );
    }

    @Test
    public void theMostComplexObject() throws IOException {
        writer.writeObjectStart();
        writer.writeString( "0" );
        writer.writeObjectStart();
        writer.writeString( "String" );
        writer.writeString( "s" );
        writer.writeString( "boolean" );
        writer.writeBoolean( false );
        writer.writeObjectEnd();
        writer.writeString( "1" );
        writer.writeArrayStart();
        writer.writeNull();
        writer.writeBoolean( true );
        writer.writeInt( 7 );
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.writeArrayEnd();
        writer.writeObjectEnd();
        Assert.assertEquals( "{\"0\":{\"String\":\"s\",\"boolean\":false},\"1\":[null,true,7,{}]}", getWriterOutput() );
    }

    private String getWriterOutput() throws UnsupportedEncodingException {
        return baos.toString( "UTF-8" );
    }
}
