package com.lwh147.common.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 单独存储用户请求上下文信息，方便日志记录
 * <p>
 * 使用 {@link ThreadLocal} 避免多线程问题
 *
 * @author lwh
 * @date 2021/11/18 17:40
 **/
public class ContextHolder {
    /**
     * 线程本地变量，存储该线程处理的请求相关信息
     **/
    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL_MAP = new ThreadLocal<>();

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
     * 删除Map
     **/
    public static void remove() {
        THREAD_LOCAL_MAP.remove();
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
     * 不能实例化
     **/
    private ContextHolder() {
    }
}
