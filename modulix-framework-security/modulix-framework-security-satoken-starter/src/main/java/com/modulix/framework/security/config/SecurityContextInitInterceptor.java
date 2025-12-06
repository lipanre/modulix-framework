package com.modulix.framework.security.config;

import cn.dev33.satoken.stp.StpUtil;
import com.modulix.framework.security.api.SecurityService;
import com.modulix.framework.security.api.SecurityUtil;
import com.modulix.framework.security.api.common.HttpHeader;
import com.modulix.framework.security.api.info.SecurityUser;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * security上下文拦截器
 *
 * @author lipanre
 */
public class SecurityContextInitInterceptor extends AbstractHandlerInterceptor {

    @Override
    public boolean preHandle(SecurityService securityService, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserId(StpUtil.getLoginIdAsLong());
        securityUser.setDataScopes(securityService.getCurrentDataScopes());
        securityUser.setRoleCodes(securityService.listCurrentRoleCodes());
        securityUser.setClientType(request.getHeader(HttpHeader.CLIENT_TYPE));
        SecurityUtil.setCurrentUser(securityUser);
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) throws Exception {
        // 清除缓存
        SecurityUtil.clearCurrentUser();
    }
}
