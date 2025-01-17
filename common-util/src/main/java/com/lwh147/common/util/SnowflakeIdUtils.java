package com.lwh147.common.util;

import cn.hutool.core.lang.Snowflake;
import com.lwh147.common.util.constant.RegExpConstant;

/**
 * 雪花算法工具类
 *
 * @author lwh
 * @date 2021/12/15 16:26
 **/
public final class SnowflakeIdUtils {
    /**
     * 随便生成一个默认的雪花算法对象
     **/
    private static final Snowflake SNOWFLAKE = new Snowflake(0L, 0L);

    private SnowflakeIdUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取一个雪花算法节点，不保证不会重复
     **/
    public static long nextId() {
        return SNOWFLAKE.nextId();
    }

    /**
     * 检验Id是否合法
     *
     * @param id 类型为String，为null判断为非法
     **/
    public static boolean isLegal(String id) {
        if (Strings.isBlank(id)) {
            return false;
        }
        return id.matches(RegExpConstant.SNOWFLAKE_ID_PATTERN.pattern());
    }

    /**
     * 检验Id是否合法
     *
     * @param id 类型为Long，为null判断为非法
     **/
    public static boolean isLegal(Long id) {
        if (id == null) {
            return false;
        }
        return isLegal(id.toString());
    }

    /**
     * 检验Id是否合法
     *
     * @param id 类型为long
     **/
    public static boolean isLegal(long id) {
        String id1 = String.valueOf(id);
        return isLegal(id1);
    }

    /**
     * 检验Id是否非法
     **/
    public static boolean isUnLegal(String id) {
        return !isLegal(id);
    }

    public static boolean isUnLegal(Long id) {
        return !isLegal(id);
    }

    public static boolean isUnLegal(long id) {
        return !isLegal(id);
    }
}