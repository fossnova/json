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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.JsonException;
import org.fossnova.json.JsonWriter;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class InvalidJsonWriterTestCase extends AbstractJsonTestCase {

    @Test
    public void emptyState() throws IOException {
        write_objectEnd();
        write_arrayEnd();
        write_string();
        write_byte();
        write_short();
        write_int();
        write_long();
        write_bigInteger();
        write_bigDecimal();
        write_float();
        write_double();
        write_boolean();
        write_null();
    }

    @Test
    public void emptyObjectStartState() throws IOException {
        write_objectStart_arrayEnd();
        write_objectStart_byte();
        write_objectStart_short();
        write_objectStart_int();
        write_objectStart_long();
        write_objectStart_bigInteger();
        write_objectStart_bigDecimal();
        write_objectStart_float();
        write_objectStart_double();
        write_objectStart_boolean();
        write_objectStart_null();
    }

    @Test
    public void emptyArrayStartState() throws IOException {
        write_arrayStart_objectEnd();
    }

    @Test
    public void notEmptyArrayStartState() throws IOException {
        write_arrayStart_string_objectEnd();
        write_arrayStart_byte_objectEnd();
        write_arrayStart_short_objectEnd();
        write_arrayStart_int_objectEnd();
        write_arrayStart_long_objectEnd();
        write_arrayStart_bigInteger_objectEnd();
        write_arrayStart_bigDecimal_objectEnd();
        write_arrayStart_float_objectEnd();
        write_arrayStart_double_objectEnd();
        write_arrayStart_boolean_objectEnd();
        write_arrayStart_null_objectEnd();
    }

    @Test
    public void objectStartObjectEndState() throws IOException {
        write_objectStart_objectEnd_objectStart();
        write_objectStart_objectEnd_objectEnd();
        write_objectStart_objectEnd_arrayStart();
        write_objectStart_objectEnd_arrayEnd();
        write_objectStart_objectEnd_string();
        write_objectStart_objectEnd_byte();
        write_objectStart_objectEnd_short();
        write_objectStart_objectEnd_int();
        write_objectStart_objectEnd_long();
        write_objectStart_objectEnd_bigInteger();
        write_objectStart_objectEnd_bigDecimal();
        write_objectStart_objectEnd_float();
        write_objectStart_objectEnd_double();
        write_objectStart_objectEnd_boolean();
        write_objectStart_objectEnd_null();
    }

    @Test
    public void arrayStartArrayEndState() throws IOException {
        write_arrayStart_arrayEnd_objectStart();
        write_arrayStart_arrayEnd_objectEnd();
        write_arrayStart_arrayEnd_arrayStart();
        write_arrayStart_arrayEnd_arrayEnd();
        write_arrayStart_arrayEnd_string();
        write_arrayStart_arrayEnd_byte();
        write_arrayStart_arrayEnd_short();
        write_arrayStart_arrayEnd_int();
        write_arrayStart_arrayEnd_long();
        write_arrayStart_arrayEnd_bigInteger();
        write_arrayStart_arrayEnd_bigDecimal();
        write_arrayStart_arrayEnd_float();
        write_arrayStart_arrayEnd_double();
        write_arrayStart_arrayEnd_boolean();
        write_arrayStart_arrayEnd_null();
    }

    @Test
    public void notEmptyObjectStartState() throws IOException {
        write_objectStart_string_objectEnd();
        write_objectStart_string_string_arrayEnd();
        write_objectStart_string_string_byte();
        write_objectStart_string_string_short();
        write_objectStart_string_string_int();
        write_objectStart_string_string_long();
        write_objectStart_string_string_bigInteger();
        write_objectStart_string_string_bigDecimal();
        write_objectStart_string_string_float();
        write_objectStart_string_string_double();
        write_objectStart_string_string_boolean();
        write_objectStart_string_string_null();
        write_objectStart_string_string_string_objectEnd();
        write_objectStart_string_byte_string_objectEnd();
        write_objectStart_string_short_string_objectEnd();
        write_objectStart_string_int_string_objectEnd();
        write_objectStart_string_long_string_objectEnd();
        write_objectStart_string_bigInteger_string_objectEnd();
        write_objectStart_string_bigDecimal_string_objectEnd();
        write_objectStart_string_float_string_objectEnd();
        write_objectStart_string_double_string_objectEnd();
        write_objectStart_string_boolean_string_objectEnd();
        write_objectStart_string_null_string_objectEnd();
        write_objectStart_string_arrayEnd();
        write_objectStart_string_string_string_arrayEnd();
        write_objectStart_string_byte_string_arrayEnd();
        write_objectStart_string_short_string_arrayEnd();
        write_objectStart_string_int_string_arrayEnd();
        write_objectStart_string_long_string_arrayEnd();
        write_objectStart_string_bigInteger_string_arrayEnd();
        write_objectStart_string_bigDecimal_string_arrayEnd();
        write_objectStart_string_float_string_arrayEnd();
        write_objectStart_string_double_string_arrayEnd();
        write_objectStart_string_boolean_string_arrayEnd();
        write_objectStart_string_null_string_arrayEnd();
    }

    private void write_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_string() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeByte( ( byte ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeShort( ( short ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_bigInteger() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_bigDecimal() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeFloat( 0.0F );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeNull();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting { [", e.getMessage() );
        }
    }

    private void write_objectStart_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeByte( ( byte ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeShort( ( short ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_bigInteger() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_bigDecimal() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeFloat( 0.0F );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeNull();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_arrayStart_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting ] { [ STRING NUMBER true false null", e.getMessage() );
        }
    }

    private void write_arrayStart_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_byte_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeByte( ( byte ) 0 );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_short_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeShort( ( short ) 0 );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_int_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeInt( 0 );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_long_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeLong( 0L );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_bigInteger_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeBigInteger( BigInteger.ZERO );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_bigDecimal_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeBigDecimal( BigDecimal.ZERO );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_float_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeFloat( 0.0F );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_double_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeDouble( 0.0 );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_boolean_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeBoolean( true );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_arrayStart_null_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeNull();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , ]", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_objectStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_arrayStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeArrayStart();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_string() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeByte( ( byte ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeShort( ( short ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_bigInteger() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_bigDecimal() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeFloat( 0.0F );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeNull();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_objectStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeObjectStart();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_arrayStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeArrayStart();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_string() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeString( "" );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeByte( ( byte ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeShort( ( short ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_bigInteger() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_bigDecimal() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeFloat( 0.0F );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeNull();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting EOF", e.getMessage() );
        }
    }

    private void write_objectStart_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting , }", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeByte( ( byte ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeShort( ( short ) 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeInt( 0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeLong( 0L );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_bigInteger() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBigInteger( BigInteger.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_bigDecimal() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBigDecimal( BigDecimal.ZERO );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeFloat( 0.0F );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeDouble( 0.0 );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBoolean( true );
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeNull();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting } STRING", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_byte_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeByte( ( byte ) 0 );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_short_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeShort( ( short ) 0 );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_int_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeInt( 0 );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_long_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeLong( 0L );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigInteger_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeBigInteger( BigInteger.ZERO );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigDecimal_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeBigDecimal( BigDecimal.ZERO );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_float_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeFloat( 0.0F );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_double_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeDouble( 0.0 );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_boolean_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeBoolean( true );
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_null_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeNull();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_string_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_byte_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeByte( ( byte ) 0 );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_short_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeShort( ( short ) 0 );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_int_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeInt( 0 );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_long_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeLong( 0L );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigInteger_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeBigInteger( BigInteger.ZERO );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_bigDecimal_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeBigDecimal( BigDecimal.ZERO );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_float_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeFloat( 0.0F );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_double_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeDouble( 0.0 );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_boolean_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeBoolean( true );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }

    private void write_objectStart_string_null_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeNull();
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
            fail();
        } catch ( final JsonException e ) {
            assertEquals( "Expecting :", e.getMessage() );
        }
    }
}
