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
import static com.fossnova.json.JsonConstants.OBJECT_END;
import static com.fossnova.json.JsonConstants.OBJECT_START;
import static com.fossnova.json.JsonConstants.QUOTE;
import static com.fossnova.json.JsonConstants.SOLIDUS;
import static com.fossnova.json.JsonConstants.TAB;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import org.fossnova.json.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonWriterImpl implements JsonWriter {

    private static final byte[] ARRAY_START_DATA = wrap( ARRAY_START );

    private static final byte[] ARRAY_END_DATA = wrap( ARRAY_END );

    private static final byte[] OBJECT_START_DATA = wrap( OBJECT_START );

    private static final byte[] OBJECT_END_DATA = wrap( OBJECT_END );

    private static final byte[] COLON_DATA = wrap( COLON );

    private static final byte[] COMMA_DATA = wrap( COMMA );

    private static final byte[] NULL_DATA = "null".getBytes();

    private final WritableByteChannel channel;

    private final String encoding;

    private final JsonGrammarAnalyzer analyzer = new JsonGrammarAnalyzer();

    JsonWriterImpl( final WritableByteChannel channel, final String encoding ) {
        this.channel = channel;
        this.encoding = encoding;
    }

    public void writeObjectStart() throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.OBJECT_START );
        write( OBJECT_START_DATA );
    }

    public void writeObjectEnd() throws IOException {
        analyzer.put( JsonGrammarToken.OBJECT_END );
        write( OBJECT_END_DATA );
    }

    public void writeArrayStart() throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.ARRAY_START );
        write( ARRAY_START_DATA );
    }

    public void writeArrayEnd() throws IOException {
        analyzer.put( JsonGrammarToken.ARRAY_END );
        write( ARRAY_END_DATA );
    }

    public void writeString( final String data ) throws IOException {
        if ( data == null ) {
            throw new IllegalArgumentException( "Null parameter" );
        }
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.STRING );
        write( escape( data ).getBytes( encoding ) );
    }

    public void writeNull() throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NULL );
        write( NULL_DATA );
    }

    public void writeBoolean( final boolean data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.BOOLEAN );
        write( String.valueOf( data ).getBytes() );
    }

    public void writeByte( final byte data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        write( String.valueOf( data ).getBytes() );
    }

    public void writeShort( final short data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        write( String.valueOf( data ).getBytes() );
    }

    public void writeInt( final int data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        write( String.valueOf( data ).getBytes() );
    }

    public void writeLong( final long data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        write( String.valueOf( data ).getBytes() );
    }

    public void writeFloat( final float data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        write( String.valueOf( data ).getBytes() );
    }

    public void writeDouble( final double data ) throws IOException {
        writeOptionalColonOrComma();
        analyzer.put( JsonGrammarToken.NUMBER );
        write( String.valueOf( data ).getBytes() );
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
        write( COLON_DATA );
    }

    private void writeComma() throws IOException {
        analyzer.put( JsonGrammarToken.COMMA );
        write( COMMA_DATA );
    }

    private void write( final byte[] data ) throws IOException {
        channel.write( ByteBuffer.wrap( data ) );
    }

    private static String escape( final String s ) {
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

    private static byte[] wrap( final int character ) {
        final byte[] retVal = new byte[ 1 ];
        retVal[ 0 ] = ( byte ) character;
        return retVal;
    }
}
