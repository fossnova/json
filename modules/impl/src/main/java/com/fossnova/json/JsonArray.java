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
package com.fossnova.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import org.fossnova.json.JsonValue;
import org.fossnova.json.stream.JsonException;

import com.fossnova.json.stream.JsonWriter;

/**
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 */
final class JsonArray extends JsonStructure implements org.fossnova.json.JsonArray, RandomAccess {

    private static final long serialVersionUID = 1L;

    private final List< JsonValue > list = new ArrayList<>();

    JsonArray() {}

    @Override
    public boolean add( final String value ) {
        return list.add( toJsonString( value ) );
    }

    @Override
    public boolean add( final Number value ) {
        return list.add( toJsonNumber( value ) );
    }

    @Override
    public boolean add( final Boolean value ) {
        return list.add( toJsonBoolean( value ) );
    }

    @Override
    public boolean add( final JsonValue value ) {
        return list.add( value );
    }

    @Override
    public boolean addNull() {
        return list.add( null );
    }

    @Override
    public void add( final int index, final String value ) {
        list.add( index, toJsonString( value ) );
    }

    @Override
    public void add( final int index, final Number value ) {
        list.add( index, toJsonNumber( value ) );
    }

    @Override
    public void add( final int index, final Boolean value ) {
        list.add( index, toJsonBoolean( value ) );
    }

    @Override
    public void add( final int index, final JsonValue value ) {
        list.add( index, value );
    }

    @Override
    public void addNull( final int index ) {
        list.add( index, null );
    }

    @Override
    public boolean addAll( final Collection< ? extends JsonValue > values ) {
        return list.addAll( values );
    }

    @Override
    public boolean addAll( final int index, final Collection< ? extends JsonValue > values ) {
        return list.addAll( index, values );
    }

    @Override
    public boolean contains( final String value ) {
        return list.contains( toJsonString( value ) );
    }

    @Override
    public boolean contains( final Number value ) {
        return list.contains( toJsonNumber( value ) );
    }

    @Override
    public boolean contains( final Boolean value ) {
        return list.contains( toJsonBoolean( value ) );
    }

    @Override
    public boolean contains( final JsonValue value ) {
        return list.contains( value );
    }

    @Override
    public boolean containsNull() {
        return list.contains( null );
    }

    @Override
    public boolean contains( final Object value ) {
        if ( value instanceof String ) {
            return contains( ( String ) value );
        } else if ( value instanceof Number ) {
            return contains( ( Number ) value );
        } else if ( value instanceof Boolean ) {
            return contains( ( Boolean ) value );
        }
        return value == null ? containsNull() : contains( ( JsonValue ) value );
    }

    @Override
    public boolean containsAll( final Collection< ? > values ) {
        for ( final Object o : values ) {
            if ( !contains( o ) ) return false;
        }
        return true;
    }

    @Override
    public int indexOf( final String value ) {
        return list.indexOf( toJsonString( value ) );
    }

    @Override
    public int indexOf( final Number value ) {
        return list.indexOf( toJsonNumber( value ) );
    }

    @Override
    public int indexOf( final Boolean value ) {
        return list.indexOf( toJsonBoolean( value ) );
    }

    @Override
    public int indexOf( final JsonValue value ) {
        return list.indexOf( value );
    }

    @Override
    public int indexOfNull() {
        return list.indexOf( null );
    }

    @Override
    public int indexOf( final Object value ) {
        if ( value instanceof String ) {
            return indexOf( ( String ) value );
        } else if ( value instanceof Number ) {
            return indexOf( ( Number ) value );
        } else if ( value instanceof Boolean ) {
            return indexOf( ( Boolean ) value );
        }
        return value == null ? indexOfNull() : indexOf( ( JsonValue ) value );
    }

    @Override
    public int lastIndexOf( final String value ) {
        return list.lastIndexOf( toJsonString( value ) );
    }

    @Override
    public int lastIndexOf( final Number value ) {
        return list.lastIndexOf( toJsonNumber( value ) );
    }

    @Override
    public int lastIndexOf( final Boolean value ) {
        return list.lastIndexOf( toJsonBoolean( value ) );
    }

    @Override
    public int lastIndexOf( final JsonValue value ) {
        return list.lastIndexOf( value );
    }

    @Override
    public int lastIndexOfNull() {
        return list.lastIndexOf( null );
    }

