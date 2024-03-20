package com.lwh147.common.util.constant;

import java.util.regex.Pattern;

/**
 * 预编译正则表达式常量类
 *
 * @author lwh
 * @date 2021/11/17 16:21
 **/
public class RegExpConstant {
    /**
     * 匹配回车或换行或制表符
     **/
    public static final Pattern ENTER_PATTERN = Pattern.compile("[\r\n\t]");

    /**
     * 6-16位字符串，允许出现字母、数字、下划线，可用作用户名
     **/
    public static final Pattern USERNAME_PATTERN = Pattern.compile("^\\w{6,16}$");
    /**
     * 6-16位字符串，允许出现数字、字母、下划线或 ~ ` ! @ # $ % ^ & * + - . /，可用作密码
     **/
    public static final Pattern SIX_TO_SIXTEEN_DIGITS_PATTERN = Pattern.compile("^[\\w~`!@#$%^&*+\\-./]{6,16}$");
    /**
     * 6位纯数字，可用作支付密码
     **/
    public static final Pattern SIX_DIGITS_NUMBER_PATTERN = Pattern.compile("^\\d{6}$");

    /**
     * 大于等于0的整数，允许出现前导0，可用作物品数量
     **/
    public static final Pattern INTEGER_BE_ZERO_PATTERN = Pattern.compile("^\\d+$");
    /**
     * 大于等于0的数，包含小数，允许出现前导0，可用作金额
     **/
    public static final Pattern INTEGER_FLOAT_BE_ZERO_PATTERN = Pattern.compile("^\\d+(\\.?\\d+)?$");
    /**
     * 合法的雪花算法ID校验正则表达式
     **/
    public static final Pattern SNOWFLAKE_ID_PATTERN = Pattern.compile("^[1-9]\\d{18}$");

    /**
     * 邮件地址
     **/
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    /**
     * URL
     **/
    public static final Pattern URL_PATTERN = Pattern.compile("^https?://(([a-zA-Z0-9_-])+(\\.)?)*(:\\d+)?(/((\\.)?(\\?)?=?&?[a-zA-Z0-9_-](\\?)?)*)*$");
    /**
     * 手机号
     **/
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$");
    /**
     * 身份证号
     **/
    public static final Pattern ID_NUMBER_PATTERN = Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0-2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0-2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");

    private RegExpConstant() {}
}