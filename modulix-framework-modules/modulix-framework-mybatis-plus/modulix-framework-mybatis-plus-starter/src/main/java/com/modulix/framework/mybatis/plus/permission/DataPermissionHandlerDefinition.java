package com.modulix.framework.mybatis.plus.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * 数据权限处理器定义对象
 *
 * <br>
 * {@code date} 2025/3/4 12:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataPermissionHandlerDefinition {

    /**
     * 处理器对象
     */
    private Object bean;

    /**
     * 处理器方法
     */
    private Method method;

    /**
     * 方法参数列表
     */
    private MethodParameter[] parameters;
}