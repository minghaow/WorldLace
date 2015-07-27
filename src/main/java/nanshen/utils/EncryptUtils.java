/*
 * @(#)EncryptUtils.java, 2014-12-23.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密签名相关的工具类
 *
 * @author WANG Minghao
 */
public class EncryptUtils {

    private static final char PASSWORD_SEPARATOR = '|';

    /**
     * 根据原始密码，通过加Salt的方式生成一个不可逆的密文，用于密码存储
     *
     * @param rawPass
     * @return
     */
    public static String encodePassword(String rawPass) {
        String salt = generateSalt();
        return encodePassword(rawPass, salt);
    }

    private static String generateSalt() {
        return "1d312ye2shd12q12dfsd";
    }

    private static String encodePassword(String rawPass, String salt) {
        return DigestUtils.md5Hex(rawPass + salt) + PASSWORD_SEPARATOR + salt;
    }

    /**
     * 判断给定的原始密码和密文是否匹配
     * <p />
     * <b>注意</b>：只能用于判断{@link #encodePassword(String, String)}生成的密文
     *
     * @param encPass 密文
     * @param rawPass 原始密码
     * @return
     */
    public static boolean isPasswordValid(String encPass, String rawPass) {
        int index = encPass.indexOf(PASSWORD_SEPARATOR);
        if (index < 0) {
            return false;
        }
        String salt = encPass.substring(index + 1);
        return encPass.equals(encodePassword(rawPass, salt));
    }

}
