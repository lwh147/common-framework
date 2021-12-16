package com.lwh147.common.cache.policy;

import java.util.function.Function;

/**
 * 自定义JetCache缓存value解码策略
 *
 * @author lwh
 * @date 2021/12/7 9:33
 **/
public class CacheValueDecoder implements Function<byte[], Object> {
    public static final CacheValueDecoder INSTANCE = new CacheValueDecoder();

    @Override
    public Object apply(byte[] bytes) {
        return JacksonSerializer.deserialize(bytes);
    }
}
