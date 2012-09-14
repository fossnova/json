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
import java.util.List;

import org.fossnova.json.stream.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public interface JsonArray extends JsonValue, List< JsonValue > {

    boolean add( final String value );

    boolean add( final Number value );

    boolean add( final Boolean value );

    boolean add( final JsonValue value );

    void add( final int index, final String value );

    void add( final int index, final Number value );

    void add( final int index, final Boolean value );

    void add( final int index, final JsonValue value );

    boolean contains( final String value );

    boolean contains( final Number value );

    boolean contains( final Boolean value );

    boolean contains( final JsonValue value );
    
    boolean contains( final Object value );
    
    int indexOf( final String value );

    int indexOf( final Number value );

    int indexOf( final Boolean value );

    int indexOf( final JsonValue value );

    int lastIndexOf( final String value );

    int lastIndexOf( final Number value );

    int lastIndexOf( final Boolean value );

    int lastIndexOf( final JsonValue value );

    boolean remove( final String value );

    boolean remove( final Number value );

    boolean remove( final Boolean value );

    boolean remove( final JsonValue value );

    JsonValue set( final int index, final String value );

    JsonValue set( final int index, final Number value );

    JsonValue set( final int index, final Boolean value );

    JsonValue set( final int index, final JsonValue value );

    public JsonArray subList( final int fromIndex, final int toIndex );

    JsonValue[] toArray();

    void writeTo( JsonWriter writer ) throws IOException;

}
