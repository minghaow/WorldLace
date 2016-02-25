/*
 * @(#)SpringAuthenticationSuccessHandler.java, 2015-7-17.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.service.impl;

import nanshen.dao.UserInfoDao;
import nanshen.utils.LogUtils;
import nanshen.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author WANG Minghao
 */
@Service
public class SpringAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String PARAM_USERNAME = "email";

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        recordLogin(request);
        setDefaultTargetUrl("/auth/success");
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void recordLogin(HttpServletRequest request) {
        String username = request.getParameter(PARAM_USERNAME);
        LogUtils.info("" + userInfoDao.login(username, RequestUtils.getRequestIp(), new Date()));
    }

}
