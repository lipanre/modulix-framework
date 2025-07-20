package com.modulix.framework.web.aip.annotation;

import java.lang.annotation.*;

/**
 * 可重复读请求
 *
 * <br>
 * {@code date} 2025/4/14 16:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface ReadRepeatable {
}
