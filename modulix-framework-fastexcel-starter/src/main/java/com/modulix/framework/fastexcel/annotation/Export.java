package com.modulix.framework.fastexcel.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 导出注解
 *
 * <br>
 * {@code date} 2025/3/18 15:30
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Export {

    /**
     * @return 导出数据集获取spel
     */
    @AliasFor("dataSpel")
    String value() default "";

    /**
     * @return 导出数据集获取spel
     */
    @AliasFor("value")
    String dataSpel() default "";

    /**
     * @return 导出文件名
     */
    String fileName() default "";

    /**
     * @return 导出类型
     */
    ExportType exportType();



    enum ExportType {
        /**
         * 导出excel
         */
        EXCEL,

        /**
         * 导出pdf
         */
        PDF,
    }

}
