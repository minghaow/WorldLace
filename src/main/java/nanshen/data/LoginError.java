/*
 * @(#)LoginError.java, 2015-7-16.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.data;

/**
 * 登陆错误时的提示信息
 *
 * @author WANG Minghao
 */
public enum LoginError {

    PASSWORD_ERROR("用户名或者密码错误"),

    PASSWORD_ERROR_TOO_MUCH("密码错误次数过多，该账号已经被封禁，请等待10分钟后再试"),

    ACCOUNT_DISABLE("账号已经失效，请联系管理员");

    LoginError(String desc) {
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

}
