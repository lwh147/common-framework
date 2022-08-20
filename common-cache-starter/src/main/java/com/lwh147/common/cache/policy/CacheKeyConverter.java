package com.lwh147.common.cache.policy;

import com.lwh147.common.core.exception.CommonException;
import com.lwh147.common.core.exception.CommonExceptionEnum;

import java.util.function.Function;

/**
 * 自定义JetCache缓存key转换器
 * <p>
 * 将key转换为json的方式效率低，可读性差
 * <p>
 * 本方法采用与 {@link Object#toString()} 方法相似的逻辑生成key，
 * 不同的是：
 * <p>
 * 不显示类名
 * <p>
 * 当key的类型为 {@link String}、{@link Long}、{@link Integer} 时直接作为
 * key；其它情况下值为对象的16进制哈希码
 *
 * @author lwh
 * @date 2021/12/7 9:59
 **/
public class CacheKeyConverter implements Function<Object, Object> {
    public static final CacheKeyConverter INSTANCE = new CacheKeyConverter();

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("缓存key为null");
        }
        if (originalKey instanceof String || originalKey instanceof Long || originalKey instanceof Integer) {
            return "@" + originalKey.toString();
        }
        return "@" + Integer.toHexString(originalKey.hashCode());
    }
}
