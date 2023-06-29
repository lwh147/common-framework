package com.lwh147.common.cache.policy;

import com.lwh147.common.core.exception.CommonExceptionEnum;

import java.util.Arrays;
import java.util.function.Function;

/**
 * 自定义JetCache缓存key转换器
 * <p>
 * 正常情况下，key应该为可读性较强的数值或字符串类型，但如果一个Bean被当作key时默认会调用其 {@code toString()} 方法作为key（
 * 实际项目中真有这么干的），这样的key会极大影响可读性
 * <p>
 * 本方法采用与 {@link Object#toString()} 方法相似的逻辑生成key：
 * <p>
 * 当key的类型为Java原始类型对应的包装类型时使用 {@code toString()} 结果作为key，其它情况下取 对象的类名 + 16进制哈希码
 * <p>
 * 此外，虽然Redis允许key为 {@code null}，但是本框架不允许，这种情况本身就应该是异常情况，如果你已经知道对象可能是 {@code
 * null} 的情况下还会用这个对象做key吗？再者，就算允许key为 {@code null} 也没有意义，因为一旦允许则根据墨菲定律必定会出现各种
 * 意外情况造成key为 {@code null} 的缓存内容互相覆盖，这样一来缓存对应的业务场景也无法确定，获取到之后进行处理时可能会导致发生
 * 异常
 *
 * @author lwh
 * @date 2021/12/7 9:59
 **/
public class CacheKeyConverter implements Function<Object, Object> {
    public static final CacheKeyConverter INSTANCE = new CacheKeyConverter();
    public static final Class<?>[] ClASS_CAN_BE_KEY = {String.class, Short.class, Integer.class, Long.class, Boolean.class, Byte.class, Character.class, Float.class, Double.class};

    private CacheKeyConverter() {
    }

    @Override
    public Object apply(Object originalKey) {
        if (originalKey == null) {
            throw CommonExceptionEnum.COMMON_ERROR.toException("缓存key为null");
        }
        if (this.canBeKey(originalKey)) {
            return '@' + originalKey.toString();
        }
        return originalKey.getClass().getName() + "@" + Integer.toHexString(originalKey.hashCode());
    }

    private boolean canBeKey(Object obj) {
        return Arrays.stream(ClASS_CAN_BE_KEY).anyMatch(clazz -> clazz.equals(obj.getClass()));
    }
}