package com.lwh147.common.util.constant;

/**
 * 日期时间常量类
 *
 * @author lwh
 * @date 2021/11/26 11:00
 **/
public class DateTimeConstant {
    public static final String DEFAULT_TIMEZONE = "GMT+8";

    public static final String DEFAULT_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_MONTH_PATTERN = "yyyy-MM";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    public static final String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";
    public static final String PURE_MONTH_PATTERN = "yyyyMM";
    public static final String PURE_TIME_PATTERN = "HHmmss";

    public static final String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String CHINESE_MONTH_PATTERN = "yyyy年MM月";
    public static final String CHINESE_TIME_PATTERN = "HH时mm分ss秒";

    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final int MILLISECONDS_OF_SECOND = 1000;
    public static final int SECONDS_OF_MINUTE = 60;
    public static final int MINUTES_OF_HOUR = 60;
    public static final int HOURS_OF_DAY = 24;
    public static final int DAYS_OF_WEEK = 7;
    public static final int MONTHS_OF_YEAR = 12;

    public static final long SECONDS_OF_HOUR = MINUTES_OF_HOUR * SECONDS_OF_MINUTE;
    public static final long SECONDS_OF_DAY = HOURS_OF_DAY * SECONDS_OF_HOUR;
    public static final long SECONDS_OF_WEEK = DAYS_OF_WEEK * SECONDS_OF_DAY;

    public static final long MILLISECONDS_OF_MINUTE = SECONDS_OF_MINUTE * MILLISECONDS_OF_SECOND;
    public static final long MILLISECONDS_OF_HOUR = MINUTES_OF_HOUR * MILLISECONDS_OF_MINUTE;
    public static final long MILLISECONDS_OF_DAY = HOURS_OF_DAY * MILLISECONDS_OF_HOUR;
    public static final long MILLISECONDS_OF_WEEK = DAYS_OF_WEEK * MILLISECONDS_OF_DAY;

    /**
     * 以下均为估计时长
     **/
    public static final int DAYS_OF_MONTH = 30;
    public static final int DAYS_OF_YEAR = 365;

    public static final long SECONDS_OF_MONTH = DAYS_OF_MONTH * SECONDS_OF_DAY;
    public static final long SECONDS_OF_YEAR = DAYS_OF_YEAR * SECONDS_OF_DAY;

    public static final long MILLISECONDS_OF_MONTH = DAYS_OF_MONTH * MILLISECONDS_OF_DAY;
    public static final long MILLISECONDS_OF_YEAR = DAYS_OF_YEAR * MILLISECONDS_OF_DAY;

    private DateTimeConstant() {}
}