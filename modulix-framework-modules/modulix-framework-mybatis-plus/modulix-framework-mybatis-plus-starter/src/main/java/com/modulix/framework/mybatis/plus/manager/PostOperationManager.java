package com.modulix.framework.mybatis.plus.manager;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.modulix.framework.mybatis.plus.api.base.BaseDomain;
import com.modulix.framework.mybatis.plus.mybatis.PostOperationSqlSource;
import jakarta.annotation.Resource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 检查管理器
 *
 * <br>
 * {@code date} 2025/5/12 15:38
 */
public class PostOperationManager {

    /**
     * 检查是否存在sql模板
     */
    private static final String EXISTS_SQL_TEMPLATE = "select count(*) from %s where (%s)";

    /**
     * 删除sql模板
     */
    private static final String DELETE_SQL_TEMPLATE = "update %s set %s where (%s)";


    @Resource
    private SqlSession sqlSession;

    /**
     * 检查符合条件的数据是否存在的statementId
     */
    private static final String EXIST_MAPPED_STATEMENT_ID = "_check.exist";
    private static final String EXIST_MAPPED_STATEMENT_RETURN_ID = "_check.exist_return";

    /**
     * 检查符合条件的数据是否存在的statementId
     */
    private static final String UPDATE_MAPPED_STATEMENT_ID = "_operation.update";
    private static final String UPDATE_MAPPED_STATEMENT_RETURN_ID = "_operation.update_return";


    public void registerStatement() {
        // 注册检查是否存在statement
        registerCheckStatement(EXIST_MAPPED_STATEMENT_ID, SqlCommandType.SELECT, configuration -> Collections.singletonList(new ResultMap.Builder(configuration, EXIST_MAPPED_STATEMENT_RETURN_ID, Boolean.class, new ArrayList<>()).build()));
        registerCheckStatement(UPDATE_MAPPED_STATEMENT_ID, SqlCommandType.UPDATE, configuration -> Collections.singletonList(new ResultMap.Builder(configuration, UPDATE_MAPPED_STATEMENT_RETURN_ID, Integer.class, new ArrayList<>()).build()));
    }

    /**
     * 动态注册mybatis statement
     *
     * @param statementId    statementId（相当于mapper全路径+方法名）
     * @param sqlCommandType sql类型
     * @param resultMaps     返回值映射
     */
    @SuppressWarnings("SameParameterValue")
    private void registerCheckStatement(String statementId, SqlCommandType sqlCommandType, Function<Configuration, List<ResultMap>> resultMaps) {
        Configuration configuration = sqlSession.getConfiguration();
        PostOperationSqlSource postOperationSqlSource = new PostOperationSqlSource(configuration);
        MappedStatement ms = new MappedStatement.Builder(configuration, statementId, postOperationSqlSource, sqlCommandType).resultMaps(resultMaps.apply(configuration)).build();
        configuration.addMappedStatement(ms);
    }

    /**
     * 执行删除操作
     *
     * @param clazz 实体类名
     * @param condition 删除条件
     * @param params 删除参数
     * @return 删除的数据行数
     */
    public int delete(Class<? extends BaseDomain> clazz, String condition, Map<String, Object> params) {
        String tableName = getTableInfo(clazz, TableInfo::getTableName);
        String logicDeleteSql = getTableInfo(clazz, tableInfo -> tableInfo.getLogicDeleteSql(false, false));
        String logicNotDeleteSql = getTableInfo(clazz, tableInfo -> tableInfo.getLogicDeleteSql(true, true));
        String sql = String.format(DELETE_SQL_TEMPLATE, tableName, logicDeleteSql, condition + logicNotDeleteSql);
        return executeOperation(sql, () -> sqlSession.update(UPDATE_MAPPED_STATEMENT_ID, params));
    }

    /**
     * 检查指定表中，是否存在符合条件的数据
     *
     * @param clazz     实体类
     * @param condition 过滤条件（已替换spel为对应的参数）
     * @param params    sql参数
     * @return true - 存在 <br> false - 不存在
     */
    public boolean notExist(Class<? extends BaseDomain> clazz, String condition, Map<String, Object> params, boolean logicDelete) {
        return !exist(clazz, condition, params, logicDelete);
    }


    /**
     * 检查指定表中，是否存在符合条件的数据
     *
     * @param clazz     实体类
     * @param condition 过滤条件（已替换spel为对应的参数）
     * @param params    sql参数
     * @param isIncludeLogicDelete 数据逻辑删除状态
     * @return true - 存在 <br> false - 不存在
     */
    public boolean exist(Class<? extends BaseDomain> clazz, String condition, Map<String, Object> params, boolean isIncludeLogicDelete) {
        String tableName = getTableInfo(clazz, TableInfo::getTableName);
        String sql = String.format(EXISTS_SQL_TEMPLATE, tableName, condition);
        if (isIncludeLogicDelete) {
            return executeOperation(sql, () -> sqlSession.selectOne(EXIST_MAPPED_STATEMENT_ID, params));
        }
        String logicDeleteSql = getTableInfo(clazz, tableInfo -> tableInfo.getLogicDeleteSql(true, true));
        return executeOperation(sql + logicDeleteSql, () -> sqlSession.selectOne(EXIST_MAPPED_STATEMENT_ID, params));
    }

    /**
     * 执行操作
     *
     * @param sql 执行的sql
     * @param operationSupplier 执行的目标操作
     * @return 目标操作返回值
     * @param <T> 目标操作返回类型
     */
    private <T> T executeOperation(String sql, Supplier<T> operationSupplier) {
        try {
            PostOperationSqlSource.setCheckSql(sql);
            return operationSupplier.get();
        } finally {
            PostOperationSqlSource.cleanSql();
        }
    }

    /**
     * 获取实体类数据表信息
     *
     * @param clazz 实体类class
     * @param tableInfoFunction 获取信息函数
     * @return 获取的目标信息
     * @param <T> 目标信息类型
     */
    private <T> String getTableInfo(Class<? extends BaseDomain> clazz, Function<TableInfo, T> tableInfoFunction) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        return (String) tableInfoFunction.apply(tableInfo);
    }

}
