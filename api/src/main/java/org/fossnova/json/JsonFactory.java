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
package org.fossnova.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.fossnova.finder.FactoryFinder;

/**
 * Defines an abstract implementation of a factory for getting JSON readers,
 * writers and builders. All readers, writers and builders returned by the
 * factory are not thread safe.
 * 
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public abstract class JsonFactory {

    /**
     * All implementations must provide public default constructor overriding this one.
     */
    protected JsonFactory() {
    }

    /**
     * Returns JSON factory instance.
     * 
     * @return JSON factory instance
     */
    public static JsonFactory newInstance() {
        final JsonFactory jsonFactoryImpl = FactoryFinder.find( JsonFactory.class );
        if ( jsonFactoryImpl != null ) {
            return jsonFactoryImpl;
        }
        throw new IllegalStateException( "Factory not configured: " + JsonFactory.class.getName() );
    }

    /**
     * Creates new JSON reader.
     * 
     * @param reader input
     * @return JSON reader instance
     */
    public abstract JsonReader newJsonReader( Reader reader );

    /**
     * Creates new JSON writer.
     * 
     * @param writer output
     * @return JSON writer instance
     */
    public abstract JsonWriter newJsonWriter( Writer writer );

    /**
     * Creates new JSON builder.
     * 
     * @param writer output
     * @return JSON builder instance
     */
    public abstract JsonBuilder newJsonBuilder( Writer writer );

    /**
     * Creates new JSON reader with default character set.
     * 
     * @param stream input
     * @return JSON reader instance
     * @throws UnsupportedEncodingException if default character set is not supported
     */
    public abstract JsonReader newJsonReader( InputStream stream ) throws UnsupportedEncodingException;

    /**
     * Creates new JSON writer with default character set.
     * 
     * @param stream output
     * @return JSON writer instance
     * @throws UnsupportedEncodingException if default character set is not supported
     */
    public abstract JsonWriter newJsonWriter( OutputStream stream ) throws UnsupportedEncodingException;

    /**
     * Creates new JSON builder with default character set.
     * 
     * @param stream output
     * @return JSON builder instance
     * @throws UnsupportedEncodingException if default character set is not supported
     */
    public abstract JsonBuilder newJsonBuilder( OutputStream stream ) throws UnsupportedEncodingException;

    /**
     * Creates new JSON reader  with specified character set.
     * 
     * @param stream input
     * @param charsetName character set name
     * @return JSON reader instance
     * @throws UnsupportedEncodingException if specified character set is not supported
     */
    public abstract JsonReader newJsonReader( InputStream stream, String charsetName ) throws UnsupportedEncodingException;

    /**
     * Creates new JSON writer with specified character set.
     * 
     * @param stream output
     * @param charsetName character set name
     * @return JSON writer instance
     * @throws UnsupportedEncodingException if specified character set is not supported
     */
    public abstract JsonWriter newJsonWriter( OutputStream stream, String charsetName ) throws UnsupportedEncodingException;

    /**
     * Creates new JSON builder with specified character set.
     * 
     * @param stream output
     * @param charsetName character set name
     * @return JSON builder instance
     * @throws UnsupportedEncodingException if specified character set is not supported
     */
    public abstract JsonBuilder newJsonBuilder( OutputStream stream, String charsetName ) throws UnsupportedEncodingException;
}
