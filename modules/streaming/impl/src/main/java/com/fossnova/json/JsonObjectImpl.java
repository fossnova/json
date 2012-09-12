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
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.fossnova.json.JsonObject;
import org.fossnova.json.JsonStructure;
import org.fossnova.json.JsonValue;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonObjectImpl extends JsonStructureImpl implements JsonObject {

    private final Map< String, JsonValue > map;

    JsonObjectImpl() {
        this( null );
    }

    JsonObjectImpl( final Comparator< String > keyComparator ) {
        if ( keyComparator == null ) {
            map = new TreeMap< String, JsonValue >( String.CASE_INSENSITIVE_ORDER );
        } else {
            map = new TreeMap< String, JsonValue >( keyComparator );
        }
    }

    public JsonValue put( final String key, final String value ) {
        if ( key == null ) {
            throw new IllegalArgumentException( "JSON key cannot be null" );
        }
        return map.put( key, toJsonString( value ) );
    }

    public JsonValue put( final String key, final Boolean value ) {
        if ( key == null ) {
            throw new IllegalArgumentException( "JSON key cannot be null" );
        }
        return map.put( key, toJsonBoolean( value ) );
    }

    public JsonValue put( final String key, final Number value ) {
        if ( key == null ) {
            throw new IllegalArgumentException( "JSON key cannot be null" );
        }
        return map.put( key, toJsonNumber( value ) );
    }

    public JsonValue put( final String key, final JsonStructure value ) {
        if ( key == null ) {
            throw new IllegalArgumentException( "JSON key cannot be null" );
        }
        return map.put( key, value );
    }

    public boolean containsKey( final String key ) {
        return key != null ? map.containsKey( key ) : false;
    }

    public boolean containsValue( final String value ) {
        return map.containsValue( toJsonString( value ) );
    }

    public boolean containsValue( final Boolean value ) {
        return map.containsValue( toJsonBoolean( value ) );
    }

    public boolean containsValue( final Number value ) {
        return map.containsValue( toJsonNumber( value ) );
    }

    public boolean containsValue( final JsonStructure value ) {
        return map.containsValue( value );
    }

    public Collection< JsonValue > values() {
        return map.values();
    }

    public Set< Entry< String, JsonValue >> entrySet() {
        return map.entrySet();
    }

    public Set< String > keySet() {
        return map.keySet();
    }

    public JsonValue get( final String key ) {
        return map.get( key );
    }

    public JsonValue remove( final String key ) {
        return map.remove( key );
    }

    public void clear() {
        map.clear();
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonObjectImpl ) ) return false;
        final JsonObjectImpl a = ( JsonObjectImpl ) o;
        return map.equals( a.map );
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    protected void writeTo( final JsonWriterImpl jsonWriter ) throws IOException {
        jsonWriter.writeObjectStart();
        JsonValue jsonValue = null;
        for ( final String jsonKey : map.keySet() ) {
            jsonWriter.writeString( jsonKey );
            jsonValue = map.get( jsonKey );
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
        jsonWriter.writeObjectEnd();
        jsonWriter.flush();
    }
}
