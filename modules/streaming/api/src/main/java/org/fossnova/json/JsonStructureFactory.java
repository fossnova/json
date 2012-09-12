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
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;

import org.fossnova.finder.FactoryFinder;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
public abstract class JsonStructureFactory {

    protected JsonStructureFactory() {
    }

    public static JsonStructureFactory newInstance() {
        final JsonStructureFactory jsonFactoryImpl = FactoryFinder.find( JsonStructureFactory.class );
        if ( jsonFactoryImpl != null ) {
            return jsonFactoryImpl;
        }
        throw new IllegalStateException( "Factory not configured: " + JsonStructureFactory.class.getName() );
    }

    public abstract JsonObject newJsonObject();

    public abstract JsonObject newJsonObject( Comparator< String > keyComparator );

    public abstract JsonArray newJsonArray();
    
    public abstract JsonStructure readFrom( Reader reader ) throws IOException;
    
    public abstract JsonStructure readFrom( InputStream stream ) throws IOException;
    
    public abstract JsonStructure readFrom( InputStream stream, String charsetName ) throws UnsupportedEncodingException, IOException;

}
