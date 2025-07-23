package com.modulix.framework.fastexcel.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <br>
 * {@code date} 2025/3/18 16:01
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Export(exportType = Export.ExportType.PDF)
public @interface ExportPDF {

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

}
