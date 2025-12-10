package com.modulix.framework.security.config;

import cn.dev33.satoken.stp.StpUtil;
import com.modulix.framework.security.api.SecurityService;
import com.modulix.framework.security.api.SecurityServiceFactory;
import com.modulix.framework.security.api.common.HttpHeader;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * 抽象处理器拦截器
 *
 * @author lipanre
 */
@Slf4j
public abstract class AbstractHandlerInterceptor implements HandlerInterceptor {

    @Resource
    private SecurityServiceFactory securityServiceFactory;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        // 如果已经登录才有下面的操作, 如果没有登录则不应该进行下面的操作
        if (!StpUtil.isLogin()) {
            return true;
        }

        String clientType = request.getHeader(HttpHeader.CLIENT_TYPE);
        if (Objects.isNull(clientType)) {
            throw new RuntimeException("缺少client-type信息");
        }
        String serviceName = SecurityService.getSecurityServiceName(clientType);

        // 当前安全上下文service对象
        SecurityService securityService = securityServiceFactory.getSecurityService(serviceName);
        return preHandle(securityService, request, response, handler);
    }


    abstract boolean preHandle(SecurityService securityService, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
