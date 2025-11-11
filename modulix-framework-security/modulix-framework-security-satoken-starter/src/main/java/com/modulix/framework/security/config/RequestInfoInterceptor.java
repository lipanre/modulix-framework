package com.modulix.framework.security.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求信息拦截器
 *
 * @author lipanre
 */

public class RequestInfoInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestInfoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request url is: {}", request.getRequestURL());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
