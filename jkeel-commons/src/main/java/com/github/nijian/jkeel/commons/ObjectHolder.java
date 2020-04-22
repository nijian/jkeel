package com.github.nijian.jkeel.commons;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * ObjectHolder provides a list of object which has time consuming construction.
 *
 * @author nj
 * @since 0.0.2
 */
public final class ObjectHolder {

    /**
     * Jackson ObjectMapper
     */
    public final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Jackson ObjectMapper
     */
    public final static ObjectMapper mapMapper = new ObjectMapper().registerModule(new SimpleModule().addSerializer(JsonString.class, new JsonStringSerializer()));

    public static class JsonStringSerializer extends StdSerializer<JsonString> {

        public JsonStringSerializer() {
            this(null);
        }

        public JsonStringSerializer(Class<JsonString> t) {
            super(t);
        }

        @Override
        public void serialize(
                JsonString value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {
            jgen.writeRawValue(value.toString());
        }
    }
}
