package com.modulix.framework.mybatis.plus.api.annotation;


import com.modulix.framework.mybatis.plus.api.base.BaseDomain;

import java.lang.annotation.*;

/**
 * 删除符合条件的数据
 *
 * @author lipanre
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DeleteOperation {

    /**
     * 实体类
     *
     * @return 实体类
     */
    Class<? extends BaseDomain> value();

    /**
     * @return 删除条件
     */
    String condition();

}
