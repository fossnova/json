/*
 * Copyright (c) 2012-2017, FOSS Nova Software foundation (FNSF),
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
package org.fossnova.json.stream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.fossnova.finder.FactoryFinder;

/**
 * JSON streams factory. Defines an abstract implementation of a factory for getting JSON readers and
 * writers. All readers and writers returned by this factory are not thread safe.
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 * @see JsonReader
 * @see JsonWriter
 */
public abstract class JsonStreamFactory {

    private static final JsonStreamFactory FACTORY = FactoryFinder.find( JsonStreamFactory.class );

    /**
     * All implementations must provide public default constructor overriding this one.
     */
    protected JsonStreamFactory() {
    }

    /**
     * Returns JSON stream factory instance.
     * @return JSON stream factory instance
     */
    public static JsonStreamFactory getInstance() {
        return FACTORY;
    }

    /**
     * Creates new JSON reader.
     * @param reader input
     * @return JSON reader instance
     */
    public abstract JsonReader newJsonReader( Reader reader );

    /**
     * Creates new JSON writer.
     * @param writer output
     * @return JSON writer instance
     */
    public abstract JsonWriter newJsonWriter( Writer writer );

    /**
     * Creates new JSON reader with <code>UTF-8</code> character set.
     * @param stream input
     * @return JSON reader instance
     */
    public abstract JsonReader newJsonReader( InputStream stream );

    /**
     * Creates new JSON writer with <code>UTF-8</code> character set.
     * @param stream output
     * @return JSON writer instance
     */
    public abstract JsonWriter newJsonWriter( OutputStream stream );

    /**
     * Creates new JSON reader with specified character set.
     * @param stream input
     * @param charset character set
     * @return JSON reader instance
     */
    public abstract JsonReader newJsonReader( InputStream stream, Charset charset );

    /**
     * Creates new JSON writer with specified character set.
     * @param stream output
     * @param charset character set
     * @return JSON writer instance
     */
    public abstract JsonWriter newJsonWriter( OutputStream stream, Charset charset );
}
