package com.lwh147.common.util;

import com.lwh147.common.core.constant.DateTimeConstant;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * <p>
 * 如不做特殊说明则返回的Date类型时间均为0点
 *
 * @author lwh
 * @date 2021/5/3 20:23
 **/
public class DateTimeUtil {
    /**
     * 置时间为0点
     *
     * @param datetime 日期时间
     **/
    public static void clearTime(Date datetime) {
        final Calendar c = Calendar.getInstance();
        c.setTime(datetime);
        clearTime(c);
        datetime.setTime(c.getTimeInMillis());
    }

    /**
     * 置时间为0点
     *
     * @param c 日期时间
     **/
    public static void clearTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 获取今天0点
     *
     * @return 今天0点
     **/
    public static Date getTodayWithTime0() {
        final Calendar c = Calendar.getInstance();
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取amount天后的日期
     *
     * @param amount 天数，为负表示amount天前
     * @return 目标日期
     **/
    public static Date getDateAfterToday(int amount) {
        return getDateAfterTheDay(new Date(), amount);
    }

    /**
     * 获取指定日期amount天后的日期
     *
     * @param date   指定日期
     * @param amount 天数，为负表示amount天前
     * @return 目标日期
     **/
    public static Date getDateAfterTheDay(Date date, int amount) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, amount);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取本月第一天的日期
     *
     * @return 目标日期
     **/
    public static Date getFirstDayOfMonth() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取本月最后一天的日期
     *
     * @return 目标日期
     **/
    public static Date getLastDayOfMonth() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取下个月第一天的日期
     *
     * @return 目标日期
     **/
    public static Date getFirstDayOfNextMonth() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取下个月最后一天的日期
     *
     * @return 目标日期
     **/
    public static Date getLastDayOfNextMonth() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 2);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取amount个月后最后一天的日期
     *
     * @param amount 月数，为负表示前amount月
     * @return 目标日期
     **/
    public static Date getLastDayOfMonthLater(int amount) {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, amount + 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 获取amount个月后第一天的日期
     *
     * @param amount 月数，为负表示前amount月
     * @return 目标日期
     **/
    public static Date getFirstDayOfMonthLater(int amount) {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, amount);
        c.set(Calendar.DAY_OF_MONTH, 1);
        clearTime(c);
        return c.getTime();
    }

    /**
     * 距离现在多久
     *
     * @param datetime 指定日期时间
     * @return 距离现在的时长描述，如几分钟前，几分钟后等
     */
    public static String fromNow(Date datetime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        long time = datetime.getTime() / DateTimeConstant.MILLISECONDS_OF_SECOND;
        long now = new Date().getTime() / DateTimeConstant.MILLISECONDS_OF_SECOND;
        long ago = now - time;

        if (ago < 0) {
            ago = -ago;
            if (ago < DateTimeConstant.SECONDS_OF_MINUTE) {
                return ago + "秒后";
            }
            return null;
        } else {
            if (ago < DateTimeConstant.SECONDS_OF_MINUTE) {
                return "刚刚";
            }
            if (ago < DateTimeConstant.SECONDS_OF_HOUR) {
                return ago / DateTimeConstant.SECONDS_OF_MINUTE + "分钟前";
            }
            if (ago < DateTimeConstant.SECONDS_OF_DAY) {
                return ago / DateTimeConstant.SECONDS_OF_HOUR + "小时前";
            }
            if (ago < DateTimeConstant.SECONDS_OF_WEEK) {
                return ago / DateTimeConstant.SECONDS_OF_DAY + "天前";
            }
            return "";
        }
    }

    /**
     * 距离截止日期还有多长时间
     *
     * @param date
     * @return
     */
    public static String fromDeadline(Date date) {
        long deadline = date.getTime() / 1000;
        long now = (new Date().getTime()) / 1000;
        long remain = deadline - now;
        if (remain <= HOUR)
            return "只剩下" + remain / MINUTE + "分钟";
        else if (remain <= DAY)
            return "只剩下" + remain / HOUR + "小时"
                    + (remain % HOUR / MINUTE) + "分钟";
        else {
            long day = remain / DAY;
            long hour = remain % DAY / HOUR;
            long minute = remain % DAY % HOUR / MINUTE;
            return "只剩下" + day + "天" + hour + "小时" + minute + "分钟";
        }

    }

    /**
     * 距离今天的绝对时间
     *
     * @param date
     * @return
     */
    public static String toToday(Date date) {
        long time = date.getTime() / 1000;
        long now = (new Date().getTime()) / 1000;
        long ago = now - time;
        if (ago <= HOUR)
            return ago / MINUTE + "分钟";
        else if (ago <= DAY)
            return ago / HOUR + "小时" + (ago % HOUR / MINUTE) + "分钟";
        else if (ago <= DAY * 2)
            return "昨天" + (ago - DAY) / HOUR + "点" + (ago - DAY)
                    % HOUR / MINUTE + "分";
        else if (ago <= DAY * 3) {
            long hour = ago - DAY * 2;
            return "前天" + hour / HOUR + "点" + hour % HOUR / MINUTE
                    + "分";
        } else if (ago <= MONTH) {
            long day = ago / DAY;
            long hour = ago % DAY / HOUR;
            long minute = ago % DAY % HOUR / MINUTE;
            return day + "天前" + hour + "点" + minute + "分";
        } else if (ago <= YEAR) {
            long month = ago / MONTH;
            long day = ago % MONTH / DAY;
            long hour = ago % MONTH % DAY / HOUR;
            long minute = ago % MONTH % DAY % HOUR / MINUTE;
            return month + "个月" + day + "天" + hour + "点" + minute + "分前";
        } else {
            long year = ago / YEAR;
            long month = ago % YEAR / MONTH;
            long day = ago % YEAR % MONTH / DAY;
            return year + "年前" + month + "月" + day + "天";
        }

    }

    private DateTimeUtil() {

    }

}
