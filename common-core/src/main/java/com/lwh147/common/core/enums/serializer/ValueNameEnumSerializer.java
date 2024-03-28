package com.lwh147.common.core.enums.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lwh147.common.core.enums.ValueNameEnum;

import java.io.IOException;
import java.io.Serializable;

/**
 * 实现了 {@link ValueNameEnum} 接口的枚举类序列化策略，基于 Jackson
 *
 * @author lwh
 * @date 2024/03/21 13:56
 **/
public class ValueNameEnumSerializer<T extends Enum<T> & ValueNameEnum<? extends Serializable>> extends JsonSerializer<T> {

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (t.getValue() == null) {
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeString(t.getValue().toString());
    }

}