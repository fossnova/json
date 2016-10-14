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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.fossnova.json.JsonBoolean;
import org.fossnova.json.JsonValueFactory;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
public final class JsonBooleanTestCase extends AbstractJsonValuesTestCase {

    @Test
    public void gettersAndSetters() {
        final JsonBoolean jsonBoolean = JsonValueFactory.getInstance().newJsonBoolean( false );
        assertFalse( jsonBoolean.getBoolean() );
        jsonBoolean.setBoolean( true );
        assertTrue( jsonBoolean.getBoolean() );
    }

    @Test
    public void cloneMethod() {
        final JsonBoolean origBoolean = JsonValueFactory.getInstance().newJsonBoolean( false );
        final JsonBoolean clonedBoolean = origBoolean.clone();
        assertTrue( origBoolean != clonedBoolean );
        assertFalse( origBoolean.getBoolean() );
        assertFalse( clonedBoolean.getBoolean() );
        clonedBoolean.setBoolean( true );
        assertFalse( origBoolean.getBoolean() );
        assertTrue( clonedBoolean.getBoolean() );
    }
}
