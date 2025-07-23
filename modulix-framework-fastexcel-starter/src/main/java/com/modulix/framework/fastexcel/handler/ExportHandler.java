package com.modulix.framework.fastexcel.handler;

import java.lang.reflect.Method;

/**
 * 导出处理器
 *
 * <br>
 * {@code date} 2025/3/18 15:51
 */
public interface ExportHandler {


    /**
     * 是否支持当前导出处理器
     *
     * @param method 待处理的方法
     * @return true - 支持 <br> false - 不支持
     */
    boolean isSupport(Method method);

    /**
     * 处理映射
     *
     * @param bean 目标对象
     * @param method 目标方法
     */
    void handle(Object bean, Method method);

}
