package com.modulix.framework.mybatis.plus.permission.resolve;

import com.modulix.framework.mybatis.plus.permission.DataPermissionParameterResolver;
import net.sf.jsqlparser.schema.Table;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * table对象方法参数解析器
 *
 * <br>
 * {@code date} 2025/3/4 14:52
 */
@Component
public class TableMethodParameterResolver implements DataPermissionParameterResolver {

    @Override
    public boolean isSupport(MethodParameter parameter) {
        return Objects.equals(parameter.getParameterType(), Table.class);
    }

    @Override
    public Object resolve(MethodParameter parameter, Table table) {
        return table;
    }
}