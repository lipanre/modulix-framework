package com.modulix.framework.mybatis.plus.api.annotation;

import java.lang.annotation.*;

/**
 * 检查数据库是否存在指定条件的数据
 *
 * @author lipanre
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckExists {

    CheckExist[] value();

}
