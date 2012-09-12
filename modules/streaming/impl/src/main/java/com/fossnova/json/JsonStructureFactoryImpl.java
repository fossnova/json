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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;

import org.fossnova.json.JsonArray;
import org.fossnova.json.JsonObject;
import org.fossnova.json.JsonStructure;
import org.fossnova.json.JsonStructureFactory;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonStructureFactoryImpl extends JsonStructureFactory {

    @Override
    public JsonStructure readFrom( final Reader reader ) throws IOException {
        final JsonReaderImpl jsonReader = new JsonStreamFactoryImpl().newJsonReader( reader );
        return readFrom( jsonReader );
    }

    @Override
    public JsonStructure readFrom( final InputStream stream ) throws UnsupportedEncodingException, IOException {
        final JsonReaderImpl jsonReader = new JsonStreamFactoryImpl().newJsonReader( stream );
        return readFrom( jsonReader );
    }

    @Override
    public JsonStructure readFrom( final InputStream stream, final String charsetName ) throws UnsupportedEncodingException, IOException {
        final JsonReaderImpl jsonReader = new JsonStreamFactoryImpl().newJsonReader( stream, charsetName );
        return readFrom( jsonReader );
    }

    private JsonStructureImpl readFrom( final JsonReaderImpl jsonReader ) throws IOException {
        throw new UnsupportedOperationException(); // TODO: implement
    }

    @Override
    public JsonObject newJsonObject() {
        return new JsonObjectImpl();
    }

    @Override
    public JsonObject newJsonObject( final Comparator< String > keyComparator ) {
        return new JsonObjectImpl( keyComparator );
    }

    @Override
    public JsonArray newJsonArray() {
        return new JsonArrayImpl();
    }

    private static void assertNotNullParameter( final Object o ) {
        if ( o == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
    }
}
