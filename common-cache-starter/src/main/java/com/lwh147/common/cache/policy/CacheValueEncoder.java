package com.lwh147.common.cache.policy;

import java.util.function.Function;

/**
 * 自定义JetCache缓存value编码策略
 *
 * @author lwh
 * @date 2021/11/29 14:31
 **/
public class CacheValueEncoder implements Function<Object, byte[]> {
    public static final CacheValueEncoder INSTANCE = new CacheValueEncoder();

    @Override
    public byte[] apply(Object o) {
        return JacksonSerializer.serialize(o);
    }
}
