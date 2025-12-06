package com.modulix.framework.mybatis.plus.api.page;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.NoArgsConstructor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 分页插件
 *
 * @author lipanre
 */
@NoArgsConstructor
public class PaginationInterceptor extends PaginationInnerInterceptor {

    private static final String IGNORE_KEY = "page";

    private static final String PARAMETER_NAME = "PAGE_PARAMETER_NAME";

    private static final List<String> SINGLE_PARAMETER_NAMES = List.of("param1", "arg0");

    public PaginationInterceptor(DbType dbType) {
        super(dbType);
    }

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        IgnoreStrategy ignoreStrategy = InterceptorIgnoreHelper.getIgnoreStrategy(ms.getId());
        if ((Objects.nonNull(ignoreStrategy) &&
                Objects.nonNull(ignoreStrategy.getOthers()) &&
                Boolean.TRUE.equals(ignoreStrategy.getOthers().get(IGNORE_KEY))) || !PageContextHolder.getPageAble()) {
            return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
        // 如果请求是分页请求，在这里通过注解构建Page对象，传递给上一层进行调用
        // 构建分页参数
        Page<Object> page = PageContextHolder.getPageRequestInfo();
        parameter = buildPageParam(page, parameter, boundSql);
        if (super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql)) {
            return true;
        }
        return false;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 如果请求是分页请求，在这里通过注解构建Page对象，传递给上一层进行调用
        if (!PageContextHolder.getPageAble()) {
            super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            return;
        }
        Page<Object> page = PageContextHolder.getPageRequestInfo();
        // 构建分页参数
        parameter = buildPageParam(page, parameter, boundSql);
        super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    /**
     * 查找分页参数
     *
     * @param page           分页对象
     * @param parameterObject 参数对象
     * @return 分页参数
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object buildPageParam(Page<Object> page, Object parameterObject, BoundSql boundSql) {
        if (parameterObject != null) {
            if (parameterObject instanceof Map paramMap) {
                paramMap.put(PARAMETER_NAME, page);
                return paramMap;
            } else if (parameterObject instanceof IPage) {
                // 如果配置了注解，又在方法上使用了Page参数，那么以方法上的page参数为准
                return parameterObject;
            }
            Map<String, Object> params = new HashMap<>();

            if (ClassUtils.isSimpleValueType(parameterObject.getClass())) {
                params.put(PARAMETER_NAME, page);
                List<ParameterMapping> parameterMappings = PluginUtils.mpBoundSql(boundSql).parameterMappings();
                params.put(parameterMappings.getFirst().getProperty(), parameterObject);
                SINGLE_PARAMETER_NAMES.forEach(name -> params.put(name, parameterObject));
                return params;
            }

            ReflectionUtils.doWithFields(parameterObject.getClass(), field -> {
                field.setAccessible(true);
                Object fieldValue = field.get(parameterObject);
                if (Objects.isNull(fieldValue)) return;
                params.put(field.getName(), fieldValue);
            });
            params.put(PARAMETER_NAME, page);
            return params;
        }
        return page;
    }
}
