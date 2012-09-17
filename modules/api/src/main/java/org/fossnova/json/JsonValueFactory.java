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

import org.fossnova.finder.FactoryFinder;
import org.fossnova.json.stream.JsonReader;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public abstract class JsonValueFactory {

    protected JsonValueFactory() {
    }

    public static JsonValueFactory newInstance() {
        final JsonValueFactory jsonFactoryImpl = FactoryFinder.find( JsonValueFactory.class );
        if ( jsonFactoryImpl != null ) {
            return jsonFactoryImpl;
        }
        throw new IllegalStateException( "Factory not configured: " + JsonValueFactory.class.getName() );
    }

    public abstract JsonObject newJsonObject();

    public abstract JsonArray newJsonArray();

    public abstract JsonBoolean newJsonBoolean( Boolean value );

    public abstract JsonNumber newJsonNumber( Number value );

    public abstract JsonString newJsonString( String value );

    public abstract JsonValue readFrom( JsonReader reader ) throws IOException;
}
