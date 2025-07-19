package com.modulix.framework.security.api.annotation;

import java.lang.annotation.*;

/**
 * 忽略认证注解
 *
 * <br>
 * {@code date} 2025/2/14 10:11
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreAuth {
}
