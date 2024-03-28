package com.lwh147.common.core.enums.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.lwh147.common.core.enums.DbColumnEnum;
import com.lwh147.common.core.enums.ValueNameEnum;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通过继承 {@link BeanDeserializerModifier} 方式向 Jackson 提供针对实现了 {@link DbColumnEnum} 或 {@link ValueNameEnum}
 * 接口的枚举类型反序列化策略
 * <p>
 * 除了根据官方 JavaDoc 所说的通过实现 {@link Deserializers} / {@link Serializers} 接口提供自定义的泛型类型的（反）序列化
 * 策略外（见 {@link EnumDeserializer}），也可以通过这种方式去实现
 * <p>
 * 该方法在上述方法之后调用，优先级更高
 *
 * @author lwh
 * @date 2024/03/27 13:54
 **/
public class EnumDeserializerModifier extends BeanDeserializerModifier {
    /**
     * 对已经获取到的枚举类型反序列化器进行修改，根据是否是枚举类型、实现 {@link DbColumnEnum} 或 {@link ValueNameEnum} 接口
     * 与否确定反序列化策略
     *
     * @param type 目标类型
     **/
    @Override
    public JsonDeserializer<?> modifyEnumDeserializer(DeserializationConfig config, JavaType type, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        Class<?> rawClass = type.getRawClass();
        if (rawClass.getSuperclass() != null && rawClass.getSuperclass().equals(Enum.class)) {
            Set<Class<?>> interfaceSet = Arrays.stream(rawClass.getInterfaces()).collect(Collectors.toSet());
            if (interfaceSet.contains(ValueNameEnum.class)) {
                return new ValueNameEnumDeserializer<>();
            } else if (interfaceSet.contains(DbColumnEnum.class)) {
                return new DbColumnEnumDeserializer<>();
            }
        }
        return super.modifyEnumDeserializer(config, type, beanDesc, deserializer);
    }
}