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
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

import org.fossnova.json.stream.JsonException;
import org.fossnova.json.stream.JsonWriter;

/**
 * JSON array.
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 * @see JsonValue
 * @see JsonValueFactory
 */
public interface JsonArray extends JsonValue, List< JsonValue > {

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#add(Object)} method.
     * @param value string to wrap
     * @return <tt>true</tt> if this JSON array contained the specified JSON string
     */
    boolean add( final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#add(Object)} method.
     * @param value number to wrap
     * @return <tt>true</tt> if this JSON array contained the specified JSON number
     */
    boolean add( final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#add(Object)} method.
     * @param value boolean to wrap
     * @return <tt>true</tt> if this JSON array contained the specified JSON boolean
     */
    boolean add( final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#add(Object)} method.
     * @param value JSON value
     * @return <tt>true</tt> if this JSON array contained the specified JSON value
     */
    boolean add( final JsonValue value );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#add(int, Object)} method.
     * @param index index at which the specified JSON string is to be inserted
     * @param value string to wrap
     */
    void add( final int index, final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#add(int, Object)} method.
     * @param index index at which the specified JSON number is to be inserted
     * @param value number to wrap
     */
    void add( final int index, final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#add(int, Object)} method.
     * @param index index at which the specified JSON boolean is to be inserted
     * @param value boolean to wrap
     */
    void add( final int index, final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#add(int, Object)} method.
     * @param index index at which the specified JSON value is to be inserted
     * @param value JSON value
     */
    void add( final int index, final JsonValue value );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#contains(Object)} method.
     * @param value string to wrap
     * @return <tt>true</tt> if this JSON array contains the specified JSON string
     */
    boolean contains( final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#contains(Object)} method.
     * @param value number to wrap
     * @return <tt>true</tt> if this JSON array contains the specified JSON number
     */
    boolean contains( final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#contains(Object)} method.
     * @param value boolean to wrap
     * @return <tt>true</tt> if this JSON array contains the specified JSON boolean
     */
    boolean contains( final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#contains(Object)} method.
     * @param value JSON value
     * @return <tt>true</tt> if this JSON array contains the specified JSON value
     */
    boolean contains( final JsonValue value );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#indexOf(Object)} method.
     * @param value string to wrap
     * @return the index of the first occurrence of the specified JSON string in
     *         this JSON array, or -1 if this JSON array does not contain the JSON string
     */
    int indexOf( final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#indexOf(Object)} method.
     * @param value number to wrap
     * @return the index of the first occurrence of the specified JSON number in
     *         this JSON array, or -1 if this JSON array does not contain the JSON number
     */
    int indexOf( final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#indexOf(Object)} method.
     * @param value boolean to wrap
     * @return the index of the first occurrence of the specified JSON boolean in
     *         this JSON array, or -1 if this JSON array does not contain the JSON boolean
     */
    int indexOf( final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#indexOf(Object)} method.
     * @param value JSON value
     * @return the index of the first occurrence of the specified JSON value in
     *         this JSON array, or -1 if this JSON array does not contain the JSON value
     */
    int indexOf( final JsonValue value );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#lastIndexOf(Object)} method.
     * @param value string to wrap
     * @return he index of the last occurrence of the specified JSON string in
     *         this JSON array, or -1 if this JSON array does not contain the JSON string
     */
    int lastIndexOf( final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#lastIndexOf(Object)} method.
     * @param value string to wrap
     * @return he index of the last occurrence of the specified JSON number in
     *         this JSON array, or -1 if this JSON array does not contain the JSON number
     */
    int lastIndexOf( final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#lastIndexOf(Object)} method.
     * @param value string to wrap
     * @return he index of the last occurrence of the specified JSON boolean in
     *         this JSON array, or -1 if this JSON array does not contain the JSON boolean
     */
    int lastIndexOf( final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#lastIndexOf(Object)} method.
     * @param value JSON value
     * @return he index of the last occurrence of the specified JSON value in
     *         this JSON array, or -1 if this JSON array does not contain the JSON value
     */
    int lastIndexOf( final JsonValue value );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#remove(Object)} method.
     * @param value string to wrap
     * @return <tt>true</tt> if this JSON array contained the specified JSON string
     */
    boolean remove( final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#remove(Object)} method.
     * @param value number to wrap
     * @return <tt>true</tt> if this JSON array contained the specified JSON number
     */
    boolean remove( final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#remove(Object)} method.
     * @param value boolean to wrap
     * @return <tt>true</tt> if this JSON array contained the specified JSON boolean
     */
    boolean remove( final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#remove(Object)} method.
     * @param value JSON value
     * @return <tt>true</tt> if this JSON array contained the specified JSON value
     */
    boolean remove( final JsonValue value );

    /**
     * Wraps passed value with JsonString and delegates the call to
     * {@link java.util.List#set(int, Object)} method.
     * @param index index of the JSON value to replace 
     * @param value string to wrap
     * @return the JSON value previously held at the specified position
     */
    JsonValue set( final int index, final String value );

    /**
     * Wraps passed value with JsonNumber and delegates the call to
     * {@link java.util.List#set(int, Object)} method.
     * @param index index of the JSON value to replace 
     * @param value number to wrap
     * @return the JSON value previously held at the specified position
     */
    JsonValue set( final int index, final Number value );

    /**
     * Wraps passed value with JsonBoolean and delegates the call to
     * {@link java.util.List#set(int, Object)} method.
     * @param index index of the JSON value to replace 
     * @param value boolean to wrap
     * @return the JSON value previously held at the specified position
     */
    JsonValue set( final int index, final Boolean value );

    /**
     * Delegates the call to {@link java.util.List#set(int, Object)} method.
     * @param index index of the JSON value to replace 
     * @param value JSON value
     * @return the JSON value previously held at the specified position
     */
    JsonValue set( final int index, final JsonValue value );

    /**
     * Translates this JSON array to Java array.
     * @return java array 
     */
    JsonValue[] toArray();

    /**
     * Serializes this JSON array to the writer.
     * @param output to write to
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    void writeTo( JsonWriter output ) throws IOException, JsonException;

    /**
     * Serializes this JSON array to the writer.
     * @param output to write to
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    void writeTo( Writer output ) throws IOException, JsonException;

    /**
     * Serializes this JSON array to the stream using default character set.
     * @param output to write to
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    void writeTo( OutputStream output ) throws IOException, JsonException;

    /**
     * Serializes this JSON array to the writer using specified character set.
     * @param output to write to
     * @param charset character set
     * @throws IOException if some I/O error occurs
     * @throws JsonException if wrong JSON is detected
     */
    void writeTo( OutputStream output, Charset charset ) throws IOException, JsonException;

    /**
     * Clones this JSON array.
     * @return new JSON array clone.
     */
    JsonArray clone();
}
