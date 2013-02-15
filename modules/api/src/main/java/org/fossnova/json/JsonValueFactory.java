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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;

import org.fossnova.finder.FactoryFinder;
import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonReader;

/**
 * JSON values factory. Defines an abstract implementation of a factory for creating JSON values.
 * All JSON values returned by this factory are not thread safe.
 *
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 * @see JsonArray
 * @see JsonBoolean
 * @see JsonNumber
 * @see JsonObject
 * @see JsonString
 */
public abstract class JsonValueFactory {

    /**
     * All implementations must provide public default constructor overriding this one.
     */
    protected JsonValueFactory() {
    }

    /**
     * Returns new JSON value factory instance.
     * @return new JSON value factory instance
     */
    public static JsonValueFactory newInstance() {
        final JsonValueFactory jsonFactory = FactoryFinder.find( JsonValueFactory.class );
        if ( jsonFactory == null ) {
            throw new IllegalStateException( "Factory not configured: " + JsonValueFactory.class.getName() );
        }
        return jsonFactory;
    }

    /**
     * Creates new JSON object.
     * @return JSON object instance
     */
    public abstract JsonObject newJsonObject();

    /**
     * Creates new JSON array.
     * @return JSON array instance
     */
    public abstract JsonArray newJsonArray();

    /**
     * Creates new JSON boolean.
     * @param value boolean value
     * @return JSON boolean instance
     */
    public abstract JsonBoolean newJsonBoolean( Boolean value );

    /**
     * Creates new JSON number.
     * @param value number value
     * @return JSON number instance
     */
    public abstract JsonNumber newJsonNumber( Number value );

    /**
     * Creates new JSON string.
     * @param value string value
     * @return JSON string instance
     */
    public abstract JsonString newJsonString( String value );

    /**
     * Creates either JSON array or object instance.
     * @param input to read JSON from
     * @return JSON array or object instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    public abstract JsonValue readFrom( JsonReader input ) throws IOException, JsonException;

    /**
     * Creates either JSON array or object instance.
     * @param input to read JSON from
     * @return JSON array or object instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    public abstract JsonValue readFrom( Reader input ) throws IOException, JsonException;

    /**
     * Creates either JSON array or object instance.
     * @param input to read JSON from
     * @return JSON array or object instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    public abstract JsonValue readFrom( String input ) throws IOException, JsonException;
    
    /**
     * Creates either JSON array or object instance using <code>UTF-8</code> character set.
     * @param input to read JSON from
     * @return JSON array or object instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    public abstract JsonValue readFrom( InputStream input ) throws IOException, JsonException;
    
    /**
     * Creates either JSON array or object instance using specified character set.
     * @param data JSON available via input stream
     * @param charset character set
     * @return JSON array or object instance
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    public abstract JsonValue readFrom( InputStream data, Charset charset ) throws IOException, JsonException;
}
