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
package org.fossnova.json;

/**
 * JSON string.
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 * @see JsonValue
 * @see JsonValueFactory
 */
public interface JsonString extends JsonValue {

    /**
     * Sets new string value.
     * @param value new string value
     */
    void setString( String value );

    /**
     * Gets string value.
     * @return string value
     */
    String getString();

    /**
     * Clones this JSON string.
     * @return new JSON string clone.
     */
    @Override
    JsonString clone();
}
