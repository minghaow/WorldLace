/*
 * @(#)SpringSecureChannelProcessor.java, 2015-2-26.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.SecureChannelProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * 当前买手系统的部署模式是“SSL termination proxy”，即
 * <pre>
 *                 https                 http
 * Web browser -------------> Nginx -------------> Application server
 * </pre>
 * 我们的服务接收到的始终是http的连接。而正常情况下，SpringSecurity默认使用的部署模式是
 * <pre>
 *                 https
 * Web browser -------------> Application server
 * </pre>
 * 为了让SpringSecurity能够兼容我们现在的部署模式，必须告诉SpringSecurity哪些连接是https的。
 * 我们的Nginx服务器会在转换https的请求的时候，在HttpHeader中添加“HTTPS”字段，其值为“on”。
 * 根据这个字段，便可以告知SpringSecurity哪些连接是https的。
 *
 * @author WANG Minghao
 */
@Service
public class SpringSecureChannelProcessor extends SecureChannelProcessor {

    @Override
    public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
        Assert.isTrue((invocation != null) && (config != null), "Nulls cannot be provided");

        for (ConfigAttribute attribute : config) {
            if (supports(attribute)) {
                HttpServletRequest httpRequest = invocation.getHttpRequest();
                if (!httpRequest.isSecure() && StringUtils.isBlank(httpRequest.getHeader("HTTPS"))) {
                    getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
                }
            }
        }
    }

}
