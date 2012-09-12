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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.stream.JsonReader;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class ValidJsonReaderTestCase extends AbstractJsonTestCase {

    @Test
    public void emptyObjectWithWhitespaces() throws IOException {
        final JsonReader reader = getJsonReader( "{ \t\r\n}" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertFinalState( reader );
        reader.close();
    }

    @Test
    public void simpleObjectWithWhitespaces() throws IOException {
        final JsonReader reader = getJsonReader( "{\r\n\t\"a\" : \"b\"\r\n}" );
        assertObjectStartState( reader );
        assertStringState( reader, "a" );
        assertStringState( reader, "b" );
        assertObjectEndState( reader );
        reader.close();
    }

    @Test
    public void moreComplexObject() throws IOException {
        final String data = "{\"0\":\"0\",\"1\":1,\"2\":2,\"3\":3,\"4\":4,\"5\":5.0,\"6\":6.0,\"7\":true,\"8\":null,\"9\":"
            + "900000000000000000000000000000000000000,\"10\":100000000000000000000000000000000000000.000000000000000000000000000001}";
        final JsonReader reader = getJsonReader( data );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertByteState( reader, ( byte ) 1 );
        assertStringState( reader, "2" );
        assertShortState( reader, ( short ) 2 );
        assertStringState( reader, "3" );
        assertIntState( reader, 3 );
        assertStringState( reader, "4" );
        assertLongState( reader, 4L );
        assertStringState( reader, "5" );
        assertFloatState( reader, 5F );
        assertStringState( reader, "6" );
        assertDoubleState( reader, 6. );
        assertStringState( reader, "7" );
        assertBooleanState( reader, true );
        assertStringState( reader, "8" );
        assertNullState( reader );
        assertStringState( reader, "9" );
        assertBigIntegerState( reader, new BigInteger( "900000000000000000000000000000000000000" ) );
        assertStringState( reader, "10" );
        assertBigDecimalState( reader, new BigDecimal( "100000000000000000000000000000000000000.000000000000000000000000000001" ) );
        assertObjectEndState( reader );
        reader.close();
    }

    @Test
    public void theMostComplexObject() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":{\"String\":\"s\",\"boolean\":false},\"1\":[null,true,7,{}]}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "1" );
        assertArrayStartState( reader );
        assertNullState( reader );
        assertBooleanState( reader, true );
        assertIntState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertArrayEndState( reader );
        assertObjectEndState( reader );
        reader.close();
    }

    @Test
    public void emptyArrayWithWhitespaces() throws IOException {
        final JsonReader reader = getJsonReader( "[ \t\r\n]" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertFinalState( reader );
        reader.close();
    }

    @Test
    public void moreComplexArrayWithWhitespaces() throws IOException {
        final String data = "[ \"0\" \r\n, 1 \r\n, 2 \r\n, 3 \r\n, 4 \r\n, 5.0 \r\n, 6.0\r\n, "
            + "700000000000000000000000000000000000000,800000000000000000000000000000000000000.000000000000000000000000000009 \r\n, true \r\n, null \r\n]";
        final JsonReader reader = getJsonReader( data );
        assertArrayStartState( reader );
        assertStringState( reader, "0" );
        assertByteState( reader, ( byte ) 1 );
        assertShortState( reader, ( short ) 2 );
        assertIntState( reader, 3 );
        assertLongState( reader, 4L );
        assertFloatState( reader, 5F );
        assertDoubleState( reader, 6.0 );
        assertBigIntegerState( reader, new BigInteger( "700000000000000000000000000000000000000" ) );
        assertBigDecimalState( reader, new BigDecimal( "800000000000000000000000000000000000000.000000000000000000000000000009" ) );
        assertBooleanState( reader, true );
        assertNullState( reader );
        assertArrayEndState( reader );
        assertFinalState( reader );
        reader.close();
    }

    @Test
    public void theMostComplexArray() throws IOException {
        final JsonReader reader = getJsonReader( "[\"0\",{\"String\":\"s\",\"boolean\":false},\"1\",[null,true,7,{},[[]]]]" );
        assertArrayStartState( reader );
        assertStringState( reader, "0" );
        assertObjectStartState( reader );
        assertStringState( reader, "String" );
        assertStringState( reader, "s" );
        assertStringState( reader, "boolean" );
        assertBooleanState( reader, false );
        assertObjectEndState( reader );
        assertStringState( reader, "1" );
        assertArrayStartState( reader );
        assertNullState( reader );
        assertBooleanState( reader, true );
        assertIntState( reader, 7 );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertArrayStartState( reader );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertArrayEndState( reader );
        assertArrayEndState( reader );
        assertArrayEndState( reader );
        reader.close();
    }

    @Test
    public void escapesEncoding() throws IOException {
        final JsonReader reader = getJsonReader( "[\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"]" );
        assertArrayStartState( reader );
        final StringBuilder sb = new StringBuilder();
        sb.append( '\"' );
        sb.append( '\\' );
        sb.append( '/' );
        sb.append( '\b' );
        sb.append( '\f' );
        sb.append( '\n' );
        sb.append( '\r' );
        sb.append( '\t' );
        assertStringState( reader, sb.toString() );
        assertArrayEndState( reader );
        reader.close();
    }

    @Test
    public void controlsEncoding() throws IOException {
        final String data = "[\"\\u0000\\u0001\\u0002\\u0003\\u0004\\u0005\\u0006\\u0007\\b\\t\\n\\u000B\\f\\r\\u000E\\u000F\\u0010\\u0011\\u0012\\u0013\\u0014\\u0015"
            + "\\u0016\\u0017\\u0018\\u0019\\u001A\\u001B\\u001C\\u001D\\u001E\\u001F\\u007F\\u0080\\u0081\\u0082\\u0083\\u0084\\u0085\\u0086\\u0087\\u0088\\u0089\\u008A"
            + "\\u008B\\u008C\\u008D\\u008E\\u008F\\u0090\\u0091\\u0092\\u0093\\u0094\\u0095\\u0096\\u0097\\u0098\\u0099\\u009A\\u009B\\u009C\\u009D\\u009E\\u009F\"]";
        final JsonReader reader = getJsonReader( data );
        assertArrayStartState( reader );
        final StringBuilder sb = new StringBuilder();
        for ( char c = '\u0000'; c <= '\u001F'; c++ ) {
            sb.append( c );
        }
        for ( char c = '\u007F'; c <= '\u009F'; c++ ) {
            sb.append( c );
        }
        assertStringState( reader, sb.toString() );
        assertArrayEndState( reader );
        reader.close();
    }
}
