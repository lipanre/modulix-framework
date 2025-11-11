package com.modulix.framework.mybatis.plus.aop;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.modulix.framework.common.core.function.Consumer3;
import com.modulix.framework.common.core.util.SpelUtil;
import com.modulix.framework.mybatis.plus.api.annotation.*;
import com.modulix.framework.mybatis.plus.manager.PostOperationManager;
import com.modulix.framework.web.aip.exception.BizException;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 检查切面
 *
 * @author lipanre
 */
@Aspect
public class DataBaseOperationAspect {

    private static final String SQL_SPEL_PARAM_PATTERN = "#\\{(.*?)}";

    private static final Pattern pattern = Pattern.compile(SQL_SPEL_PARAM_PATTERN);

    /**
     * spel 参数名目标
     */
    private static final String SPEL_PARAM_NAME_TEMPLATE = "p%d";
    private static final String SPEL_SECOND_PARAM_NAME_TEMPLATE = "%s_%d";
    private static final String SQL_REPLACEMENT_TEMPLATE = "#{%s}";

    @Resource
    private PostOperationManager postOperationManager;


    @Around("@annotation(checkExists)")
    public Object checkExists(ProceedingJoinPoint pjp, CheckExists checkExists) throws Throwable {
        for (CheckExist checkExist : checkExists.value()) {
            doCheck(pjp, checkExist.condition(), checkExist.notExistNotify(),
                    (condition, params) -> postOperationManager.notExist(checkExist.value(), condition, params, checkExist.includeDeleted()));
        }
        return pjp.proceed();
    }

    @Around("@annotation(checkExist)")
    public Object checkExist(ProceedingJoinPoint pjp, CheckExist checkExist) throws Throwable {
        doCheck(pjp, checkExist.condition(), checkExist.notExistNotify(),
                (condition, params) -> postOperationManager.notExist(checkExist.value(), condition, params, checkExist.includeDeleted()));
        return pjp.proceed();
    }

    @Around("@annotation(checkNotExists)")
    public Object checkNotExist(ProceedingJoinPoint pjp, CheckNotExists checkNotExists) throws Throwable {
        for (CheckNotExist checkNotExist : checkNotExists.value()) {
            doCheck(pjp, checkNotExist.condition(), checkNotExist.existNotify(),
                    (condition, params) -> postOperationManager.exist(checkNotExist.value(), condition, params, checkNotExist.includeDeleted()));
        }
        return pjp.proceed();
    }

    @Around("@annotation(checkNotExist)")
    public Object checkNotExist(ProceedingJoinPoint pjp, CheckNotExist checkNotExist) throws Throwable {
        doCheck(pjp, checkNotExist.condition(), checkNotExist.existNotify(),
                (condition, params) -> postOperationManager.exist(checkNotExist.value(), condition, params, checkNotExist.includeDeleted()));
        return pjp.proceed();
    }

    @Around("@annotation(deleteOperation)")
    public Object deleteOperation(ProceedingJoinPoint pjp, DeleteOperation deleteOperation) throws Throwable {
        doOperation(pjp, deleteOperation.condition(), (transformedCondition, params, methodParamMap) -> postOperationManager.delete(deleteOperation.value(), transformedCondition, params));
        return pjp.proceed();
    }

    private void doCheck(ProceedingJoinPoint pjp,
                         String condition,
                         String notifyMessage,
                         BiPredicate<String, Map<String, Object>> checkPredicate) {
        doOperation(pjp, condition, (checkCondition, params, methodParamMap) -> {
            if (checkPredicate.test(checkCondition, params)) {
                throw new BizException(SpelUtil.getValue(notifyMessage, methodParamMap, String.class));
            }
        });
    }

    /**
     * 执行操作
     *
     * @param pjp 切面JoinPoint对象
     * @param condition 操作条件
     * @param operation 操作
     */
    private void doOperation(ProceedingJoinPoint pjp,
                             String condition,
                             Consumer3<String, Map<String, Object>, Map<String, Object>> operation) {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Map<String, Object> methodParamMap = zipParams(pjp, signature);


        // 转换条件，并生成参数
        Matcher matcher = pattern.matcher(condition);
        int start = 0;
        Map<String, Object> paramMap = new HashMap<>();

        // 转换条件中的spel，将非对象属性spel表达式的值直接注入条件中
        // 对象属性spel表达式特征: #{#对象.属性} #{#字段名}
        // 非对象属性spel表达式特征: #{1 + 2 + 4} #{T(com.xxxx.Enum.xxx)}
        for (int i = 0; matcher.find(start); i++) {
            condition = buildCondition(condition, i, matcher, paramMap, methodParamMap);
            start = matcher.end();
        }
        operation.accept(condition, paramMap, methodParamMap);
    }

    /**
     * 将参数转为map
     *
     * @param pjp       切面JoinPoint对象
     * @param signature 方法签名对象
     * @return 参数map
     */
    private static Map<String, Object> zipParams(ProceedingJoinPoint pjp, MethodSignature signature) {
        String[] parameterNames = signature.getParameterNames();
        Object[] parameterValues = pjp.getArgs();

        Map<String, Object> methodParamMap = new HashMap<>();
        IntStream.range(0, parameterNames.length).forEach(i -> methodParamMap.put(parameterNames[i], parameterValues[i]));
        return methodParamMap;
    }

    private static String buildCondition(String condition, int paramIndex, Matcher matcher, Map<String, Object> paramMap, Map<String, Object> methodParamMap) {
        Object param = SpelUtil.getValue(matcher.group(), methodParamMap);
        String paramName = String.format(SPEL_PARAM_NAME_TEMPLATE, paramIndex);
        if (param instanceof List<?> params) {
            String paramNames = IntStream.range(0, params.size()).boxed()
                    .map(i -> buildSecondParam(paramMap, params, i, paramName))
                    .map(DataBaseOperationAspect::formatSecondParamName)
                    .collect(Collectors.joining(Constants.COMMA));
            return condition.replaceFirst(Pattern.quote(matcher.group()), paramNames);
        }
        condition = condition.replaceFirst(Pattern.quote(matcher.group(1)), paramName);
        paramMap.put(paramName, param);
        return condition;
    }

    private static String buildSecondParam(Map<String, Object> paramMap, List<?> params, Integer i, String paramName) {
        String secondParamName = String.format(SPEL_SECOND_PARAM_NAME_TEMPLATE, paramName, i);
        paramMap.put(secondParamName, params.get(i));
        return secondParamName;
    }

    private static String formatSecondParamName(String p) {
        return String.format(SQL_REPLACEMENT_TEMPLATE, p);
    }

}
