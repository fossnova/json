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

import org.fossnova.json.JsonException;
import org.fossnova.json.JsonFactory;
import org.fossnova.json.JsonWriter;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
// TODO: implement assertion and remove system outs
public final class InvalidJsonTestCase {

    @Test
    public void testEmptyState() throws IOException {
        write_objectEnd();
        write_arrayEnd();
        write_string();
        write_byte();
        write_short();
        write_int();
        write_long();
        write_float();
        write_double();
        write_boolean();
        write_null();
    }

    @Test
    public void testEmptyObjectStartState() throws IOException {
        write_objectStart_arrayEnd();
        write_objectStart_byte();
        write_objectStart_short();
        write_objectStart_int();
        write_objectStart_long();
        write_objectStart_float();
        write_objectStart_double();
        write_objectStart_boolean();
        write_objectStart_null();
    }

    @Test
    public void testEmptyArrayStartState() throws IOException {
        write_arrayStart_objectEnd();
    }

    @Test
    public void testNotEmptyArrayStartState() throws IOException {
        write_arrayStart_string_objectEnd();
        write_arrayStart_byte_objectEnd();
        write_arrayStart_short_objectEnd();
        write_arrayStart_int_objectEnd();
        write_arrayStart_long_objectEnd();
        write_arrayStart_float_objectEnd();
        write_arrayStart_double_objectEnd();
        write_arrayStart_null_objectEnd();
    }

    @Test
    public void testObjectStartObjectEndState() throws IOException {
        write_objectStart_objectEnd_objectStart();
        write_objectStart_objectEnd_objectEnd();
        write_objectStart_objectEnd_arrayStart();
        write_objectStart_objectEnd_arrayEnd();
        write_objectStart_objectEnd_string();
        write_objectStart_objectEnd_byte();
        write_objectStart_objectEnd_short();
        write_objectStart_objectEnd_int();
        write_objectStart_objectEnd_long();
        write_objectStart_objectEnd_float();
        write_objectStart_objectEnd_double();
        write_objectStart_objectEnd_boolean();
        write_objectStart_objectEnd_null();
    }

    @Test
    public void testArrayStartArrayEndState() throws IOException {
        write_arrayStart_arrayEnd_objectStart();
        write_arrayStart_arrayEnd_objectEnd();
        write_arrayStart_arrayEnd_arrayStart();
        write_arrayStart_arrayEnd_arrayEnd();
        write_arrayStart_arrayEnd_string();
        write_arrayStart_arrayEnd_byte();
        write_arrayStart_arrayEnd_short();
        write_arrayStart_arrayEnd_int();
        write_arrayStart_arrayEnd_long();
        write_arrayStart_arrayEnd_float();
        write_arrayStart_arrayEnd_double();
        write_arrayStart_arrayEnd_boolean();
        write_arrayStart_arrayEnd_null();
    }

    @Test
    public void testNotEmptyObjectStartState() throws IOException {
        write_objectStart_string_objectEnd();
        write_objectStart_string_string_arrayEnd();
        write_objectStart_string_string_byte();
        write_objectStart_string_string_short();
        write_objectStart_string_string_int();
        write_objectStart_string_string_long();
        write_objectStart_string_string_float();
        write_objectStart_string_string_double();
        write_objectStart_string_string_boolean();
        write_objectStart_string_string_null();
        write_objectStart_string_string_string_objectEnd();
        write_objectStart_string_byte_string_objectEnd();
        write_objectStart_string_short_string_objectEnd();
        write_objectStart_string_int_string_objectEnd();
        write_objectStart_string_long_string_objectEnd();
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
        write_objectStart_string_float_string_arrayEnd();
        write_objectStart_string_double_string_arrayEnd();
        write_objectStart_string_boolean_string_arrayEnd();
        write_objectStart_string_null_string_arrayEnd();
    }

    private void write_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_string() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeString( "" );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeByte( ( byte ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeShort( ( short ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeInt( 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeLong( 0L );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeFloat( 0.0F );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeDouble( 0.0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeBoolean( true );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        try {
            writer.writeNull();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeByte( ( byte ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeShort( ( short ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeInt( 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeLong( 0L );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeFloat( 0.0F );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeDouble( 0.0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeBoolean( true );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        try {
            writer.writeNull();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_byte_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeByte( ( byte ) 0 );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_short_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeShort( ( short ) 0 );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_int_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeInt( 0 );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_long_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeLong( 0L );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_float_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeFloat( 0.0F );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_double_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeDouble( 0.0 );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_null_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeNull();
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_objectStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectStart();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_arrayStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeArrayStart();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_string() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeString( "" );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeByte( ( byte ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeShort( ( short ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeInt( 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeLong( 0L );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeFloat( 0.0F );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeDouble( 0.0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeBoolean( true );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_objectEnd_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeObjectEnd();
        try {
            writer.writeNull();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_objectStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeObjectStart();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_arrayStart() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeArrayStart();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_string() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeString( "" );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeByte( ( byte ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeShort( ( short ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeInt( 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeLong( 0L );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeFloat( 0.0F );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeDouble( 0.0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeBoolean( true );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_arrayStart_arrayEnd_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeArrayStart();
        writer.writeArrayEnd();
        try {
            writer.writeNull();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_objectEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writeObjectEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_arrayEnd() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_byte() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeByte( ( byte ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_short() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeShort( ( short ) 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_int() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeInt( 0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_long() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeLong( 0L );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_float() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeFloat( 0.0F );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_double() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeDouble( 0.0 );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_boolean() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeBoolean( true );
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void write_objectStart_string_string_null() throws IOException {
        final JsonWriter writer = getJsonWriter();
        writer.writeObjectStart();
        writer.writeString( "" );
        writer.writeString( "" );
        try {
            writer.writeNull();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
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
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private JsonWriter getJsonWriter() {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        return JsonFactory.newInstance().newJsonWriter( baos );
    }
}
