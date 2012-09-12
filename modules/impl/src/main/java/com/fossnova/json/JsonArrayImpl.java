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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.fossnova.json.JsonArray;
import org.fossnova.json.JsonStructure;
import org.fossnova.json.JsonValue;

import com.fossnova.json.stream.JsonWriterImpl;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonArrayImpl extends JsonStructureImpl implements JsonArray {

    private final List< JsonValue > list;

    JsonArrayImpl() {
        list = new ArrayList< JsonValue >();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains( final String value ) {
        return list.contains( toJsonString( value ) );
    }

    public boolean contains( final Number value ) {
        return list.contains( toJsonNumber( value ) );
    }

    public boolean contains( final Boolean value ) {
        return list.contains( toJsonBoolean( value ) );
    }

    public boolean contains( final JsonStructure value ) {
        return list.contains( value );
    }

    public Iterator< JsonValue > iterator() {
        return list.iterator();
    }

    public JsonValue[] toArray() {
        return list.toArray( new JsonValue[ list.size() ] );
    }

    public boolean add( final String value ) {
        return addInternal( toJsonString( value ) );
    }

    public boolean add( final Number value ) {
        return addInternal( toJsonNumber( value ) );
    }

    public boolean add( final Boolean value ) {
        return addInternal( toJsonBoolean( value ) );
    }

    public boolean add( final JsonStructure value ) {
        return addInternal( value );
    }

    boolean addInternal( final JsonValue value ) {
        return list.add( value );
    }

    public boolean remove( final String value ) {
        return list.remove( toJsonString( value ) );
    }

    public boolean remove( final Number value ) {
        return list.remove( toJsonNumber( value ) );
    }

    public boolean remove( final Boolean value ) {
        return list.remove( toJsonBoolean( value ) );
    }

    public boolean remove( final JsonStructure value ) {
        return list.remove( value );
    }

    public void clear() {
        list.clear();
    }

    public JsonValue get( final int index ) {
        return list.get( index );
    }

    public JsonValue set( final int index, final String value ) {
        return list.set( index, toJsonString( value ) );
    }

    public JsonValue set( final int index, final Number value ) {
        return list.set( index, toJsonNumber( value ) );
    }

    public JsonValue set( final int index, final Boolean value ) {
        return list.set( index, toJsonBoolean( value ) );
    }

    public JsonValue set( final int index, final JsonStructure value ) {
        return list.set( index, value );
    }

    public void add( final int index, final String value ) {
        list.add( index, toJsonString( value ) );
    }

    public void add( final int index, final Number value ) {
        list.add( index, toJsonNumber( value ) );
    }

    public void add( final int index, final Boolean value ) {
        list.add( index, toJsonBoolean( value ) );
    }

    public void add( final int index, final JsonStructure value ) {
        list.add( index, value );
    }

    public JsonValue remove( final int index ) {
        return list.remove( index );
    }

    public int indexOf( final String value ) {
        return list.indexOf( toJsonString( value ) );
    }

    public int indexOf( final Number value ) {
        return list.indexOf( toJsonNumber( value ) );
    }

    public int indexOf( final Boolean value ) {
        return list.indexOf( toJsonBoolean( value ) );
    }

    public int indexOf( final JsonStructure value ) {
        return list.indexOf( value );
    }

    public int lastIndexOf( final String value ) {
        return list.lastIndexOf( toJsonString( value ) );
    }

    public int lastIndexOf( final Number value ) {
        return list.lastIndexOf( toJsonNumber( value ) );
    }

    public int lastIndexOf( final Boolean value ) {
        return list.lastIndexOf( toJsonBoolean( value ) );
    }

    public int lastIndexOf( final JsonStructure value ) {
        return list.lastIndexOf( value );
    }

    public ListIterator< JsonValue > listIterator() {
        return list.listIterator();
    }

    public ListIterator< JsonValue > listIterator( int index ) {
        return list.listIterator( index );
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonArrayImpl ) ) return false;
        final JsonArrayImpl a = ( JsonArrayImpl ) o;
        return list.equals( a.list );
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    protected void writeTo( final JsonWriterImpl jsonWriter ) throws IOException {
        jsonWriter.writeArrayStart();
        for ( final JsonValue jsonValue : list ) {
            if ( jsonValue == null ) {
                jsonWriter.writeNull();
            } else if ( jsonValue instanceof JsonBooleanImpl ) {
                jsonWriter.writeBoolean( ( ( JsonBooleanImpl ) jsonValue ).getBoolean() );
            } else if ( jsonValue instanceof JsonNumberImpl ) {
                jsonWriter.writeNumber( ( ( JsonNumberImpl ) jsonValue ).toString() );
            } else if ( jsonValue instanceof JsonStringImpl ) {
                jsonWriter.writeString( ( ( JsonStringImpl ) jsonValue ).getString() );
            } else if ( jsonValue instanceof JsonStructureImpl ) {
                ( ( JsonStructureImpl ) jsonValue ).writeTo( jsonWriter );
            } else {
                throw new IllegalStateException();
            }
        }
        jsonWriter.writeArrayEnd();
        jsonWriter.flush();
    }
}
