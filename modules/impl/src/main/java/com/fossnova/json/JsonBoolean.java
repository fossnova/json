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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonBoolean implements org.fossnova.json.JsonBoolean {

    private static final long serialVersionUID = 1L;

    private boolean value;

    JsonBoolean( final boolean value ) {
        this.value = value;
    }

    public void setBoolean( final boolean value ) {
        this.value = value;
    }

    public boolean getBoolean() {
        return value;
    }

    @Override
    public int hashCode() {
        return value ? TRUE.hashCode() : FALSE.hashCode();
    }

    @Override
    public boolean equals( final Object o ) {
        if ( o == this ) return true;
        if ( !( o instanceof JsonBoolean ) ) return false;
        final JsonBoolean b = ( JsonBoolean ) o;
        return b.value == value;
    }

    @Override
    public String toString() {
        return value ? TRUE.toString() : FALSE.toString();
    }

    @Override
    public JsonBoolean clone() {
        return new JsonBoolean( value );
    }
}
