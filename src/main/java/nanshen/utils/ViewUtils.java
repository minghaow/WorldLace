package nanshen.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

/**
 * 页面显示工具类
 *
 * @author WANG Minghao
 */
public class ViewUtils {

    public static String convertDateToString(Date date) {
        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String convertToDisplayRate(long rate) {
        BigDecimal a = BigDecimal.valueOf(rate);
        BigDecimal b = BigDecimal.valueOf(100);
        return a.divide(b).toString();
    }

    public static long priceConverter(String priceString) {
        BigDecimal price = new BigDecimal(priceString);
        price = price.multiply(BigDecimal.valueOf(100));
        price = price.setScale(0, BigDecimal.ROUND_HALF_UP);
        return Long.parseLong(price.toString());
    }

    public static long priceConverter(double priceDouble) {
        BigDecimal price = new BigDecimal(priceDouble);
        price = price.multiply(BigDecimal.valueOf(100));
        price = price.setScale(0, BigDecimal.ROUND_HALF_UP);
        return Long.parseLong(price.toString());
    }

    public static String priceConverter(long priceLong) {
        BigDecimal a = BigDecimal.valueOf(priceLong);
        BigDecimal b = BigDecimal.valueOf(100);
        return a.divide(b).toString() + "元";
    }

    public static String priceConverterNo(long priceLong) {
        BigDecimal a = BigDecimal.valueOf(priceLong);
        BigDecimal b = BigDecimal.valueOf(100);
        return a.divide(b).toString();
    }

    public static long priceDoubleToLong(double priceDouble) {
        BigDecimal price = new BigDecimal(priceDouble);
        price = price.setScale(0, BigDecimal.ROUND_FLOOR);
        return Long.parseLong(price.toString());
    }

    /**
     * Convert special characters into HTML format. will return "" if input is
     * null
     */
    public static String getHTMLValidText(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (ch == '"') {
                buffer.append("&quot;");
            } else if (ch == '&') {
                buffer.append("&amp;");
            } else if (ch == '<') {
                buffer.append("&lt;");
            } else if (ch == '>') {
                buffer.append("&gt;");
            } else if (ch == '\'') {
                buffer.append("&#39;");
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
}
