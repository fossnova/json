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

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class Utils {

    static final char[] ONES;
    static final char[] TENS;

    static {
        ONES = new char[ 100 ];
        TENS = new char[ 100 ];
        for ( int i = 0; i < 100; i++ ) {
            ONES[ i ] = ( char ) ( '0' + ( i % 10 ) );
            TENS[ i ] = ( char ) ( '0' + ( i / 10 ) );
        }
    }

    private Utils() {
    }

    static boolean isNumberString( final char[] number, final int offset, final int length ) {
        // precondition: length > 0
        final int startIndex = number[ offset ] == '-' ? offset + 1 : offset;
        int dotIndex = offset;
        int eIndex = offset;
        final int limit = offset + length;
        for ( int i = startIndex; i < limit; i++ ) {
            if ( '0' <= number[ i ] && number[ i ] <= '9' ) continue;
            if ( number[ i ] == '.' ) {
                // number cannot start/end with dot
                if ( i == startIndex || i == limit - 1 ) return false;
                // there can be only one dot and it must be before e or E
                if ( dotIndex != offset || eIndex != offset ) return false;
                // number cannot start with 0
                if ( i - startIndex > 1 && number[ startIndex ] == '0' ) return false;
                dotIndex = i;
            } else if ( number[ i ] == 'e' || number[ i ] == 'E' ) {
                // number cannot start/end with e or E
                if ( i == startIndex || i == limit - 1 ) return false;
                // there can be only one e or E
                if ( eIndex != offset ) return false;
                // number cannot end with +/-
                if ( i == limit - 2 && ( number[ i + 1 ] == '+' || number[ i + 1 ] == '-' ) ) return false;
                // distance between e/E and dot cannot be 1
                if ( dotIndex > offset && i - dotIndex == 1 ) return false;
                // number cannot start with 0
                if ( dotIndex == offset && i - startIndex > 1 && number[ startIndex ] == '0' ) return false;
                eIndex = i;
                // skip processing of +/- after e/E
                if ( i < limit - 1 && ( number[ i + 1 ] == '+' || number[ i + 1 ] == '-' ) ) i++;
            } else return false; // +/- in unexpected location
        }
        if ( dotIndex == offset && eIndex == offset && length - ( startIndex - offset ) > 1 ) return number[ startIndex ] != '0';
        return true; // valid json number
    }

    static boolean isControl( final int c ) {
        return c <= '\u001F' || c >= '\u007F' && c <= '\u009F';
    }

    static boolean isWhitespace( final int c ) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    static boolean isNumberChar( final int c ) {
        return '0' <= c && c <= '9' || c == '-' || c == '+' || c == '.' || c == 'e' || c == 'E';
    }

    static String toUnicodeString( final int c ) {
        final StringBuilder sb = new StringBuilder();
        sb.append( BACKSLASH ).append( 'u' );
        final String hexString = Integer.toHexString( c );
        for ( int j = 0; j < ( 4 - hexString.length() ); j++ ) {
            sb.append( '0' );
        }
        sb.append( hexString );
        return sb.toString();
    }

    static int stringSizeOf( long l ) {
        int signSize = 1;
        if ( l >= 0 ) {
            signSize = 0;
            l = -l;
        }
        long temp = -10;
        for ( int j = 1; j < 19; j++ ) {
            if ( l > temp ) return j + signSize;
            temp = 10 * temp;
        }
        return 19 + signSize;
    }

    static int stringSizeOf( int i ) {
        int signSize = 1;
        if ( i >= 0 ) {
            signSize = 0;
            i = -i;
        }
        int temp = -10;
        for ( int j = 1; j < 10; j++ ) {
            if ( i > temp ) return j + signSize;
            temp = 10 * temp;
        }
        return 10 + signSize;
    }

}
