package com.lwh147.common.util;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * SnowflakeIdUtil
 *
 * @author lwh
 * @date 2021/12/15 16:26
 **/
public class SnowflakeIdUtil {
    /**
     * 合法的id校验正则表达式
     **/
    private static final Pattern SNOWFLAKE_ID_PATTERN = Pattern.compile("^[1-9]\\d{18}$");

    /**
     * 检验Id是否合法
     *
     * @param id 类型为String，为null判断为非法
     **/
    public static boolean isLegal(String id) {
        if (Objects.isNull(id)) {
            return false;
        }
        return id.matches(SNOWFLAKE_ID_PATTERN.pattern());
    }

    /**
     * 检验Id是否合法
     *
     * @param id 类型为Long，为null判断为非法
     **/
    public static boolean isLegal(Long id) {
        if (Objects.isNull(id)) {
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

    private SnowflakeIdUtil() {
    }
}
