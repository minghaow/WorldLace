/*
 * @(#)SystemConstants.java, 2015-08-01.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.constant;

/**
 * SystemConstants
 *
 * @author WANG Minghao
 */
public class SystemConstants {

    /** base encode type */
    public static final String SYS_ENC = "utf-8";

    /** base domain */
    public static final String BASE_URL = "http://www.zaitaoyuan.com";
    public static final String IMAGE_URL = "http://image.zaitaoyuan.com";
    public static final String CDN_URL = "http://image-cdn.zaitaoyuan.com";

    /** default page length for lists */
    public static final int DEFAULT_PAGE_SIZE = 30;
    public static final int DEFAULT_CACHED_LOOK_SIZE = 100;
    public static final int DEFAULT_CACHED_SKU_SIZE = 3 * DEFAULT_CACHED_LOOK_SIZE;

    /** OSS related */
    public static String OSS_BASE_HTTP = "http://image-cdn.zaitaoyuan.com";
    public static String BUCKET_NAME = "taoyuan";

    /** hello Message */
    public static String HELLO_MSG_MORNING = "早上好";
    public static String HELLO_MSG_AFTERNOON = "下午好";
    public static String HELLO_MSG_EVENING = "晚上好";

}
