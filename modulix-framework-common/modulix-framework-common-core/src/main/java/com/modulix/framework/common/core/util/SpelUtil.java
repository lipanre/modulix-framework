package com.modulix.framework.common.core.util;


import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * spel表达式工具类
 *
 * @author lipanre
 */
public class SpelUtil {

    public static final String RETURN_VARIABLE_NAME = "result";

    /**
     * spel 解析器
     */
    private static final SpelExpressionParser parser = new SpelExpressionParser();


    /**
     * 获取spel解析结果
     *
     * @param spel  spel表达式
     * @param clazz 返回值类型
     * @param <T>   解析结果类型
     * @return 解析结果
     */
    public static <T> T getValue(String spel, Class<T> clazz) {
        Expression expression = parseExpression(spel);
        return expression.getValue(clazz);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel spel表达式
     * @param variables 变脸
     * @return 解析结果
     * @param <T> 目标类型
     */
    public static<T> T getValue(String spel, Map<String, Object> variables) {
        return getValue(spel, null, null, variables);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel spel表达式
     * @param variables 变脸
     * @param clazz 目标class
     * @return 解析结果
     * @param <T> 目标类型
     */
    public static<T> T getValue(String spel, Map<String, Object> variables, Class<T> clazz) {
        return getValue(spel, clazz, null, variables);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel       spel表达式
     * @param clazz      返回值类型
     * @param rootObject 根对象
     * @param <T>        解析结果类型
     * @return 解析结果
     */
    public static <T> T getValue(String spel, Class<T> clazz, Object rootObject) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(rootObject);
        return getValue(spel, clazz, evaluationContext);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel       spel表达式
     * @param clazz      返回值类型
     * @param rootObject 根对象
     * @param <T>        解析结果类型
     * @return 解析结果
     */
    public static <T> T getValue(String spel, Class<T> clazz, Object rootObject, Map<String, Object> variables) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(rootObject);
        evaluationContext.setVariables(variables);
        return getValue(spel, clazz, evaluationContext);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel          spel表达式
     * @param clazz         返回值类型
     * @param parserContext 解析器上下文
     * @param rootObject    根对象
     * @param <T>           解析结果类型
     * @return 解析结果
     */
    public static <T> T getValue(String spel, Class<T> clazz, ParserContext parserContext, Object rootObject, Map<String, Object> variables) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(rootObject);
        evaluationContext.setVariables(variables);
        return getValue(spel, clazz, parserContext, evaluationContext);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel              spel表达式
     * @param evaluationContext 表达式上下文
     * @param clazz             返回值类型
     * @param <T>               解析结果类型
     * @return 解析结果
     */
    public static <T> T getValue(String spel, Class<T> clazz, EvaluationContext evaluationContext) {
        Expression expression = parseExpression(spel);
        return expression.getValue(evaluationContext, clazz);
    }

    /**
     * 获取spel解析结果
     *
     * @param spel              spel表达式
     * @param parserContext     解析器上下文
     * @param evaluationContext 表达式上下文
     * @param clazz             返回值类型
     * @param <T>               解析结果类型
     * @return 解析结果
     */
    public static <T> T getValue(String spel, Class<T> clazz, ParserContext parserContext, EvaluationContext evaluationContext) {
        Expression expression = parseExpression(spel, parserContext);
        return expression.getValue(evaluationContext, clazz);
    }

    /**
     * 解析spel表达式
     *
     * @param spel spel表达式
     * @return 解析结果
     */
    public static Expression parseExpression(final String spel) {
        return parseExpression(spel, ParserContext.TEMPLATE_EXPRESSION);
    }

    /**
     * 解析spel表达式
     *
     * @param spel          spel表达式
     * @param parserContext 解析器上下文
     * @return 解析结果
     */
    public static Expression parseExpression(String spel, ParserContext parserContext) {
        return parser.parseExpression(spel, parserContext);
    }
}
