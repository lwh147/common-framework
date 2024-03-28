package com.lwh147.common.core.enums.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.lwh147.common.core.enums.DbColumnEnum;
import com.lwh147.common.core.enums.ValueNameEnum;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通过继承 {@link BeanSerializerModifier} 方式向 Jackson 提供针对实现了 {@link DbColumnEnum} 或 {@link ValueNameEnum}
 * 接口的枚举类型序列化策略
 *
 * @author lwh
 * @date 2024/03/27 13:54
 **/
public class EnumSerializerModifier extends BeanSerializerModifier {
    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        Class<?> rawClass = valueType.getRawClass();
        if (rawClass.getSuperclass() != null && rawClass.getSuperclass().equals(Enum.class)) {
            Set<Class<?>> interfaceSet = Arrays.stream(rawClass.getInterfaces()).collect(Collectors.toSet());
            if (interfaceSet.contains(ValueNameEnum.class)) {
                return new ValueNameEnumSerializer<>();
            } else if (interfaceSet.contains(DbColumnEnum.class)) {
                return new DbColumnEnumSerializer<>();
            }
        }
        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }
}