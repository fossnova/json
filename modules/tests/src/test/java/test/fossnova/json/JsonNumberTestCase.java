/*
 * Copyright (c) 2012-2020, FOSS Nova Software foundation (FNSF),
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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.fossnova.json.JsonNumber;
import org.fossnova.json.JsonValueFactory;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class JsonNumberTestCase extends AbstractJsonValuesTestCase {

    @Test
    public void gettersAndSetters() {
        final JsonNumber jsonNumber = JsonValueFactory.getInstance().newJsonNumber( 0 );
        assertEquals( BigDecimal.ZERO, jsonNumber.getBigDecimal() );
        jsonNumber.setBigDecimal( BigDecimal.ONE );
        assertEquals( BigInteger.ONE, jsonNumber.getBigInteger() );
        jsonNumber.setBigInteger( new BigInteger( "2" ) );
        assertTrue( ( byte ) 2 == jsonNumber.getByte() );
        jsonNumber.setByte( ( byte ) 3 );
        assertTrue( ( short ) 3 == jsonNumber.getShort() );
        jsonNumber.setShort( ( short ) 4 );
        assertTrue( 4 == jsonNumber.getInt() );
        jsonNumber.setInt( 5 );
        assertTrue( 5L == jsonNumber.getLong() );
        jsonNumber.setLong( 6L );
        assertTrue( 6.0F == jsonNumber.getFloat() );
        jsonNumber.setFloat( 7.0F );
        assertTrue( 7.0 == jsonNumber.getDouble() );
        jsonNumber.setDouble( 8.0 );
        assertTrue( 8.0 == jsonNumber.getDouble() );
    }

    @Test
    public void cloneMethod() {
        final JsonNumber origNumber = JsonValueFactory.getInstance().newJsonNumber( 1 );
        final JsonNumber clonedNumber = origNumber.clone();
        assertTrue( origNumber != clonedNumber );
        assertTrue( 1 == origNumber.getInt() );
        assertTrue( 1 == clonedNumber.getInt() );
        clonedNumber.setInt( 0 );
        assertTrue( 1 == origNumber.getInt() );
        assertTrue( 0 == clonedNumber.getInt() );
    }
}
