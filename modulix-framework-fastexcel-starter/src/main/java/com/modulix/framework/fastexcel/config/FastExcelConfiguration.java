package com.modulix.framework.fastexcel.config;

import com.modulix.framework.fastexcel.handler.ExportExcelHandler;
import com.modulix.framework.fastexcel.processor.ExportPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lipanre
 */
@Configuration
public class FastExcelConfiguration {

    @Bean
    public ExportExcelHandler exportExcelHandler() {
        return new ExportExcelHandler();
    }

    @Bean
    public ExportPostProcessor exportPostProcessor() {
        return new ExportPostProcessor();
    }

}
