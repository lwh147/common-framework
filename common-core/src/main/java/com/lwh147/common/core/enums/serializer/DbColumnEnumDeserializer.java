package com.lwh147.common.core.enums.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.lwh147.common.core.enums.DbColumnEnum;

import java.io.IOException;

/**
 * 实现了 {@link DbColumnEnum} 接口的枚举类反序列化策略，基于 Jackson
 *
 * @author lwh
 * @date 2024/03/21 13:56
 **/
@SuppressWarnings("unchecked")
public class DbColumnEnumDeserializer<T extends Enum<T> & DbColumnEnum> extends JsonDeserializer<T>
        implements ContextualDeserializer {

    private Class<T> type;

    public DbColumnEnumDeserializer<T> withType(Class<T> type) {
        this.type = type;
        return this;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return DbColumnEnum.from(type, jsonParser.getText());
    }

    @Override
    public JsonDeserializer<T> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) {
        final Class<?> targetClass = deserializationContext.getContextualType() != null
                ? deserializationContext.getContextualType().getRawClass()
                : beanProperty.getMember().getType().getRawClass();
        return this.withType((Class<T>) targetClass);
    }
}