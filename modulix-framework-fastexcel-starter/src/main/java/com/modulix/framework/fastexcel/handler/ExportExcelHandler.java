package com.modulix.framework.fastexcel.handler;

import cn.idev.excel.FastExcel;
import cn.idev.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.modulix.framework.common.core.util.SpelUtil;
import com.modulix.framework.fastexcel.annotation.ExportExcel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 导出excel处理器
 *
 * <br>
 * {@code date} 2025/3/18 15:53
 */
public class ExportExcelHandler implements ExportHandler, MethodInterceptor, ApplicationContextAware {

    /**
     * 导出excel文件api路径后缀
     */
    private static final String EXPORT_EXCEL_API_PATH_SUFFIX = "/export/excel";

    private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final String CONTENT_DISPOSITION = "Content-disposition";

    private static final String CONTENT_DISPOSITION_TEMPLATE = "attachment;filename=%s.xlsx";

    public static final LongestMatchColumnWidthStyleStrategy WRITE_HANDLER = new LongestMatchColumnWidthStyleStrategy();
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";


    @Lazy
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private static AutowireCapableBeanFactory autowireCapableBeanFactory;


    @Override
    public boolean isSupport(Method method) {
        return AnnotatedElementUtils.hasAnnotation(method, ExportExcel.class);
    }

    @Override
    public void handle(Object bean, Method method) {
        List<String> apiPaths = scanApi(bean, method);
        if (Objects.isNull(apiPaths)) return;
        apiPaths = apiPaths.stream().map(path -> path + EXPORT_EXCEL_API_PATH_SUFFIX).toList();
        // 动态注册api到容器
        RequestMappingInfo requestMappingInfo = createRequestMappingInfo(apiPaths);
        Object proxy = createProxy(AopUtils.getTargetClass(bean));
        requestMappingHandlerMapping.registerMapping(requestMappingInfo, proxy, method);
    }

    /**
     * 创建请求映射信息
     *
     * @param apiPaths api路径列表
     * @return 请求映射信息列表
     */
    private RequestMappingInfo createRequestMappingInfo(List<String> apiPaths) {
        return RequestMappingInfo.paths(apiPaths.toArray(new String[0])).methods(RequestMethod.GET).build();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 调用目标类的目标方法
        // 获取返回值
        // 利用注解的spel表达式获取数据列表
        // 获取HttpResponse对象 利用fastExcel导出excel文件
        Object result = proxy.invokeSuper(obj, args);
        ExportExcel exportExcel = AnnotatedElementUtils.findMergedAnnotation(method, ExportExcel.class);
        if (Objects.isNull(exportExcel)) {
            return result;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String fileName = SpelUtil.getValue(exportExcel.fileName(), String.class);
        Collection<?> dataSet = SpelUtil.getValue(exportExcel.value(), createSpelVariables(result), Collection.class);
        if (Objects.nonNull(requestAttributes) && Objects.nonNull(requestAttributes.getResponse()) && CollectionUtils.isNotEmpty(dataSet)) {
            HttpServletResponse response = requestAttributes.getResponse();
            response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, CONTENT_DISPOSITION);
            response.setContentType(CONTENT_TYPE);
            response.setHeader(CONTENT_DISPOSITION, String.format(CONTENT_DISPOSITION_TEMPLATE, URLEncoder.encode(fileName, StandardCharsets.UTF_8)));
            FastExcel.write(requestAttributes.getResponse().getOutputStream(), IterableUtils.get(dataSet, 0).getClass())
                    .registerWriteHandler(WRITE_HANDLER)
                    .sheet(exportExcel.sheetName())
                    .doWrite(dataSet);
        }
        return null;
    }

    /**
     * 创建spel变量
     *
     * @param result 返回值
     * @return spel变量列表
     */
    private static Map<String, Object> createSpelVariables(Object result) {
        return Map.of(SpelUtil.RETURN_VARIABLE_NAME, result);
    }

    /**
     * 创建代理对象，用于处理excel文件导出
     *
     * @return 代理对昂
     */
    private Object createProxy(Class<?> superClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superClass);
        enhancer.setCallback(this);
        Object bean = enhancer.create();
        // 将对象依赖注入
        autowireCapableBeanFactory.autowireBean(bean);
        return bean;
    }

    /**
     * 扫描接口路径
     *
     * @param bean   controller对象
     * @param method 方法
     * @return api路径列表
     */
    private static List<String> scanApi(Object bean, Method method) {
        RequestMapping classRequestMapping = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), RequestMapping.class);
        List<String> apiPaths = new ArrayList<>();
        if (Objects.nonNull(classRequestMapping)) {
            for (String path : classRequestMapping.path()) {
                RequestMapping methodRequestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                if (Objects.nonNull(methodRequestMapping) && methodRequestMapping.path().length > 0) {
                    for (String methodPath : methodRequestMapping.path()) {
                        apiPaths.add(path + methodPath);
                    }
                } else {
                    apiPaths.add(path);
                }
            }
        } else {
            RequestMapping methodRequestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            // 如果被注解的方法所在类上与方法上都没有RequestMapping注解，直接退出去
            if (Objects.isNull(methodRequestMapping) || methodRequestMapping.path().length == 0) {
                return null;
            }
            apiPaths.addAll(Arrays.asList(methodRequestMapping.path()));
        }
        return apiPaths;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
    }
}
