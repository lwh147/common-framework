package com.lwh147.common.cache.policy;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 自定义缓存key转换器
 * <p>
 * 正常情况下，缓存key应该为可读性较强的数值或字符串或其他基础数据类型，但如果一个Bean被当作key时，JetCache会默认将其转换为字
 * 节码，这样的key会极大影响可读性
 * <p>
 * 本类基于 JetCache 内置的默认缓存key转换策略定义了一个缓存key转换策略（
 * 见 {@link com.alicp.jetcache.external.ExternalKeyUtil#buildKeyAfterConvert}），区别是：
 * <p>
 * 1.优化了展示规则
 * <p>
 * 2.当key为其它类型时，采用 {@link Object#toString()} 类似规则转换为字符串
 * <p>
 * 此外，该转换策略也不允许使用 {@code null} 对象作为key（虽然可以序列化为字符串"null"）。Redis自身允许key为 {@code null}，
 * 但是实际使用过程中允许这么做没有什么好处，反而还可能会产生问题，因为一旦允许，根据墨菲定律必定会出现 {@code "null"} 的key，
 * 这样的key缓存内容会互相覆盖，且对应的使用场景也无法确定，获取到之后进行处理时可能会导致未知的异常
 *
 * @author lwh
 * @date 2021/12/7 9:59
 **/
public final class CacheKeyConverter {
    private CacheKeyConverter() {
    }

    public static String convert(Object key) {
        if (key == null) {
            throw new NullPointerException("cache key can't be null");
        }
        if (key instanceof String) {
            return (String) key;
        } else if (key instanceof byte[] || key instanceof char[] || key instanceof Character || key instanceof CharSequence) {
            return key.toString();
        } else if (key instanceof Number) {
            return key.getClass().getSimpleName() + "@" + key;
        } else if (key instanceof Date) {
            return key.getClass().getSimpleName() + "@" + DateUtil.format((Date) key, DatePattern.PURE_DATETIME_MS_FORMATTER);
        } else if (key instanceof Boolean) {
            return key.toString();
        } else {
            return key.getClass().getName() + "@" + Integer.toHexString(key.hashCode());
        }
    }
}