    @Override
    public int lastIndexOf( final Object value ) {
        if ( value instanceof String ) {
            return lastIndexOf( ( String ) value );
        } else if ( value instanceof Number ) {
            return lastIndexOf( ( Number ) value );
        } else if ( value instanceof Boolean ) {
            return lastIndexOf( ( Boolean ) value );
        }
        return value == null ? lastIndexOfNull() : lastIndexOf( ( JsonValue ) value );
    }

    @Override
    public Iterator< JsonValue > iterator() {
        return list.iterator();
    }

    @Override
    public JsonValue get( final int index ) {
        return list.get( index );
    }

    @Override
    public boolean remove( final String value ) {
        return list.remove( toJsonString( value ) );
    }

    @Override
    public boolean remove( final Number value ) {
        return list.remove( toJsonNumber( value ) );
    }

    @Override
    public boolean remove( final Boolean value ) {
        return list.remove( toJsonBoolean( value ) );
    }

    @Override
    public boolean remove( final JsonValue value ) {
        return list.remove( value );
    }

    @Override
    public boolean removeNull() {
        return list.remove( null );
    }

    @Override
    public boolean remove( final Object value ) {
        if ( value instanceof String ) {
            return remove( ( String ) value );
        } else if ( value instanceof Number ) {
            return remove( ( Number ) value );
        } else if ( value instanceof Boolean ) {
            return remove( ( Boolean ) value );
        }
        return value == null ? removeNull() : remove( ( JsonValue ) value );
    }

    @Override
    public JsonValue remove( final int index ) {
        return list.remove( index );
    }

    @Override
    public boolean removeAll( final Collection< ? > values ) {
        return list.removeAll( toJsonValuesCollection( values ) );
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public JsonValue set( final int index, final String value ) {
        return list.set( index, toJsonString( value ) );
    }

    @Override
    public JsonValue set( final int index, final Number value ) {
        return list.set( index, toJsonNumber( value ) );
    }

    @Override
    public JsonValue set( final int index, final Boolean value ) {
        return list.set( index, toJsonBoolean( value ) );
    }

    @Override
    public JsonValue set( final int index, final JsonValue value ) {
        return list.set( index, value );
    }

    @Override
    public JsonValue setNull( final int index ) {
        return list.set( index, null );
    }

    @Override
    public ListIterator< JsonValue > listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator< JsonValue > listIterator( final int index ) {
        return list.listIterator( index );
    }

    @Override
    public JsonValue[] toArray() {
        return list.toArray( new JsonValue[ list.size() ] );
    }

    @Override
    public < T > T[] toArray( final T[] a ) {
        return list.toArray( a );
    }

    @Override
    public boolean retainAll( final Collection< ? > values ) {
        return list.retainAll( toJsonValuesCollection( values ) );
    }

    @Override
    public List< JsonValue > subList( final int fromIndex, final int toIndex ) {
        return list.subList( fromIndex, toIndex );
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonArray ) ) return false;
        final JsonArray a = ( JsonArray ) o;
        return list.equals( a.list );
    }

    @Override
    public JsonArray clone() {
        final JsonArray retVal = new JsonArray();
        for ( final JsonValue jsonValue : list ) {
            retVal.add( jsonValue != null ? jsonValue.clone() : null );
        }
        return retVal;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    protected void writeTo( final JsonWriter jsonWriter ) throws IOException, JsonException {
        jsonWriter.writeArrayStart();
        for ( final JsonValue jsonValue : list ) {
            if ( jsonValue instanceof JsonString ) {
                jsonWriter.writeString( ( ( JsonString ) jsonValue ).getString() );
            } else if ( jsonValue instanceof JsonNumber ) {
                jsonWriter.writeNumber( jsonValue.toString() );
            } else if ( jsonValue instanceof JsonBoolean ) {
                jsonWriter.writeBoolean( ( ( JsonBoolean ) jsonValue ).getBoolean() );
            } else if ( jsonValue instanceof JsonStructure ) {
                ( ( JsonStructure ) jsonValue ).writeTo( jsonWriter );
            } else if ( jsonValue == null ) {
                jsonWriter.writeNull();
            } else {
                throw new IllegalStateException();
            }
        }
        jsonWriter.writeArrayEnd();
        jsonWriter.flush();
    }

}
