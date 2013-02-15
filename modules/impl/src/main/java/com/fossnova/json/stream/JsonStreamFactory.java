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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public final class JsonStreamFactory extends org.fossnova.json.stream.JsonStreamFactory {

    private static final Charset DEFAULT_CHARSET = Charset.forName( "UTF-8" );

    public JsonStreamFactory() {
    }

    @Override
    public JsonReader newJsonReader( final Reader reader ) {
        assertNotNullParameter( reader );
        return new JsonReader( reader );
    }

    @Override
    public JsonReader newJsonReader( final InputStream stream ) {
        return newJsonReader( stream, DEFAULT_CHARSET );
    }

    @Override
    public JsonReader newJsonReader( final InputStream stream, final Charset charset ) {
        assertNotNullParameter( stream );
        assertNotNullParameter( charset );
        return newJsonReader( new InputStreamReader( stream, charset ) );
    }

    @Override
    public JsonWriter newJsonWriter( final Writer writer ) {
        assertNotNullParameter( writer );
        return new JsonWriter( writer );
    }

    @Override
    public JsonWriter newJsonWriter( final OutputStream stream ) {
        return newJsonWriter( stream, DEFAULT_CHARSET );
    }

    @Override
    public JsonWriter newJsonWriter( final OutputStream stream, final Charset charset ) {
        assertNotNullParameter( stream );
        assertNotNullParameter( charset );
        return newJsonWriter( new OutputStreamWriter( stream, charset ) );
    }

    private static void assertNotNullParameter( final Object o ) {
        if ( o == null ) {
            throw new NullPointerException( "Parameter cannot be null" );
        }
    }
}
