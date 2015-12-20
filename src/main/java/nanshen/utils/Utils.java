/*
 * @(#)Utils.java, 2015-6-26.
 *
 * Copyright 2015 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.utils;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Utils类bean注解，用于将Utils类注入Velocity
 *
 * @author WANG Minghao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Utils {

    String value() default "";

}
