package com.lwh147.common.core.constant;

/**
 * 日期时间常量类
 *
 * @author lwh
 * @date 2021/11/26 11:00
 **/
public class DateTimeConstant {
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_TIMEZONE = "GMT+8";

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