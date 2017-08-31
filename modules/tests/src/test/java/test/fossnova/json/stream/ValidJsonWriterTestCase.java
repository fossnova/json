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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonStreamFactory;
import org.fossnova.json.stream.JsonWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class ValidJsonWriterTestCase extends AbstractJsonStreamsTestCase {

    private ByteArrayOutputStream baos;

    private JsonWriter writer;

    @Before
    public void init() {
        baos = new ByteArrayOutputStream();
        writer = JsonStreamFactory.getInstance().newJsonWriter( new OutputStreamWriter( baos ) );
    }

    @After
    public void destroy() {
        baos = null;
        writer = null;
    }

    @Test
    public void emptyObject() throws IOException, JsonException {
        writer.writeObjectStart();
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "{}", getWriterOutput() );
    }

    @Test
    public void emptyArray() throws IOException, JsonException {
        writer.writeArrayStart();
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "[]", getWriterOutput() );
    }

    @Test
    public void simple_string() throws IOException, JsonException {
        writer.writeString( "" );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "\"\"", getWriterOutput() );
    }

    @Test
    public void simple_byte() throws IOException, JsonException {
        writer.writeByte( ( byte ) 0 );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0", getWriterOutput() );
    }

    @Test
    public void simple_short() throws IOException, JsonException {
        writer.writeShort( ( short ) 0 );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0", getWriterOutput() );
    }

    @Test
    public void simple_int() throws IOException, JsonException {
        writer.writeInt( 0 );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0", getWriterOutput() );
    }

    @Test
    public void simple_long() throws IOException, JsonException {
        writer.writeLong( 0L );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0", getWriterOutput() );
    }

    @Test
    public void simple_bigInteger() throws IOException, JsonException {
        writer.writeBigInteger( BigInteger.ZERO );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0", getWriterOutput() );
    }

    @Test
    public void simple_bigDecimal() throws IOException, JsonException {
        writer.writeBigDecimal( BigDecimal.ZERO );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0", getWriterOutput() );
    }

    @Test
    public void simple_float() throws IOException, JsonException {
        writer.writeFloat( 0.0F );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0.0", getWriterOutput() );
    }

    @Test
    public void simple_double() throws IOException, JsonException {
        writer.writeDouble( 0.0 );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "0.0", getWriterOutput() );
    }

    @Test
    public void simple_false() throws IOException, JsonException {
        writer.writeBoolean( false );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "false", getWriterOutput() );
    }

    @Test
    public void simple_true() throws IOException, JsonException {
        writer.writeBoolean( true );
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "true", getWriterOutput() );
    }

    @Test
    public void simple_null() throws IOException, JsonException {
        writer.writeNull();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "null", getWriterOutput() );
    }

    @Test
    public void moreComplexArray() throws IOException, JsonException {
        writer.writeArrayStart();
        writer.writeString( "0" );
        writer.writeByte( ( byte ) 1 );
        writer.writeShort( ( short ) 2 );
        writer.writeInt( 3 );
        writer.writeLong( 4L );
        writer.writeFloat( 5F );
        writer.writeDouble( 6.0 );
        writer.writeBigInteger( new BigInteger( "700000000000000000000000000000000000000" ) );
        writer.writeBigDecimal( new BigDecimal( "800000000000000000000000000000000000000.000000000000000000000000000009" ) );
        writer.writeBoolean( true );
        writer.writeNull();
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        final String expected = "[\"0\",1,2,3,4,5.0,6.0,"
            + "700000000000000000000000000000000000000,800000000000000000000000000000000000000.000000000000000000000000000009,true,null]";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    @Test
    public void theMostComplexArray() throws IOException, JsonException {
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
        assertClosedState( writer );
        Assert.assertEquals( "[\"0\",{\"String\":\"s\",\"boolean\":false},\"1\",[null,true,7,{},[[]]]]", getWriterOutput() );
    }

    @Test
    public void simpleObject() throws IOException, JsonException {
        writer.writeObjectStart();
        writer.writeString( "a" );
        writer.writeString( "b" );
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "{\"a\":\"b\"}", getWriterOutput() );
    }

    @Test
    public void moreComplexObject() throws IOException, JsonException {
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
        writer.writeString( "9" );
        writer.writeBigInteger( new BigInteger( "900000000000000000000000000000000000000" ) );
        writer.writeString( "10" );
        writer.writeBigDecimal( new BigDecimal( "100000000000000000000000000000000000000.000000000000000000000000000001" ) );
        writer.writeObjectEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        final String expected = "{\"0\":\"0\",\"1\":1,\"2\":2,\"3\":3,\"4\":4,\"5\":5.0,\"6\":6.0,\"7\":true,\"8\":null,\"9\":"
            + "900000000000000000000000000000000000000,\"10\":100000000000000000000000000000000000000.000000000000000000000000000001}";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    @Test
    public void testIntegers() throws IOException, JsonException {
        writer.writeArrayStart();
        writer.writeByte( Byte.MIN_VALUE );
        writer.writeByte( ( byte ) 0 );
        writer.writeByte( Byte.MAX_VALUE );
        writer.writeShort( Short.MIN_VALUE );
        writer.writeShort( ( short ) 0 );
        writer.writeShort( Short.MAX_VALUE );
        writer.writeInt( Integer.MIN_VALUE );
        writer.writeInt( 0 );
        writer.writeInt( Integer.MAX_VALUE );
        writer.writeLong( Long.MIN_VALUE );
        writer.writeLong( 0L );
        writer.writeLong( Long.MAX_VALUE );
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "[" + Byte.MIN_VALUE + ",0," + Byte.MAX_VALUE + ","
                + Short.MIN_VALUE + ",0," + Short.MAX_VALUE + ","
                + Integer.MIN_VALUE + ",0," + Integer.MAX_VALUE + ","
                + Long.MIN_VALUE + ",0," + Long.MAX_VALUE + "]", getWriterOutput() );
    }

    @Test
    public void theMostComplexObject() throws IOException, JsonException {
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
        assertClosedState( writer );
        Assert.assertEquals( "{\"0\":{\"String\":\"s\",\"boolean\":false},\"1\":[null,true,7,{}]}", getWriterOutput() );
    }

    @Test
    public void escapesEncoding() throws IOException, JsonException {
        writer.writeArrayStart();
        final StringBuilder sb = new StringBuilder();
        sb.append( '\"' );
        sb.append( '\\' );
        sb.append( '\b' );
        sb.append( '\f' );
        sb.append( '\n' );
        sb.append( '\r' );
        sb.append( '\t' );
        writer.writeString( sb.toString() );
        writer.writeArrayEnd();
        writer.flush();
        writer.close();
        assertClosedState( writer );
        Assert.assertEquals( "[\"\\\"\\\\\\b\\f\\n\\r\\t\"]", getWriterOutput() );
    }

    @Test
    public void controlsEncoding() throws IOException, JsonException {
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
        assertClosedState( writer );
        final String expected = "[\"\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007\\b\\t\\n\\u000b\\f\\r\\u000e\\u000f\\u0010\\u0011\\u0012\\u0013\\u0014\\u0015"
            + "\\u0016\\u0017\\u0018\\u0019\\u001a\\u001b\\u001c\\u001d\\u001e\\u001f\\u007f\\u0080\\u0081\\u0082\\u0083\\u0084\\u0085\\u0086\\u0087\\u0088\\u0089\\u008a"
            + "\\u008b\\u008c\\u008d\\u008e\\u008f\\u0090\\u0091\\u0092\\u0093\\u0094\\u0095\\u0096\\u0097\\u0098\\u0099\\u009a\\u009b\\u009c\\u009d\\u009e\\u009f\"]";
        Assert.assertEquals( expected, getWriterOutput() );
    }

    private String getWriterOutput() {
        return baos.toString();
    }
}
