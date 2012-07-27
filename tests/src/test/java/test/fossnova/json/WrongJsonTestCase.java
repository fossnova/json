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

import org.fossnova.json.JsonException;
import org.fossnova.json.JsonFactory;
import org.fossnova.json.JsonWriter;
import org.junit.Test;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class WrongJsonTestCase {

    @Test
    public void test1() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final JsonWriter writer = JsonFactory.newInstance().newJsonWriter( baos );
        try {
            writer.writeArrayEnd();
        } catch ( final JsonException e ) {
            System.out.println( e.getMessage() );
        }
    }
}
