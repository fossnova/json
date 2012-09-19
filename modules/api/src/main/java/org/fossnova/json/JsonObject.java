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
import java.util.Map;

import org.fossnova.json.stream.JsonWriter;

/**
 * JSON object.
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 * @see JsonValue
 * @see JsonValueFactory
 */
public interface JsonObject extends JsonValue, Map< String, JsonValue > {

    /**
     * Delegates the call to {@link java.util.Map#containsKey(Object)} method.
     * @param key JSON string
     * @return <tt>true</tt> if this map contains a mapping for the specified key
     */
    boolean containsKey( final String key );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.Map#containsValue(Object)} method.
     * @param value string to wrap
     * @return <tt>true</tt> if this JSON object maps one or more keys to the specified value
     */
    boolean containsValue( final String value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.Map#containsValue(Object)} method.
     * @param value boolean to wrap
     * @return <tt>true</tt> if this JSON object maps one or more keys to the specified value
     */
    boolean containsValue( final Boolean value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.Map#containsValue(Object)} method.
     * @param value number to wrap
     * @return <tt>true</tt> if this JSON object maps one or more keys to the specified value
     */
    boolean containsValue( final Number value );

    /**
     * Delegates the call to {@link java.util.Map#containsValue(Object)} method.
     * @param value JSON value
     * @return <tt>true</tt> if this JSON object maps one or more keys to the specified value
     */
    boolean containsValue( final JsonValue value );

    /**
     * Delegates the call to {@link java.util.Map#get(Object)} method.
     * @param key JSON string
     * @return the JSON value to which the specified key is mapped, or
     *         {@code null} if this JSON object contains no mapping for the key
     */
    JsonValue get( final String key );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.Map#put(Object, Object)} method.
     * @param key JSON string
     * @param value string to wrap
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     */
    JsonValue put( final String key, final String value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.Map#put(Object, Object)} method.
     * @param key JSON string
     * @param value boolean to wrap
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     */
    JsonValue put( final String key, final Boolean value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.Map#put(Object, Object)} method.
     * @param key JSON string
     * @param value number to wrap
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     */
    JsonValue put( final String key, final Number value );

    /**
     * Delegates the call to {@link java.util.Map#remove(Object)} method.
     * @param key JSON string
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     */
    JsonValue remove( final String key );

    /**
     * Serializes this JSON object to the writer.
     * @param writer to write to
     * @throws IOException if some I/O error occurs.
     */
    void writeTo( JsonWriter writer ) throws IOException;

    /**
     * Clones this JSON object.
     * @return new JSON object clone.
     */
    JsonObject clone();
}
