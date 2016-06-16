/*
 * @(#)SpringAuthenticationFailureHandler.java, 2015-7-15.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.service.impl;

import nanshen.data.SystemUtil.LoginError;
import nanshen.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WANG Minghao
 */
@Service
public class SpringAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final String PARAM_USERNAME = "email";

    @Autowired
    private SpringUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        LoginError loginError = LoginError.PASSWORD_ERROR;
        if (exception instanceof BadCredentialsException) {
            increaseFailureCount(request);
            if (failureTooMuch(request)) {
                loginError = LoginError.PASSWORD_ERROR_TOO_MUCH;
            }
        } else if (exception instanceof DisabledException) {
            loginError = LoginError.ACCOUNT_DISABLE;
        } else if (exception instanceof LockedException) {
            loginError = LoginError.PASSWORD_ERROR_TOO_MUCH;
        }
        LogUtils.info("login fail");
        response.sendRedirect("/auth/fail");
    }

    private void increaseFailureCount(HttpServletRequest request) {
        String username = request.getParameter(PARAM_USERNAME);
        userDetailsService.increaseFailureCount(username);
    }

    private boolean failureTooMuch(HttpServletRequest request) {
        String username = request.getParameter(PARAM_USERNAME);
        return userDetailsService.failureTooMuch(username);
    }

}
