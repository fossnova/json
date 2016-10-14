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

/**
 * JSON boolean.
 * @author <a href="mailto:opalka.richard@gmail.com">Richard Opalka</a>
 * @see JsonValue
 * @see JsonValueFactory
 */
public interface JsonBoolean extends JsonValue {

    /**
     * Sets new boolean value.
     * @param value new boolean value
     */
    void setBoolean( boolean value );

    /**
     * Gets boolean value.
     * @return boolean value
     */
    boolean getBoolean();

    /**
     * Clones this JSON boolean.
     * @return new JSON boolean clone.
     */
    @Override
    JsonBoolean clone();
}
