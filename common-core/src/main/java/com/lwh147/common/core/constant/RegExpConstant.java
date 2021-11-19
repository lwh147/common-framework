package com.lwh147.common.core.constant;

import java.util.regex.Pattern;

/**
 * 预编译正则表达式常量类
 *
 * @author lwh
 * @date 2021/11/17 16:21
 **/
public class RegExpConstant {
    /**
     * 匹配回车、换行、制表符
     **/
    public static final Pattern ENTER_PATTERN = Pattern.compile("[\r\n\t]");

    private RegExpConstant() {
    }
}
