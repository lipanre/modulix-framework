package com.modulix.framework.mybatis.plus.permission;

import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import com.google.common.collect.HashBasedTable;
import com.modulix.framework.mybatis.plus.api.annotation.DataPermissionHandler;
import com.modulix.framework.security.api.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.schema.Table;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 数据权限处理器
 *
 * @author lipanre
 */
@Slf4j
@Component
@NoArgsConstructor
public class DataPermissionHandlerImpl implements MultiDataPermissionHandler, BeanPostProcessor {

    /**
     * 数据权限类型 + 表名 ==> 数据权限处理器定义
     */
    private static final HashBasedTable<@NonNull String, @NonNull String, @NonNull DataPermissionHandlerDefinition> dataPermissionTable = HashBasedTable.create();

    @Resource
    private List<DataPermissionParameterResolver> parameterResolvers;


    @SuppressWarnings("unchecked")
    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        // 循环当前用户的数据权限列表
        // 判断指定类型的数据权限是否需要对指定的表进行数据权限过滤
        // 如果需要过滤则通过DataPermissionHandlerDefinition来构建OR表达式
        // 如果没有登录就不走权限过滤逻辑
        if (!SecurityUtil.isLogin() || SecurityUtil.isSuperAdmin() || SecurityUtil.isAdmin() || Objects.isNull(SecurityUtil.getCurrentDataScopes())) {
            return null;
        }


        if (isIgnore(mappedStatementId)) return null;

        List<Expression> expressions = new ArrayList<>();
        for (String dataPermission : SecurityUtil.getCurrentDataScopes()) {
            DataPermissionHandlerDefinition dataPermissionHandlerDefinition = dataPermissionTable.get(dataPermission, table.getName());
            if (Objects.isNull(dataPermissionHandlerDefinition)) continue;
            Expression expression = invokeHandler(dataPermissionHandlerDefinition, table);
            if (Objects.isNull(expression)) continue;
            expressions.add(new ParenthesedExpressionList<>(expression));
        }
        if (CollectionUtils.isEmpty(expressions)) return null;
        Optional<Expression> dataPermissionExpressionOpt = expressions.stream().reduce(OrExpression::new);
        // 如果数据权限没有构建任何表达式，则不添加任何过滤条件
        return dataPermissionExpressionOpt.map(ParenthesedExpressionList::new).orElse(null);
    }

    private static boolean isIgnore(String mappedStatementId) {
        IgnoreStrategy ignoreStrategy = InterceptorIgnoreHelper.getIgnoreStrategy(mappedStatementId);

        // 判断方法上是否有忽略注解
        if (Objects.nonNull(ignoreStrategy) && ignoreStrategy.getDataPermission()) {
            return true;
        }

        // 判断类上是否有忽略注解
        IgnoreStrategy classStrategy = InterceptorIgnoreHelper.getIgnoreStrategy(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
        return Objects.nonNull(classStrategy) && classStrategy.getDataPermission();
    }

    private Expression invokeHandler(DataPermissionHandlerDefinition dataPermissionHandlerDefinition, Table table) {
        Object bean = dataPermissionHandlerDefinition.getBean();
        Method method = dataPermissionHandlerDefinition.getMethod();
        method.setAccessible(true);
        // 方法参数转换
        Object[] parameters = resolveParameters(dataPermissionHandlerDefinition.getParameters(), table);
        Expression generateExpression = null;
        try {
            generateExpression = (Expression) method.invoke(bean, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return generateExpression;
    }

    private Object[] resolveParameters(MethodParameter[] parameters, Table table) {
        List<Object> parameterValues = new ArrayList<>();
        for (MethodParameter parameter : parameters) {
            // 解析方法参数，获得方法参数对象
            for (DataPermissionParameterResolver resolver : parameterResolvers) {
                if (resolver.isSupport(parameter)) {
                    parameterValues.add(resolver.resolve(parameter, table));
                }
            }
        }
        return parameterValues.toArray();
    }

    /**
     * 扫描指定bean中的所有方法，判断是否存在数据权限处理方法
     */
    @SuppressWarnings("NullableProblems")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> componentClass = bean.getClass();
        for (Method method : componentClass.getDeclaredMethods()) {
            DataPermissionHandler dataPermissionHandler = AnnotatedElementUtils.getMergedAnnotation(method, DataPermissionHandler.class);
            if (Objects.isNull(dataPermissionHandler)) continue;
            DataPermissionHandlerDefinition dataPermissionHandlerDefinition = createDefinition(bean, method);
            for (String tableName : dataPermissionHandler.tables()) {
                dataPermissionTable.put(dataPermissionHandler.type(), tableName, dataPermissionHandlerDefinition);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private DataPermissionHandlerDefinition createDefinition(Object bean, Method method) {
        MethodParameter[] parameters = new MethodParameter[method.getParameters().length];
        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = new MethodParameter(method, i);
        }
        return new DataPermissionHandlerDefinition(bean, method, parameters);
    }
}