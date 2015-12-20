/*
 * @(#)SystemConstants.java, 2015-08-01.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.constant;

/**
 * Time Constants
 *
 * @author WANG Minghao
 */
public class TimeConstants {

    public static final long SECOND_IN_MILLISECONDS = 1000L;
    public static final long TEN_SECOND_IN_MILLISECONDS = 1000L * 10;
    public static final long THIRTY_SECOND_IN_MILLISECONDS = 1000L * 30;
    public static final long MINUTE_IN_MILLISECONDS = 1000L * 60;
    public static final long TEN_MINUTE_IN_MILLISECONDS = 1000L * 600;
    public static final long FIVE_MINUTE_IN_MILLISECONDS = 1000L * 300;
    public static final long HALF_HOUR_IN_MILLISECONDS = 1000L * 1800;
    public static final long HOUR_IN_MILLISECONDS = 1000L * 3600;
    public static final long DAY_IN_MILLISECONDS = HOUR_IN_MILLISECONDS * 24;
    public static final long SIX_DAY_IN_MILLISECONDS = DAY_IN_MILLISECONDS * 6;
    public static final long WEEK_IN_MILLISECONDS = DAY_IN_MILLISECONDS * 7;
    public static final long MONTH_IN_MILLISECONDS = DAY_IN_MILLISECONDS * 30;
    public static final long YEAR_IN_MILLISECONDS = DAY_IN_MILLISECONDS * 365;
    public static final long TEN_YEAR_IN_MILLISECONDS = YEAR_IN_MILLISECONDS * 10;

    public static final int MINUTE_IN_SECONDS = 60;
    public static final int FIVE_MINUTE_IN_SECONDS = 300;
    public static final int TEN_MINUTE_IN_SECONDS = 600;
    public static final int HALF_HOUR_IN_SECONDS = 1800;
    public static final int HOUR_IN_SECONDS = 3600;
    public static final int DAY_IN_HOUR = 24;
    public static final int DAY_IN_SECONDS = HOUR_IN_SECONDS * 24;
    public static final int WEEK_IN_SECONDS = DAY_IN_SECONDS * 7;
    public static final int FOUR_WEEK_IN_SECONDS = WEEK_IN_SECONDS * 4;
    public static final int YEAR_IN_SECONDS = DAY_IN_SECONDS * 365;

    /** 某些定时任务载入订单的时间范围 */
    public static final long UPDATE_PERIOD = HOUR_IN_MILLISECONDS * 2;
    public static final long SMALL_UPDATE_PERIOD = FIVE_MINUTE_IN_MILLISECONDS * 3;

}
