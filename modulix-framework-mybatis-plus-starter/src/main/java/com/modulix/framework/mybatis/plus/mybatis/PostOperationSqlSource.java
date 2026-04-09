package com.modulix.framework.mybatis.plus.mybatis;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.Collections;

/**
 * 用于存放检查sql的sql-source对象
 * <br>
 * 使用threadLocal存储，防止多线程出错
 *
 * <br>
 * {@code date} 2025/5/12 17:46
 */
@RequiredArgsConstructor
public class PostOperationSqlSource implements SqlSource {

    private final Configuration configuration;

    private static final ThreadLocal<String> checkSqlThreadLocal = new ThreadLocal<>();

    public static void setCheckSql(String sql) {
        checkSqlThreadLocal.set(sql);
    }

    public static void cleanSql() {
        checkSqlThreadLocal.remove();
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(configuration);
        SqlSource sqlSource = sqlSourceBuilder.parse(checkSqlThreadLocal.get(), parameterObject.getClass(), Collections.emptyMap());
        return sqlSource.getBoundSql(parameterObject);
    }
}
