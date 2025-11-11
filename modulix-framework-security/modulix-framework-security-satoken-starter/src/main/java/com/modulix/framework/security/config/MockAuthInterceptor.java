package com.modulix.framework.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 模拟登录拦截器
 *
 * @author lipanre
 */
public class MockAuthInterceptor implements HandlerInterceptor {

    @Resource
    private SaTokenConfig saTokenConfig;

    @Resource
    private SaTokenConfigProperties saTokenConfigProperties;


    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = request.getHeader(saTokenConfig.getTokenName());
        if (StringUtils.startsWithIgnoreCase(token, saTokenConfigProperties.getMockTokenPrefix())) {
            Long userId = Long.parseLong(token.substring(saTokenConfigProperties.getMockTokenPrefix().length()));
            // 直接模拟登录
            StpUtil.login(userId);
        }
        return true;
    }
}
