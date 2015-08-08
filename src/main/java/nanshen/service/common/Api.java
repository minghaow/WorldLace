/*
 * @(#)Api.java, 2015-1-4.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.service.common;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Api层bean注解，Api层是Service的子层，用于实现在多个Service中均需要的组件
 *
 * @author WANG Minghao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Api {

    String value() default "";

}
