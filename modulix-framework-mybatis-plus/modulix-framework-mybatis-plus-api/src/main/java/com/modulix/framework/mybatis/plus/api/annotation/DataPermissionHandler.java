package com.modulix.framework.mybatis.plus.api.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 数据权限处理器
 * <br>
 * 被注解的方法目前支持以下参数：
 * @see net.sf.jsqlparser.schema.Table 数据表对象
 *
 * <br>
 * 被注解的方法目前支持以下返回类型：
 * @see net.sf.jsqlparser.expression.Expression 及其子类
 *
 * <br>
 * {@code date} 2025/3/4 12:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataPermissionHandler {

    /**
     * 处理的数据权限类型
     *
     * @return 数据权限类型
     */
    @AliasFor("type")
    String value() default "";

    /**
     * 处理的数据权限类型
     *
     * @return 数据权限类型
     */
    @AliasFor("value")
    String type() default "";

    /**
     * 处理的数据库表名列表
     *
     * @return 数据库表名列表
     */
    String[] tables() default {};

}
