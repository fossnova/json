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
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
// TODO: implement writer.close(), reader.close(), builder.close() and call it in tests when necessary
public final class ValidJsonWriterTestCase {

    private ByteArrayOutputStream baos;

    private JsonWriter writer;

    @Before
    public void init() throws UnsupportedEncodingException {
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
        writer.flush();
        writer.close();
        Assert.assertEquals( "{}", getWriterOutput() );
    }

    @Test
    public void emptyArray() throws IOException {
        writer.writeArrayStart();
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
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
        writer.flush();
        writer.close();
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
        writer.flush();
        writer.close();
        Assert.assertEquals( "[\"0\",{\"String\":\"s\",\"boolean\":false},\"1\",[null,true,7,{}[[]]]]", getWriterOutput() );
    }

    @Test
    public void simpleObject() throws IOException {
        writer.writeObjectStart();
        writer.writeString( "a" );
        writer.writeString( "b" );
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
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
        writer.flush();
        writer.close();
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
        writer.flush();
        writer.close();
        Assert.assertEquals( "{\"0\":{\"String\":\"s\",\"boolean\":false},\"1\":[null,true,7,{}]}", getWriterOutput() );
    }

    @Test
    public void escapesEncoding() throws IOException {
        writer.writeArrayStart();
        final StringBuilder sb = new StringBuilder();
        sb.append( '\"' );
        sb.append( '\\' );
        sb.append( '/' );
        sb.append( '\b' );
        sb.append( '\f' );
        sb.append( '\n' );
        sb.append( '\r' );
        sb.append( '\t' );
        writer.writeString( sb.toString() );
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
        Assert.assertEquals( "[\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"]", getWriterOutput() );
    }

    @Test
    public void controlsEncoding() throws IOException {
        writer.writeArrayStart();
        final StringBuilder sb = new StringBuilder();
        for ( char c = '\u0000'; c <= '\u001F'; c++ ) {
            sb.append( c );
        }
        for ( char c = '\u007F'; c <= '\u009F'; c++ ) {
            sb.append( c );
        }
        writer.writeString( sb.toString() );
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
        final String expected = "[\"\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007\\b\\t\\n\\u000B\\f\\r\\u000E\\u000F\\u0010\\u0011\\u0012\\u0013\\u0014\\u0015"
            + "\\u0016\\u0017\\u0018\\u0019\\u001A\\u001B\\u001C\\u001D\\u001E\\u001F\\u007F\\u0080\\u0081\\u0082\\u0083\\u0084\\u0085\\u0086\\u0087\\u0088\\u0089\\u008A"
            + "\\u008B\\u008C\\u008D\\u008E\\u008F\\u0090\\u0091\\u0092\\u0093\\u0094\\u0095\\u0096\\u0097\\u0098\\u0099\\u009A\\u009B\\u009C\\u009D\\u009E\\u009F\"]";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    private String getWriterOutput() throws UnsupportedEncodingException {
        return baos.toString( "UTF-8" );
    }
}
