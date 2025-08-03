package com.modulix.framework.web.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LongToStringSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (id != null) {
            // 长度小于某个值的，还是保持long类型  
            if (id < Integer.MAX_VALUE) {
                jsonGenerator.writeNumber(id);
            } else {
                // 长度超过某个值的，转化为字符串  
                jsonGenerator.writeString(id.toString());
            }
        }
    }
}