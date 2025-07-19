package com.modulix.framework.security.processor;

import com.modulix.framework.security.api.annotation.IgnoreAuth;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 忽略认证post-processor
 *
 * <br>
 * {@code date} 2025/5/14 18:57
 */
@Getter
public class IgnoreAuthProcessor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final List<PathPatternRequestMatcher> matchers = new ArrayList<>();

    private final String errorPath;

    public IgnoreAuthProcessor(String errorPath) {
        this.errorPath = errorPath;
    }


    public void init() {
        // 扫描所有带有IgnoreAuth注解的接口
        // 1. 注解在类上
        Map<String, Object> ignoreAuthControllers = applicationContext.getBeansWithAnnotation(IgnoreAuth.class);
        ignoreAuthControllers.values().stream().map(Object::getClass).flatMap(this::scanClass).forEach(matchers::add);

        // 2. 注解在方法上
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RestController.class);
        controllers.values().stream()
                .filter(controller -> !ignoreAuthControllers.containsValue(controller))
                .map(Object::getClass)
                .flatMap(controllerClass -> scanClassMethods(controllerClass, false)).forEach(matchers::add);
        // 放行/error接口，让系统异常时可以正常提示
        matchers.add(createMatcher(errorPath));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }


    private Stream<PathPatternRequestMatcher> scanClass(Class<?> controllerClass) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(controllerClass, RequestMapping.class);
        if (Objects.isNull(requestMapping)) {
            // 扫描类下面的方法上有没有忽略认证的注解
            return scanClassMethods(controllerClass, true);
        }
        List<PathPatternRequestMatcher> matchers = new ArrayList<>();
        for (String path : requestMapping.path()) {
            matchers.add(createMatcher(path + "/**"));
        }
        return matchers.stream();
    }

    /**
     * 扫描类下面的方法上有没有忽略认证的注解
     *
     * @param controllerClass 控制器类
     * @return 忽略认证请求匹配器
     */
    private Stream<PathPatternRequestMatcher> scanClassMethods(Class<?> controllerClass, boolean isClassIgnore) {
        List<PathPatternRequestMatcher> matchers = new ArrayList<>();

        RequestMapping classRequestMapping = AnnotatedElementUtils.findMergedAnnotation(controllerClass, RequestMapping.class);
        if (Objects.isNull(classRequestMapping)) {
            for (Method method : controllerClass.getMethods()) {
                if (!isClassIgnore && !AnnotatedElementUtils.hasAnnotation(method, IgnoreAuth.class)) {
                    continue;
                }
                RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                if (Objects.isNull(requestMapping)) continue;
                for (String path : requestMapping.path()) {
                    for (RequestMethod httpMethod : requestMapping.method()) {
                        matchers.add(createMatcher(path, httpMethod.asHttpMethod()));
                    }
                }
            }
            return matchers.stream();
        }
        for (Method method : controllerClass.getDeclaredMethods()) {
            if (!isClassIgnore && !AnnotatedElementUtils.hasAnnotation(method, IgnoreAuth.class)) {
                continue;
            }
            RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            if (Objects.isNull(requestMapping)) continue;
            for (String classPath : classRequestMapping.path()) {
                for (String path : requestMapping.path()) {
                    for (RequestMethod httpMethod : requestMapping.method()) {
                        matchers.add(createMatcher(classPath +path, httpMethod.asHttpMethod()));
                    }
                }
            }
        }
        return matchers.stream();
    }

    private PathPatternRequestMatcher createMatcher(String path, HttpMethod httpMethod) {
        PathPatternRequestMatcher.Builder builder = PathPatternRequestMatcher.withDefaults();
        return builder.matcher(httpMethod, path);
    }

    private PathPatternRequestMatcher createMatcher(String path) {
        PathPatternRequestMatcher.Builder builder = PathPatternRequestMatcher.withDefaults();
        return builder.matcher(path);
    }
}
