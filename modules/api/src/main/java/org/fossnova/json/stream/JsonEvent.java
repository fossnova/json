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
package org.fossnova.json.stream;

/**
 * JSON encoding parsing events.
 * 
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public enum JsonEvent {
    /**
     * Parsing cursor points to JSON <CODE>true</CODE> or <CODE>false</CODE> token.
     */
    BOOLEAN,
    /**
     * Parsing cursor points to JSON <CODE>number</CODE>.
     */
    NUMBER,
    /**
     * Parsing cursor points to JSON <CODE>null</CODE> token.
     */
    NULL,
    /**
     * Parsing cursor points to JSON <CODE>string</CODE>.
     */
    STRING,
    /**
     * Parsing cursor points to JSON <CODE>object start</CODE> token.
     */
    OBJECT_START,
    /**
     * Parsing cursor points to JSON <CODE>object end</CODE> token.
     */
    OBJECT_END,
    /**
     * Parsing cursor points to JSON <CODE>array start</CODE> token.
     */
    ARRAY_START,
    /**
     * Parsing cursor points to JSON <CODE>array end</CODE> token.
     */
    ARRAY_END,
}
