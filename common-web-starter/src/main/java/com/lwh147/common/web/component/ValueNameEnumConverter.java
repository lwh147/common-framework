package com.lwh147.common.web.component;

import com.lwh147.common.core.enums.ValueNameEnum;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Set;

/**
 * 自定义实现了 {@link ValueNameEnum} 接口的枚举类型 Spring MVC 参数绑定策略
 *
 * @author lwh
 * @date 2024/04/08 17:41
 **/
@SuppressWarnings("unchecked")
public class ValueNameEnumConverter<T extends Enum<T> & ValueNameEnum<? extends Serializable>> implements ConditionalGenericConverter {
    /**
     * 判断是否匹配的参数类型
     **/
    @Override
    public boolean matches(@Nonnull TypeDescriptor sourceTypeDescriptor, TypeDescriptor targetTypeDescriptor) {
        Class<?> targetType = targetTypeDescriptor.getType();
        if (targetType.isEnum() && sourceTypeDescriptor.getSource() instanceof Serializable) {
            for (Class<?> anInterface : targetType.getInterfaces()) {
                if (ValueNameEnum.class.equals(anInterface)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 转换为目标类型
     **/
    @Override
    public Object convert(Object o, @Nonnull TypeDescriptor sourceTypeDescriptor, @Nonnull TypeDescriptor targetTypeDescriptor) {
        if (o == null) {
            return null;
        }
        Class<?> targetType = targetTypeDescriptor.getObjectType();
        if (targetType.isEnum() && o instanceof Serializable) {
            for (Class<?> anInterface : targetType.getInterfaces()) {
                if (ValueNameEnum.class.equals(anInterface)) {
                    return ValueNameEnum.from((Class<T>) targetType, o.toString());
                }
            }
        }
        throw new IllegalStateException("Can not convert to type " + targetType.getCanonicalName() + " from value "
                + o + ": type mismatch");
    }

    /**
     * 由于需要根据接口进行类型匹配，所以无法确定并提供具体的实现类型
     **/
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

}
