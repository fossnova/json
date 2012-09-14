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

import java.util.Map;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public interface JsonObject extends JsonStructure, Map< String, JsonValue > {

    boolean containsKey( final String key );

    boolean containsValue( final String value );

    boolean containsValue( final Boolean value );

    boolean containsValue( final Number value );

    boolean containsValue( final JsonValue value );

    JsonValue get( final String key );

    JsonValue put( final String key, final String value );

    JsonValue put( final String key, final Boolean value );

    JsonValue put( final String key, final Number value );

    JsonValue remove( final String key );

}
