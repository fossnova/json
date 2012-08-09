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
package com.fossnova.json;

import static com.fossnova.json.JsonConstants.ARRAY_END;
import static com.fossnova.json.JsonConstants.ARRAY_START;
import static com.fossnova.json.JsonConstants.BACKSLASH;
import static com.fossnova.json.JsonConstants.BACKSPACE;
import static com.fossnova.json.JsonConstants.COLON;
import static com.fossnova.json.JsonConstants.COMMA;
import static com.fossnova.json.JsonConstants.CR;
import static com.fossnova.json.JsonConstants.FORMFEED;
import static com.fossnova.json.JsonConstants.NL;
import static com.fossnova.json.JsonConstants.NULL;
import static com.fossnova.json.JsonConstants.OBJECT_END;
import static com.fossnova.json.JsonConstants.OBJECT_START;
import static com.fossnova.json.JsonConstants.QUOTE;
import static com.fossnova.json.JsonConstants.SOLIDUS;
import static com.fossnova.json.JsonConstants.TAB;

import java.io.IOException;
import java.io.OutputStream;

import org.fossnova.json.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonWriterImpl implements JsonWriter {

    private final OutputStream out;

    private final String encoding;

    private final JsonGrammarAnalyzer analyzer = new JsonGrammarAnalyzer();

    JsonWriterImpl( final OutputStream out, final String encoding ) {
        this.out = out;
        this.encoding = encoding;
    }

    public void writeObjectStart() throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.OBJECT_START );
        out.write( OBJECT_START );
    }

    public void writeObjectEnd() throws IOException {
        analyzer.put( JsonGrammarToken.OBJECT_END );
        out.write( OBJECT_END );
    }

    public void writeArrayStart() throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.ARRAY_START );
        out.write( ARRAY_START );
    }

    public void writeArrayEnd() throws IOException {
        analyzer.put( JsonGrammarToken.ARRAY_END );
        out.write( ARRAY_END );
    }

    public void writeString( final String data ) throws IOException {
        if ( data == null ) {
            throw new IllegalArgumentException( "Null parameter" );
        }
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.STRING );
        out.write( encode( data ).getBytes( encoding ) );
    }

    public void writeNull() throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NULL );
        out.write( NULL.getBytes() );
    }

    public void writeBoolean( final boolean data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.BOOLEAN );
        out.write( String.valueOf( data ).getBytes() );
    }

    public void writeByte( final byte data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ).getBytes() );
    }

    public void writeShort( final short data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ).getBytes() );
    }

    public void writeInt( final int data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ).getBytes() );
    }

    public void writeLong( final long data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ).getBytes() );
    }

    public void writeFloat( final float data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ).getBytes() );
    }

    public void writeDouble( final double data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        out.write( String.valueOf( data ).getBytes() );
    }

    private void writeOptionalColonOrComma() throws IOException {
        if ( analyzer.isColonExpected() ) {
            writeColon();
        } else if ( analyzer.isCommaExpected() ) {
            writeComma();
        }
    }

    private void writeColon() throws IOException {
        analyzer.put( JsonGrammarToken.COLON );
        out.write( COLON );
    }

    private void writeComma() throws IOException {
        analyzer.put( JsonGrammarToken.COMMA );
        out.write( COMMA );
    }

    private static String encode( final String s ) {
        final int length = s.length();
        final StringBuilder retVal = new StringBuilder( length + 16 );
        retVal.append( QUOTE );
        for ( int i = 0; i < length; i = s.offsetByCodePoints( i, 1 ) ) {
            final int character = s.codePointAt( i );
            switch ( character ) {
                case QUOTE:
                    retVal.append( BACKSLASH ).append( QUOTE );
                    break;
                case BACKSLASH:
                    retVal.append( BACKSLASH ).append( BACKSLASH );
                    break;
                case SOLIDUS:
                    retVal.append( BACKSLASH ).append( SOLIDUS );
                    break;
                case BACKSPACE:
                    retVal.append( BACKSLASH ).append( 'b' );
                    break;
                case FORMFEED:
                    retVal.append( BACKSLASH ).append( 'f' );
                    break;
                case NL:
                    retVal.append( BACKSLASH ).append( 'n' );
                    break;
                case CR:
                    retVal.append( BACKSLASH ).append( 'r' );
                    break;
                case TAB:
                    retVal.append( BACKSLASH ).append( 't' );
                    break;
                default:
                    if ( isControl( character ) ) {
                        retVal.append( BACKSLASH ).append( 'u' );
                        final String hexString = Integer.toHexString( character );
                        for ( int j = 0; j < ( 4 - hexString.length() ); j++ ) {
                            retVal.append( '0' );
                        }
                        retVal.append( hexString.toUpperCase() );
                    } else {
                        retVal.appendCodePoint( character );
                    }
                    break;
            }
        }
        retVal.append( QUOTE );
        return retVal.toString();
    }

    private static boolean isControl( final int c ) {
        // ASCII control characters
        if ( ( ( c >= '\u0000' ) && ( c <= '\u001F' ) ) || ( c == '\u007F' ) ) {
            return true;
        }
        // ISO-8859 control characters
        if ( ( c >= '\u0080' ) && ( c <= '\u009F' ) ) {
            return true;
        }
        // not unicode control character
        return false;
    }
}
