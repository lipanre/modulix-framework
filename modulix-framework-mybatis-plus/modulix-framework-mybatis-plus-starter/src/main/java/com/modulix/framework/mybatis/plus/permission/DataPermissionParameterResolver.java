package com.modulix.framework.mybatis.plus.permission;

import net.sf.jsqlparser.schema.Table;
import org.springframework.core.MethodParameter;

/**
 * 放射方法参数解析器
 *
 * <br>
 * {@code date} 2025/3/4 14:35
 */
public interface DataPermissionParameterResolver {

    /**
     * 是否支持当前参数的解析
     * <br>
     * @param parameter 参数
     * @return true - 支持
     *         false - 不支持
     */
    boolean isSupport(MethodParameter parameter);

    /**
     * 解析当前参数
     *
     * @param parameter 参数
     * @param table 数据表
     * @return 参数对象
     */
    Object resolve(MethodParameter parameter, Table table);
}