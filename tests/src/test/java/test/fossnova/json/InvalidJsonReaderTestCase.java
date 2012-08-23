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

import org.fossnova.json.JsonReader;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class InvalidJsonReaderTestCase extends AbstractJsonTestCase {

    // TODO: implement test - EOF at string, number, boolean, null, array or object structure

    @Test
    public void emptyState() throws IOException {
        read_colon();
        read_comma();
        read_objectEnd();
        read_arrayEnd();
        read_string();
        read_number();
        read_boolean();
        read_null();
    }

    @Test
    public void emptyObjectStartState() throws IOException {
        read_objectStart_arrayEnd();
        read_objectStart_colon();
        read_objectStart_comma();
        read_objectStart_number();
        read_objectStart_boolean();
        read_objectStart_null();
    }

    @Test
    public void emptyArrayStartState() throws IOException {
        read_arrayStart_objectEnd();
        read_arrayStart_colon();
        read_arrayStart_comma();
    }

    @Test
    public void notEmptyArrayStartState() throws IOException {
        read_arrayStart_arrayStart_arrayEnd_objectEnd();
        read_arrayStart_objectStart_objectEnd_objectEnd();
        read_arrayStart_string_objectEnd();
        read_arrayStart_number_objectEnd();
        read_arrayStart_boolean_objectEnd();
        read_arrayStart_null_objectEnd();
        read_arrayStart_arrayStart_arrayEnd_colon();
        read_arrayStart_objectStart_objectEnd_colon();
        read_arrayStart_string_colon();
        read_arrayStart_number_colon();
        read_arrayStart_boolean_colon();
        read_arrayStart_null_colon();
    }

    @Test
    public void objectStartObjectEndState() throws IOException {
        read_objectStart_objectEnd_objectStart();
        read_objectStart_objectEnd_objectEnd();
        read_objectStart_objectEnd_arrayStart();
        read_objectStart_objectEnd_arrayEnd();
        read_objectStart_objectEnd_string();
        read_objectStart_objectEnd_number();
        read_objectStart_objectEnd_boolean();
        read_objectStart_objectEnd_null();
        read_objectStart_objectEnd_colon();
        read_objectStart_objectEnd_comma();
    }

    @Test
    public void arrayStartArrayEndState() throws IOException {
        read_arrayStart_arrayEnd_objectStart();
        read_arrayStart_arrayEnd_objectEnd();
        read_arrayStart_arrayEnd_arrayStart();
        read_arrayStart_arrayEnd_arrayEnd();
        read_arrayStart_arrayEnd_string();
        read_arrayStart_arrayEnd_number();
        read_arrayStart_arrayEnd_boolean();
        read_arrayStart_arrayEnd_null();
        read_arrayStart_arrayEnd_colon();
        read_arrayStart_arrayEnd_comma();
    }

    @Test
    public void notEmptyObjectStartState() throws IOException {
        // TODO: review test cases - add comma & colon words to method names
        read_objectStart_string_comma();
        read_objectStart_string_objectEnd();
        read_objectStart_string_string_arrayEnd();
        read_objectStart_string_string_number();
        read_objectStart_string_string_boolean();
        read_objectStart_string_string_null();
        read_objectStart_string_string_colon();
        read_objectStart_string_string_string_objectEnd();
        read_objectStart_string_number_string_objectEnd();
        read_objectStart_string_boolean_string_objectEnd();
        read_objectStart_string_null_string_objectEnd();
        read_objectStart_string_string_string_comma();
        read_objectStart_string_number_string_comma();
        read_objectStart_string_boolean_string_comma();
        read_objectStart_string_null_string_comma();
        read_objectStart_string_arrayEnd();
        read_objectStart_string_string_string_arrayEnd();
        read_objectStart_string_number_string_arrayEnd();
        read_objectStart_string_boolean_string_arrayEnd();
        read_objectStart_string_null_string_arrayEnd();
        read_objectStart_string_string_string_comma();
        read_objectStart_string_number_string_comma();
        read_objectStart_string_boolean_string_comma();
        read_objectStart_string_null_string_comma();
    }

    private void read_colon() throws IOException {
        final JsonReader reader = getJsonReader( ":" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_comma() throws IOException {
        final JsonReader reader = getJsonReader( "," );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "}" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "]" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_string() throws IOException {
        final JsonReader reader = getJsonReader( "\"" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_number() throws IOException {
        final JsonReader reader = getJsonReader( "1" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_boolean() throws IOException {
        JsonReader reader = getJsonReader( "true" );
        assertJsonException( reader, "Expecting { [" );
        reader = getJsonReader( "false" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_null() throws IOException {
        final JsonReader reader = getJsonReader( "null" );
        assertJsonException( reader, "Expecting { [" );
    }

    private void read_objectStart_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{]" );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_colon() throws IOException {
        final JsonReader reader = getJsonReader( "{:" );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_comma() throws IOException {
        final JsonReader reader = getJsonReader( "{," );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_number() throws IOException {
        final JsonReader reader = getJsonReader( "{1" );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_boolean() throws IOException {
        JsonReader reader = getJsonReader( "{true" );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
        reader = getJsonReader( "{false" );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_null() throws IOException {
        final JsonReader reader = getJsonReader( "{null" );
        assertObjectStartState( reader );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_arrayStart_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[}" );
        assertArrayStartState( reader );
        assertJsonException( reader, "Expecting ] { [ STRING NUMBER true false null" );
    }

    private void read_arrayStart_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[:" );
        assertArrayStartState( reader );
        assertJsonException( reader, "Expecting ] { [ STRING NUMBER true false null" );
    }

    private void read_arrayStart_comma() throws IOException {
        final JsonReader reader = getJsonReader( "[," );
        assertArrayStartState( reader );
        assertJsonException( reader, "Expecting ] { [ STRING NUMBER true false null" );
    }

    private void read_arrayStart_arrayStart_arrayEnd_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[[]}" );
        assertArrayStartState( reader );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_objectStart_objectEnd_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[{}}" );
        assertArrayStartState( reader );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_string_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[\"\"}" );
        assertArrayStartState( reader );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_number_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[1.0}" );
        assertArrayStartState( reader );
        assertDoubleState( reader, 1.0 );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_boolean_objectEnd() throws IOException {
        JsonReader reader = getJsonReader( "[true}" );
        assertArrayStartState( reader );
        assertBooleanState( reader, true );
        assertJsonException( reader, "Expecting , ]" );
        reader = getJsonReader( "[false}" );
        assertArrayStartState( reader );
        assertBooleanState( reader, false );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_null_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[null}" );
        assertArrayStartState( reader );
        assertNullState( reader );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_arrayStart_arrayEnd_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[[]:" );
        assertArrayStartState( reader );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_objectStart_objectEnd_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[{}:" );
        assertArrayStartState( reader );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_string_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[\"\":" );
        assertArrayStartState( reader );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_number_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[1.0:" );
        assertArrayStartState( reader );
        assertDoubleState( reader, 1.0 );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_boolean_colon() throws IOException {
        JsonReader reader = getJsonReader( "[true:" );
        assertArrayStartState( reader );
        assertBooleanState( reader, true );
        assertJsonException( reader, "Expecting , ]" );
        reader = getJsonReader( "[false}" );
        assertArrayStartState( reader );
        assertBooleanState( reader, false );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_arrayStart_null_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[null:" );
        assertArrayStartState( reader );
        assertNullState( reader );
        assertJsonException( reader, "Expecting , ]" );
    }

    private void read_objectStart_objectEnd_objectStart() throws IOException {
        final JsonReader reader = getJsonReader( "{}{" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{}}" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_arrayStart() throws IOException {
        final JsonReader reader = getJsonReader( "{}[" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{}]" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_string() throws IOException {
        final JsonReader reader = getJsonReader( "{}\"\"" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_number() throws IOException {
        final JsonReader reader = getJsonReader( "{}0" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_boolean() throws IOException {
        JsonReader reader = getJsonReader( "{}true" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
        reader = getJsonReader( "{}false" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_null() throws IOException {
        final JsonReader reader = getJsonReader( "{}null" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_colon() throws IOException {
        final JsonReader reader = getJsonReader( "{}:" );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_objectEnd_comma() throws IOException {
        final JsonReader reader = getJsonReader( "{}," );
        assertObjectStartState( reader );
        assertObjectEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_objectStart() throws IOException {
        final JsonReader reader = getJsonReader( "[]{" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[]}" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_arrayStart() throws IOException {
        final JsonReader reader = getJsonReader( "[][" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "[]]" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_string() throws IOException {
        final JsonReader reader = getJsonReader( "[]\"\"" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_number() throws IOException {
        final JsonReader reader = getJsonReader( "[]1" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_boolean() throws IOException {
        JsonReader reader = getJsonReader( "[]true" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
        reader = getJsonReader( "[]false" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_null() throws IOException {
        final JsonReader reader = getJsonReader( "[]null" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_colon() throws IOException {
        final JsonReader reader = getJsonReader( "[]:" );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_arrayStart_arrayEnd_comma() throws IOException {
        final JsonReader reader = getJsonReader( "[]," );
        assertArrayStartState( reader );
        assertArrayEndState( reader );
        assertJsonException( reader, "Expecting EOF" );
    }

    private void read_objectStart_string_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_comma() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\"," );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_string_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\":\"\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting , }" );
    }

    private void read_objectStart_string_string_number() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\":\"\",1" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_string_string_boolean() throws IOException {
        JsonReader reader = getJsonReader( "{\"\":\"\",true" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting } STRING" );
        reader = getJsonReader( "{\"\":\"\",false" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_string_string_null() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\":\"\",null" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_string_string_colon() throws IOException {
        final JsonReader reader = getJsonReader( "{\"\":\"\",:" );
        assertObjectStartState( reader );
        assertStringState( reader, "" );
        assertStringState( reader, "" );
        assertJsonException( reader, "Expecting } STRING" );
    }

    private void read_objectStart_string_string_string_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":\"1\",\"2\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_number_string_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":1,\"2\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertIntState( reader, 1 );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_boolean_string_objectEnd() throws IOException {
        JsonReader reader = getJsonReader( "{\"0\":true,\"2\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertBooleanState( reader, true );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
        reader = getJsonReader( "{\"0\":false,\"2\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertBooleanState( reader, false );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_null_string_objectEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":null,\"2\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertNullState( reader );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_string_string_comma() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":\"1\",\"2\"," );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_number_string_comma() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":1,\"2\"," );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertIntState( reader, 1 );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_boolean_string_comma() throws IOException {
        JsonReader reader = getJsonReader( "{\"0\":true,\"2\"," );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertBooleanState( reader, true );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
        reader = getJsonReader( "{\"0\":false,\"2\"}" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertBooleanState( reader, false );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_null_string_comma() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":null,\"2\"," );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertNullState( reader );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_string_string_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":\"1\",\"2\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertStringState( reader, "1" );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_number_string_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":1,\"2\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertIntState( reader, 1 );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_boolean_string_arrayEnd() throws IOException {
        JsonReader reader = getJsonReader( "{\"0\":true,\"2\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertBooleanState( reader, true );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
        reader = getJsonReader( "{\"0\":false,\"2\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertBooleanState( reader, false );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }

    private void read_objectStart_string_null_string_arrayEnd() throws IOException {
        final JsonReader reader = getJsonReader( "{\"0\":null,\"2\"]" );
        assertObjectStartState( reader );
        assertStringState( reader, "0" );
        assertNullState( reader );
        assertStringState( reader, "2" );
        assertJsonException( reader, "Expecting :" );
    }
}
