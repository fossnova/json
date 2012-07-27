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

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonConstants {

    static final int OBJECT_START = '{';

    static final int OBJECT_END = '}';

    static final int ARRAY_START = '[';

    static final int ARRAY_END = ']';

    static final int COLON = ':';

    static final int COMMA = ',';

    static final int MINUS = '-';

    static final int QUOTE = '\"';

    static final int BACKSLASH = '\\';

    static final String STRING = "STRING";

    static final String NUMBER = "NUMBER";

    static final String NULL = "null";

    static final String TRUE = "true";

    static final String FALSE = "false";

    private JsonConstants() {
        // forbidden instantiation
    }
}
