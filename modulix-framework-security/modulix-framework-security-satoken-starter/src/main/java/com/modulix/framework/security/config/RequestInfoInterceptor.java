package com.modulix.framework.security.config;

import com.modulix.framework.security.api.SecurityUtil;
import com.modulix.framework.security.api.TenantInfoService;
import com.modulix.framework.security.api.common.HttpHeader;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.jspecify.annotations.Nullable;
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

    @Resource
    private TenantInfoService tenantInfoService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler) throws Exception {
        SecurityUtil.setServerName(request.getServerName());

        SecurityUtil.setTenantId(tenantInfoService.getTenantId(request.getServerName()));
        log.info("request url is: {}, request uri is: {}, client-type = {}", request.getRequestURL(), request.getRequestURI(), request.getHeader(HttpHeader.CLIENT_TYPE));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        SecurityUtil.clearServerName();
        SecurityUtil.clearTenantId();
    }
}
