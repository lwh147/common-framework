package com.lwh147.common.core.enums.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lwh147.common.core.enums.ValueNameEnum;

import java.io.IOException;

/**
 * {@link ValueNameEnum} 的实现枚举类序列化策略，基于 Jackson
 *
 * @author lwh
 * @date 2024/03/21 13:56
 **/
public class ValueNameEnumSerializer<T extends Enum<T> & ValueNameEnum> extends JsonSerializer<T> {

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(t.getValue());
    }

}