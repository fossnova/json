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

import org.fossnova.json.JsonBuilder;
import org.fossnova.json.JsonWriter;

/**
 * @author <a href="mailto:opalka dot richard at gmail dot com">Richard Opalka</a>
 */
final class JsonBuilderImpl implements JsonBuilder {

    private final JsonWriter delegate;

    JsonBuilderImpl( final JsonWriter delegate ) {
        this.delegate = delegate;
    }

    public JsonBuilder writeObjectStart() throws IOException {
        delegate.writeObjectStart();
        return this;
    }

    public JsonBuilder writeObjectEnd() throws IOException {
        delegate.writeObjectEnd();
        return this;
    }

    public JsonBuilder writeArrayStart() throws IOException {
        delegate.writeArrayStart();
        return this;
    }

    public JsonBuilder writeArrayEnd() throws IOException {
        delegate.writeArrayEnd();
        return this;
    }

    public JsonBuilder writeNull() throws IOException {
        delegate.writeNull();
        return this;
    }

    public JsonBuilder writeString( final String data ) throws IOException {
        delegate.writeString( data );
        return this;
    }

    public JsonBuilder writeBoolean( final boolean data ) throws IOException {
        delegate.writeBoolean( data );
        return this;
    }

    public JsonBuilder writeByte( final byte data ) throws IOException {
        delegate.writeByte( data );
        return this;
    }

    public JsonBuilder writeShort( final short data ) throws IOException {
        delegate.writeShort( data );
        return this;
    }

    public JsonBuilder writeInt( final int data ) throws IOException {
        delegate.writeInt( data );
        return this;
    }

    public JsonBuilder writeLong( final long data ) throws IOException {
        delegate.writeLong( data );
        return this;
    }

    public JsonBuilder writeFloat( final float data ) throws IOException {
        delegate.writeFloat( data );
        return this;
    }

    public JsonBuilder writeDouble( final double data ) throws IOException {
        delegate.writeDouble( data );
        return this;
    }
}
