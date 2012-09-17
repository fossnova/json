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
package com.fossnova.json.stream;

import static com.fossnova.json.stream.JsonConstants.BACKSLASH;
import static com.fossnova.json.stream.JsonConstants.BACKSPACE;
import static com.fossnova.json.stream.JsonConstants.CR;
import static com.fossnova.json.stream.JsonConstants.FORMFEED;
import static com.fossnova.json.stream.JsonConstants.NL;
import static com.fossnova.json.stream.JsonConstants.QUOTE;
import static com.fossnova.json.stream.JsonConstants.SOLIDUS;
import static com.fossnova.json.stream.JsonConstants.TAB;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class Utils {

    private Utils() {
    }

    static boolean isControl( final int c ) {
        if ( ( c >= '\u0000' ) && ( c <= '\u001F' ) ) return true;
        return ( c >= '\u007F' ) && ( c <= '\u009F' );
    }

    static boolean isWhitespace( final int c ) {
        return ( c == ' ' ) || ( c == '\t' ) || ( c == '\r' ) || ( c == '\n' );
    }

    static boolean isNumberCharacter( final int c ) {
        if ( ( '0' <= c ) && ( c <= '9' ) ) return true;
        return ( c == '-' ) || ( c == '+' ) || ( c == '.' ) || ( c == 'e' ) || ( c == 'E' );
    }

    static boolean isStringEnd( final int firstChar, final int secondChar ) {
        return ( firstChar != BACKSLASH ) && ( secondChar == QUOTE );
    }

    static String toUnicodeString( final int c ) {
        final StringBuilder sb = new StringBuilder();
        sb.append( BACKSLASH ).append( 'u' );
        final String hexString = Integer.toHexString( c );
        for ( int j = 0; j < ( 4 - hexString.length() ); j++ ) {
            sb.append( '0' );
        }
        sb.append( hexString.toUpperCase() );
        return sb.toString();
    }

    static String encode( final String s ) {
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
                        retVal.append( toUnicodeString( character ) );
                    } else {
                        retVal.appendCodePoint( character );
                    }
                    break;
            }
        }
        retVal.append( QUOTE );
        return retVal.toString();
    }
}
