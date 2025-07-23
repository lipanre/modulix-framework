package com.modulix.framework.fastexcel.processor;

import com.modulix.framework.fastexcel.handler.ExportHandler;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 导出的postProcessor
 *
 * <br>
 * {@code date} 2025/3/18 10:37
 */
@SuppressWarnings("NullableProblems")
public class ExportPostProcessor implements BeanPostProcessor {

    @Resource
    private List<ExportHandler> exportHandlers;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 扫描所有controller
        // 扫描controller下面有@ExportExcel的方法
        // 动态注册接口进入spring容器，实现文件导出：https://liuzhicong.cn/index.php/study/springboot-dynamic-add-api.html
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), RequestMapping.class);
        if (Objects.isNull(requestMapping)) {
            return bean;
        }

        // 对这个类里面每个方法做判断并处理，动态注册接口进入容器，然后实现导出功能
        for (Method method : bean.getClass().getMethods()) {
            exportHandlers.stream().filter(handler -> handler.isSupport(method)).forEach(handler -> handler.handle(bean, method));
        }
        return bean;
    }
}
