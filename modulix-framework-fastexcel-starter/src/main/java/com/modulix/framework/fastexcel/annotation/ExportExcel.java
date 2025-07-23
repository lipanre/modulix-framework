package com.modulix.framework.fastexcel.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 导出excel注解
 *
 * <br>
 * {@code date} 2025/3/18 9:02
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Export(exportType = Export.ExportType.EXCEL)
public @interface ExportExcel {

    /**
     * @return 获取导出excel的数据的spel表达式
     */
    @AliasFor(annotation = Export.class)
    String value() default "#{#result.data}";

    /**
     * @return 导出的文件名
     */
    @AliasFor(annotation = Export.class)
    String fileName();

    /**
     * @return excel sheet名
     */
    String sheetName() default "";
}
