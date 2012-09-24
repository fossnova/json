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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.fossnova.json.JsonString;
import org.fossnova.json.JsonValueFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonStringTestCase extends AbstractJsonValuesTestCase {

    @Test
    public void gettersAndSetters() {
        final JsonString jsonString = JsonValueFactory.newInstance().newJsonString( "foo" );
        assertEquals( "foo", jsonString.getString() );
        jsonString.setString( "bar" );
        assertEquals( "bar", jsonString.getString() );
    }

    @Test
    public void cloneMethod() {
        final JsonString origString = JsonValueFactory.newInstance().newJsonString( "foo" );
        final JsonString clonedString = origString.clone();
        assertTrue( origString != clonedString );
        assertEquals( "foo", origString.getString() );
        assertEquals( "foo", clonedString.getString() );
        clonedString.setString( "bar" );
        assertEquals( "foo", origString.getString() );
        assertEquals( "bar", clonedString.getString() );
    }
}
