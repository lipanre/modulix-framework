package com.modulix.framework.mybatis.plus.api.annotation;


import java.lang.annotation.*;

/**
 * 分页请求
 * <br>
 * 被注解的方法必须要返回一下类型：
 * @see com.modulix.framework.common.http.Response
 *
 * @author lipanre
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageRequest {

    /**
     * @return 分页参数名
     */
    String pageNum() default "current";

    /**
     * @return 分页大小参数名
     */
    String pageSize() default "size";

}
