package nanshen.utils;

import java.util.logging.Level;

/**
 * 提供一个Log对象，用于记录不同级别的log信息
 *
 * @author WANG Minghao
 */
public class LogUtils {

    private static final String EXCEPTION_PREFIX = " Exception: ";

    private LogUtils() {}

    /**
     * 按vaquero的格式打印日志
     * @param key
     * @param value
     */
    public static void vaquero(Object key, Object value) {
        info(String.format("@@ANALYSIS@@ %s=%s", key.toString(), value.toString()));
    }

    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void info(String msg, Throwable e) {
        log(Level.INFO, msg, e);
    }

    public static void severe(String msg) {
        log(Level.SEVERE, msg);
    }

    public static void severe(String msg, Throwable e) {
        log(Level.SEVERE, msg, e);
    }

    public static void warning(String msg) {
        log(Level.WARNING, msg);
    }

    public static void warning(String msg, Throwable e) {
        log(Level.WARNING, msg, e);
    }

    private static void log(Level level, String msg, Throwable e) {
//        log(level, "{" + RequestUtils.loginedBuyerId() + "} " + msg + EXCEPTION_PREFIX + e.toString());
        log(level, "{未登录用户} " + msg + EXCEPTION_PREFIX + e.toString());
        e.printStackTrace();
    }

    private static void log(Level level, String msg) {
        System.out.println(level + "{未登录用户} " + msg);
    }

}

