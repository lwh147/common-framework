package com.lwh147.common.core.enums.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.lwh147.common.core.enums.DbColumnEnum;
import com.lwh147.common.core.enums.ValueNameEnum;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通过继承 {@link SimpleDeserializers} 实现 {@link Deserializers} 接口的方式向 Jackson 提供针对实现了 {@link DbColumnEnum}
 * 或 {@link ValueNameEnum} 接口的枚举类型反序列化策略
 * <p>
 * {@link com.fasterxml.jackson.databind.module.SimpleModule} 的 JavaDoc中指出，拥有泛型类型的（反）序列化策略类需要通过实
 * 现反序列化程序和/或序列化程序回调（也就是 {@link Deserializers} / {@link Serializers} ），以匹配全类型签名（使用JavaType）
 * <p>
 * 这是推荐的针对泛型类型的自定义（反）序列化策略实现方式，除此之外还有 {@link EnumDeserializerModifier} 这种实现方式
 *
 * @author lwh
 * @date 2024/03/28 10:24
 **/
public class EnumDeserializer extends SimpleDeserializers {
    /**
     * 如果按原方法 {@link SimpleDeserializers#findEnumDeserializer} 没有找到反序列化器，则根据是否是枚举类型、实现
     * {@link DbColumnEnum} 或 {@link ValueNameEnum} 接口与否确定反序列化策略
     *
     * @param type 目标类型
     **/
    @Override
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        JsonDeserializer<?> deser = super.findEnumDeserializer(type, config, beanDesc);
        // 自定义判断逻辑
        if (deser == null && type.isEnum()) {
            Set<Class<?>> interfaceSet = Arrays.stream(type.getInterfaces()).collect(Collectors.toSet());
            if (interfaceSet.contains(ValueNameEnum.class)) {
                deser = new ValueNameEnumDeserializer<>();
            } else if (interfaceSet.contains(DbColumnEnum.class)) {
                deser = new DbColumnEnumDeserializer<>();
            }
        }
        return deser;
    }
}
