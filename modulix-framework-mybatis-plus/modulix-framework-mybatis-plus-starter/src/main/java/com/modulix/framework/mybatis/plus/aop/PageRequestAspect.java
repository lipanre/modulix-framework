package com.modulix.framework.mybatis.plus.aop;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modulix.framework.mybatis.plus.api.annotation.PageRequest;
import com.modulix.framework.mybatis.plus.api.page.PageContextHolder;
import com.modulix.framework.mybatis.plus.api.page.PageRequestInfo;
import com.modulix.framework.web.aip.http.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;

/**
 * pageRequest切面
 *
 * @author lipanre
 */
@Aspect
public class PageRequestAspect {

    /**
     * 默认分页参数
     */
    private static final long DEFAULT_PAGE_NUM = 1;

    /**
     * 默认分页大小
     */
    private static final long DEFAULT_PAGE_SIZE = 10;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 1. 切面@PageRequest注解的方法
     * 2. 获取request对象，获取分页相关参数：pageNum与pageSize
     * 3. 构建page对象
     * 4. 缓存page对象到ThreadLocal中
     * @param pjp ProceedingJoinPoint
     * @param pageRequest 注解对象
     * @return Object
     * @throws Throwable 方法执行出错时抛出
     */
    @SuppressWarnings("unchecked")
    @Around("@annotation(pageRequest)")
    public Object pageRequestAround(ProceedingJoinPoint pjp, PageRequest pageRequest) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (Objects.isNull(servletRequestAttributes)) {
            return pjp.proceed();
        }
        PageRequestInfo<Object> pageRequestInfo = new PageRequestInfo<>();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String pageNum = getParameter(request, pageRequest.pageNum());
        String pageSize = getParameter(request, pageRequest.pageSize());
        if (Objects.isNull(pageNum) && Objects.isNull(pageSize)) {
            pageRequestInfo.setPageable(false);
            return pjp.proceed();
        }
        pageRequestInfo.setPageable(true);
        Page<Object> page = getPage(pageNum, pageSize);
        pageRequestInfo.setPage(page);
        try {
            PageContextHolder.setPageRequestInfo(pageRequestInfo);
            // 这里后面需要将响应内容替换为分页内容
            Object result = pjp.proceed();
            if (!(result instanceof Response<?>) || !(((Response<?>) result).getData() instanceof List<?>)) {
                return result;
            }
            page.setRecords(((Response<List<Object>>)result).getData());
            Response<Object> response = (Response<Object>) result;
            response.setData(page);
            return response;
        } finally {
            // 移除缓存
            PageContextHolder.removePageRequestInfo();
        }
    }

    /**
     * 获取请求参数
     *
     * @param request 请求对象
     * @param parameterName 请求参数名
     * @return 请求参数值
     */
    private String getParameter(HttpServletRequest request, String parameterName) {
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return handleGetParameter(request, parameterName);
        }

        if (request.getMethod().equals(HttpMethod.POST.name())) {
            return handlePostParameter(request, parameterName);
        }
        return null;
    }

    /**
     * 处理post请求参数
     *
     * @param request 请求对象
     * @param parameterName 请求参数名
     * @return 请求参数值
     */
    @SneakyThrows
    private String handlePostParameter(HttpServletRequest request, String parameterName) {
        JsonNode node = objectMapper.readTree(request.getInputStream().readAllBytes());
        return node.get(parameterName).asText();
    }

    /**
     * 处理get请求参数
     *
     * @param request 请求对象
     * @param parameterName 参数名
     * @return 参数值
     */
    private static String handleGetParameter(HttpServletRequest request, String parameterName) {
        return request.getParameter(parameterName);
    }

    /**
     * 构建page对象
     *
     * @param pageNum  请求的pageNum
     * @param pageSize 请求的pageSize
     * @return Page对象
     */
    private static Page<Object> getPage(String pageNum, String pageSize) {
        Page<Object> page = new Page<>();
        if (Objects.isNull(pageNum)) {
            page.setCurrent(DEFAULT_PAGE_NUM);
        } else {
            page.setCurrent(Long.parseLong(pageNum));
        }

        if (Objects.isNull(pageSize)) {
            page.setSize(DEFAULT_PAGE_SIZE);
        } else {
            page.setSize(Long.parseLong(pageSize));
        }
        return page;
    }
}
