package com.modulix.framework.web.service.impl;


import com.modulix.framework.web.api.annotation.ReadRepeatable;
import com.modulix.framework.web.service.HttpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * <br>
 * {@code date} 2025/4/14 16:06
 */
@Slf4j
public class HttpServiceImpl implements HttpService, BeanPostProcessor {

    /**
     * 可重复读请求的url列表
     * <br>
     * 可能包含url ant模式
     */
    private static final List<String> repeatableReadRequestUrls = new ArrayList<>();

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public boolean isNeedCacheBody(HttpServletRequest request) {
        // get请求不需要缓存请求体
        if (Objects.equals(request.getMethod(), HttpMethod.GET.name())) {
            return false;
        }
        for (String urlPattern : repeatableReadRequestUrls) {
            if (antPathMatcher.match(urlPattern, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> controllerClass = AopUtils.getTargetClass(bean);
        RestController restController = AnnotatedElementUtils.findMergedAnnotation(controllerClass, RestController.class);
        // 1. 如果不是controller不做任何处理
        if (Objects.isNull(restController)) {
            return bean;
        }
        // 2. 如果是controller
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(controllerClass, RequestMapping.class);
        if (Objects.isNull(requestMapping)) {
            // 2.2 controller上面没有配置请求路径
            // 2.2.1 扫描controller下面的所有请求方法，判断方法上是否有RepeatableReadRequest注解，并且非仅get请求，如果二者都满足，那么这个url的请求体需要被缓存, 否则不做任何处理
            scanClass(controllerClass, Function.identity());
        } else {
            // 2.1 controller上面配置了请求路径
            // 2.1.1 扫描controller下面的所有方法，判断方法上是否有RepeatableReadRequest注解，并且非仅get请求，如果是则 类上url + 方法上url 的请求体需要被缓存，否则不做任何处理
            scanClass(controllerClass, methodPaths -> {
                List<String> parentPaths = Arrays.asList(requestMapping.path());
                if (CollectionUtils.isEmpty(parentPaths)) {
                    return methodPaths;
                }
                if (CollectionUtils.isEmpty(methodPaths)) {
                    return parentPaths;
                }
                return parentPaths.stream().flatMap(parentPath -> methodPaths.stream().map(path -> parentPath + path)).toList();
            });
        }
        return bean;
    }

    private void scanClass(Class<?> controllerClass, Function<List<String>, List<String>> strategy) {
        for (Method method : controllerClass.getMethods()) {
            RequestMapping methodRequestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            if (Objects.isNull(methodRequestMapping) || isOnlyGet(methodRequestMapping.method())) {
                continue;
            }
            if (AnnotatedElementUtils.hasAnnotation(method, ReadRepeatable.class)) {
                repeatableReadRequestUrls.addAll(strategy.apply(Arrays.asList(methodRequestMapping.path())));
            }
        }
    }

    private boolean isOnlyGet(RequestMethod[] method) {
        return method.length == 1 && method[0] == RequestMethod.GET;
    }

}
