package com.lwh147.common.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储用户请求上下文信息
 *
 * @author lwh
 * @date 2021/11/18 17:40
 **/
public final class ContextHolder {
    /**
     * 线程本地变量，该线程独享这份请求上下文
     * <p>
     * 使用 {@link ThreadLocal} 避免多线程问题
     **/
    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL_MAP = new ThreadLocal<>();

    /**
     * 不能实例化
     **/
    private ContextHolder() {}

    /**
     * 获取整个Map
     *
     * @return 存储信息的Map
     **/
    public static Map<String, String> getMap() {
        Map<String, String> map = THREAD_LOCAL_MAP.get();
        if (map == null) {
            map = new HashMap<>(16);
            THREAD_LOCAL_MAP.set(map);
        }
        return map;
    }

    /**
     * 设置上下文信息
     *
     * @param key   键
     * @param value 值
     **/
    public static void set(String key, String value) {
        Map<String, String> map = getMap();
        map.put(key, value);
    }

    /**
     * 获取上下文信息
     *
     * @param key 键
     * @return 值，不存在时返回null
     **/
    public static String get(String key) {
        Map<String, String> map = getMap();
        return map.getOrDefault(key, null);
    }

    /**
     * 删除key
     *
     * @param key 要删除的key
     **/
    public static void remove(String key) {
        Map<String, String> map = getMap();
        map.remove(key);
    }

    /**
     * 删除Map
     **/
    public static void remove() {
        THREAD_LOCAL_MAP.remove();
    }
}