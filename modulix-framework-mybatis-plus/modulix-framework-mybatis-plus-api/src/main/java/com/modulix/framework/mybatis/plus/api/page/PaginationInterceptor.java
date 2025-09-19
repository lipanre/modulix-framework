package com.modulix.framework.mybatis.plus.api.page;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.NoArgsConstructor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
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

    public PaginationInterceptor(DbType dbType) {
        super(dbType);
    }

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        IgnoreStrategy ignoreStrategy = InterceptorIgnoreHelper.getIgnoreStrategy(ms.getId());
        if (Objects.nonNull(ignoreStrategy) && Boolean.TRUE.equals(ignoreStrategy.getOthers().get(IGNORE_KEY))) {
            return false;
        }
        // 如果请求是分页请求，在这里通过注解构建Page对象，传递给上一层进行调用
        PageRequestInfo<Object> pageRequestInfo = PageContextHolder.getPageRequestInfo();
        if (Objects.isNull(pageRequestInfo) || !pageRequestInfo.isPageable()) {
            return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        }
        // 构建分页参数
        parameter = buildPageParam(pageRequestInfo.getPage(), parameter);
        try {
            return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
        } finally {
            // 执行第一次查询之后,后续查询取消分页
            pageRequestInfo.setPageable(false);
        }
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // 如果请求是分页请求，在这里通过注解构建Page对象，传递给上一层进行调用
        PageRequestInfo<Object> pageRequestInfo = PageContextHolder.getPageRequestInfo();
        if (Objects.isNull(pageRequestInfo) || !pageRequestInfo.isPageable()) {
            super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            return;
        }
        // 构建分页参数
        parameter = buildPageParam(pageRequestInfo.getPage(), parameter);
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
    public static Object buildPageParam(Page<Object> page, Object parameterObject) {
        if (parameterObject != null) {
            if (parameterObject instanceof Map paramMap) {
                paramMap.put(PARAMETER_NAME, page);
                return paramMap;
            } else if (parameterObject instanceof IPage) {
                // 如果配置了注解，又在方法上使用了Page参数，那么以方法上的page参数为准
                return parameterObject;
            }
        }
        return page;
    }
}
