package com.modulix.framework.mybatis.plus.api.annotation;


import com.modulix.framework.mybatis.plus.api.base.BaseDomain;

import java.lang.annotation.*;

/**
 * 检查数据库是否存在指定条件的数据
 * <p>
 * 如果存在则抛出异常
 *
 * @author lipanre
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckNotExists.class)
public @interface CheckNotExist {

    /**
     * @return 实体类
     */
    Class<? extends BaseDomain> value();

    /**
     * @return spel构建的查询条件
     */
    String condition();

    /**
     * @return 当存在符合条件的数据的时候，给前端的提示信息
     */
    String existNotify() default "";

    /**
     * @return 是否查询已经被逻辑删除的数据
     */
    boolean includeDeleted() default false;


}
