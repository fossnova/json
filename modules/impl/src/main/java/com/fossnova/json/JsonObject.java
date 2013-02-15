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
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.fossnova.json.JsonValue;
import org.fossnova.json.stream.JsonException;

import com.fossnova.json.stream.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonObject extends JsonStructure implements org.fossnova.json.JsonObject {

    private static final long serialVersionUID = 1L;

    private final Map< String, JsonValue > map;

    private final Map< String, JsonValue > userView;

    JsonObject() {
        map = new TreeMap< String, JsonValue >();
        userView = Collections.unmodifiableMap( map );
    }

    @Override
    public JsonValue put( final String key, final String value ) {
        return putInternal( key, toJsonString( value ) );
    }

    @Override
    public JsonValue put( final String key, final Boolean value ) {
        return putInternal( key, toJsonBoolean( value ) );
    }

    @Override
    public JsonValue put( final String key, final Number value ) {
        return putInternal( key, toJsonNumber( value ) );
    }

    @Override
    public JsonValue put( final String key, final JsonValue value ) {
        return putInternal( key, value );
    }

    @Override
    public boolean containsKey( final Object key ) {
        return containsKey( ( String ) key );
    }

    @Override
    public boolean containsKey( final String key ) {
        return map.containsKey( key );
    }

    @Override
    public boolean containsValue( final Object value ) {
        if ( ( value == null ) || ( value instanceof String ) ) {
            return containsValue( ( String ) value );
        } else if ( value instanceof Number ) {
            return containsValue( ( Number ) value );
        } else if ( value instanceof Boolean ) {
            return containsValue( ( Boolean ) value );
        } else {
            return containsValue( ( JsonValue ) value );
        }
    }

    @Override
    public boolean containsValue( final Boolean value ) {
        return map.containsValue( toJsonBoolean( value ) );
    }

    @Override
    public boolean containsValue( final Number value ) {
        return map.containsValue( toJsonNumber( value ) );
    }

    @Override
    public boolean containsValue( final String value ) {
        return map.containsValue( toJsonString( value ) );
    }

    @Override
    public boolean containsValue( final JsonValue value ) {
        return map.containsValue( value );
    }

    @Override
    public Collection< JsonValue > values() {
        return userView.values();
    }

    @Override
    public Set< Entry< String, JsonValue >> entrySet() {
        return userView.entrySet();
    }

    @Override
    public Set< String > keySet() {
        return userView.keySet();
    }

    @Override
    public JsonValue get( final String key ) {
        return map.get( key );
    }

    @Override
    public JsonValue get( final Object key ) {
        return get( ( String ) key );
    }

    @Override
    public JsonValue remove( final String key ) {
        return map.remove( key );
    }

    @Override
    public JsonValue remove( final Object key ) {
        return remove( ( String ) key );
    }

    @Override
    public void putAll( final Map< ? extends String, ? extends JsonValue > jsonObject ) {
        map.putAll( jsonObject );
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonObject ) ) return false;
        final JsonObject a = ( JsonObject ) o;
        return map.equals( a.map );
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public JsonObject clone() {
        final JsonObject retVal = new JsonObject();
        JsonValue jsonValue = null;
        for ( final String jsonKey : map.keySet() ) {
            jsonValue = map.get( jsonKey );
            retVal.put( jsonKey, jsonValue != null ? jsonValue.clone() : null );
        }
        return retVal;
    }

    @Override
    protected void writeTo( final JsonWriter jsonWriter ) throws IOException, JsonException {
        jsonWriter.writeObjectStart();
        JsonValue jsonValue = null;
        for ( final String jsonKey : map.keySet() ) {
            jsonWriter.writeString( jsonKey );
            jsonValue = map.get( jsonKey );
            if ( jsonValue == null ) {
                jsonWriter.writeNull();
            } else if ( jsonValue instanceof JsonBoolean ) {
                jsonWriter.writeBoolean( ( ( JsonBoolean ) jsonValue ).getBoolean() );
            } else if ( jsonValue instanceof JsonNumber ) {
                jsonWriter.writeNumber( ( ( JsonNumber ) jsonValue ).toString() );
            } else if ( jsonValue instanceof JsonString ) {
                jsonWriter.writeString( ( ( JsonString ) jsonValue ).getString() );
            } else if ( jsonValue instanceof JsonStructure ) {
                ( ( JsonStructure ) jsonValue ).writeTo( jsonWriter );
            } else {
                throw new IllegalStateException();
            }
        }
        jsonWriter.writeObjectEnd();
        jsonWriter.flush();
    }

    JsonValue putInternal( final String key, final JsonValue value ) {
        if ( key == null ) {
            throw new NullPointerException( "JSON key cannot be null" );
        }
        return map.put( key, value );
    }
}
