package com.lwh147.common.core.enums.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.lwh147.common.core.enums.ValueNameEnum;

import java.io.IOException;
import java.io.Serializable;

/**
 * 实现了 {@link ValueNameEnum} 接口的枚举类反序列化策略，基于 Jackson
 * <p>
 * {@link JsonDeserializer}只被初始化（上下文化—）一次且只在被初始化的那一刻接收完整的类型和其他信息。所以为了获得这些信息，
 * 反序列化器需要实现 {@link ContextualDeserializer#createContextual}，这个方法被调用来初始化一个反序列化器示例，并且可以访
 * 问 BeanProperty，它也提供了完整的 JavaType
 *
 * @author lwh
 * @date 2024/03/21 13:56
 * @see <a href="https://www.saoniuhuo.com/question/detail-2178046.html">获取Jackson的JsonDeserializer中检测到的泛型类型</a>
 **/
@SuppressWarnings("unchecked")
public class ValueNameEnumDeserializer<T extends Enum<T> & ValueNameEnum<? extends Serializable>> extends JsonDeserializer<T>
        implements ContextualDeserializer {
    /**
     * 记录目标对象的实际类型，便于反序列化
     **/
    private Class<T> type;

    public ValueNameEnumDeserializer<T> withType(Class<T> type) {
        this.type = type;
        return this;
    }

    /**
     * @param deserializationContext 调用此方法时，该参数已经没有要反序列化的对象的实际类型等信息了
     **/
    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return ValueNameEnum.from(type, jsonParser.getText());
    }

    /**
     * 获取Jackson检测到的目标对象类型，并根据类型创建提供反序列化器
     **/
    @Override
    public JsonDeserializer<T> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) {
        final Class<?> targetClass = deserializationContext.getContextualType() != null
                ? deserializationContext.getContextualType().getRawClass()
                : beanProperty.getMember().getType().getRawClass();
        return this.withType((Class<T>) targetClass);
    }
}