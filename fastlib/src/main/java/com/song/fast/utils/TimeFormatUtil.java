package com.song.fast.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * description: This is TimeFormatUtil
 * author: Administrator
 * date: 2017/12/14 11:17
 * update: date string 时间戳互相转换 和 获取星期几
 */
public class TimeFormatUtil {

    // 默认的时间格式
    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * 时间戳转日期
     *
     * @param millis
     *            时间戳毫秒
     * @param format
     *            时间格式
     * @return 字符串
     */
    public static String millis2String(final long millis,
                                       final DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 时间戳转默认格式的日期
     *
     * @param millis
     *            时间戳毫秒
     * @return 字符串
     */
    public static String millis2String(final long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为时间戳
     *
     * @param time
     *            时间字符串
     * @param format
     *            时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为默认格式时间戳
     *
     * @param time
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为 Date 类型
     * @param time 时间字符串
     * @param format 时间格式
     * @return Date 类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将时间字符串转为 Date 类型
     * @param time
     * @return Date 类型
     */
    public static Date string2Date(final String time) {
        return string2Date(time, DEFAULT_FORMAT);
    }


    /**
     * 将 Date 类型转为时间字符串
     * @param date Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        return format.format(date);
    }

    /**
     * 将 Date 类型转为时间字符串
     * @param date Date 类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, DEFAULT_FORMAT);
    }


    /**
     * 将 Date 类型转为星期几
     * @param date Date 类型时间
     * @return 时间字
     * @return 星期几字符串
     */
    public static String getTodayOfWeek(Date date) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE",Locale.CHINA);
        return dateFm.format(date);
    }

}
