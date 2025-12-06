package com.modulix.framework.security.config;

import com.modulix.framework.security.api.common.HttpHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
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
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler) throws Exception {
        log.info("request url is: {}, request uri is: {}, client-type = {}", request.getRequestURL(), request.getRequestURI(), request.getHeader(HttpHeader.CLIENT_TYPE));
        return true;
    }
}
