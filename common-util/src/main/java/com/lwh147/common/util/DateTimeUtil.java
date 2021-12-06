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
     * @return 距离现在的时长描述
     * <p>
     * 如果参数为过去，则返回刚刚、几分钟前、几周前，以此类推
     * <p>
     * 如果参数为未来，则返回倒计时，格式从少到多为：56、01:56、11:01:56、1天 11:01:01
     */
    public static String fromNow(Date datetime) {
        long time = datetime.getTime() / DateTimeConstant.MILLISECONDS_OF_SECOND;
        long now = new Date().getTime() / DateTimeConstant.MILLISECONDS_OF_SECOND;
        long ago = now - time;

        if (ago < 0) {
            ago = -ago;
            if (ago < DateTimeConstant.SECONDS_OF_MINUTE) {
                return String.format("%02d", ago);
            }
            if (ago < DateTimeConstant.SECONDS_OF_HOUR) {
                return String.format("%02d:%02d", ago / DateTimeConstant.SECONDS_OF_MINUTE,
                        ago % DateTimeConstant.SECONDS_OF_MINUTE);
            }
            if (ago < DateTimeConstant.SECONDS_OF_DAY) {
                return String.format("%02d:%02d:%02d", ago / DateTimeConstant.SECONDS_OF_HOUR,
                        ago % DateTimeConstant.SECONDS_OF_HOUR / DateTimeConstant.SECONDS_OF_MINUTE,
                        ago % DateTimeConstant.SECONDS_OF_MINUTE);
            }
            return String.format("%d天 %02d:%02d:%02d", ago / DateTimeConstant.SECONDS_OF_DAY,
                    ago % DateTimeConstant.SECONDS_OF_DAY / DateTimeConstant.SECONDS_OF_HOUR,
                    ago % DateTimeConstant.SECONDS_OF_HOUR / DateTimeConstant.SECONDS_OF_MINUTE,
                    ago % DateTimeConstant.SECONDS_OF_MINUTE);
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
            if (ago < DateTimeConstant.SECONDS_OF_MONTH) {
                return ago / DateTimeConstant.SECONDS_OF_WEEK + "周前";
            }
            if (ago < DateTimeConstant.SECONDS_OF_YEAR) {
                return ago / DateTimeConstant.SECONDS_OF_MONTH + "月前";
            }
            return ago / DateTimeConstant.SECONDS_OF_YEAR + "年前";
        }
    }

    private DateTimeUtil() {
    }
}
