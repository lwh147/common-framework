package com.lwh147.common.cache.policy;

import java.util.function.Function;

/**
 * 自定义缓存key转换器
 * <p>
 * 将key转换为json的方式效率低，可读性差
 * <p>
 * 本方法采用与 {@link Object#toString()} 方法相似的逻辑生成key，
 * 不同的是：
 * <p>
 * 采用简化类名
 * <p>
 * 当key的类型为 {@link String}、{@link Long}、{@link Integer} 时直接取值
 * 与 '@' 拼接；其它情况下 '@' 后的值为对象的16进制哈希码
 *
 * @author lwh
 * @date 2021/12/7 9:59
 **/
public class CacheKeyConverter implements Function<Object, Object> {
    public static final CacheKeyConverter INSTANCE = new CacheKeyConverter();

    @Override
    public Object apply(Object o) {
        return o.getClass().getSimpleName() + "@"
                + ((o instanceof String
                || o instanceof Long
                || o instanceof Integer) ? o : Integer.toHexString(o.hashCode()));
    }
}
