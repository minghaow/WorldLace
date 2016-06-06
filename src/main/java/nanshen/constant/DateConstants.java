/*
 * @(#)DateConstants.java, 2014-12-14.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式相关的常量
 *
 * @author WANG Minghao
 */
public class DateConstants {

    /**
     * 一个线程安全的DateFormat实现
     * <p />
     * {@link SimpleDateFormat}的创建是比较耗时的，每次都new一个使用会消耗一定的系统性能
     * （一般情况下我们并不关心这点性能，但是在不改变代码结构的情况下，通过简单的修改就可以实现高性能的方案却是我们应该追求的），
     * 所以应该放置一个全局的一直使用，但{@link SimpleDateFormat}不是线程安全的，因此需要实现一个线程安全的DateFormat。
     * <br />
     * 该类通过{@link ThreadLocal}封装{@link SimpleDateFormat}实现了一个线程安全的DateFormat
     */
    public static class ThreadLocalDateFormat {

        private final ThreadLocal<SimpleDateFormat> THREAD_LOCAL;

        private ThreadLocalDateFormat(final String format) {
            THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    return new SimpleDateFormat(format);
                }
            };
        }

        public String format(Date date) {
            return THREAD_LOCAL.get().format(date);
        }

        public Date parse(String date) throws ParseException {
            return THREAD_LOCAL.get().parse(date);
        }

    }

    public static ThreadLocalDateFormat MM_DD_YYYY_SLASH = new ThreadLocalDateFormat("MM/dd/yyyy");

    public static ThreadLocalDateFormat YYYY_MM_DD = new ThreadLocalDateFormat("yyyy-MM-dd");

    public static ThreadLocalDateFormat MM_DD = new ThreadLocalDateFormat("MM-dd");

    public static ThreadLocalDateFormat YYYY_MM_DD_HH_MM_SS = new ThreadLocalDateFormat("yyyy-MM-dd HH:mm:ss");

    public static ThreadLocalDateFormat YYYYMM = new ThreadLocalDateFormat("yyyyMM");

    public static ThreadLocalDateFormat YYYYMMDD = new ThreadLocalDateFormat("yyyyMMdd");

    public static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

}
