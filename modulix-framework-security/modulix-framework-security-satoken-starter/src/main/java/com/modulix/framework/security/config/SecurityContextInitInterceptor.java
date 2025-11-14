package com.modulix.framework.security.config;

import cn.dev33.satoken.stp.StpUtil;
import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import com.modulix.framework.security.api.SecurityUtil;
import com.modulix.framework.security.api.common.HttpHeader;
import com.modulix.framework.security.api.info.SecurityUser;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * security上下文拦截器
 *
 * @author lipanre
 */
public class SecurityContextInitInterceptor implements HandlerInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        if (StpUtil.isLogin()) {
            SecurityUser securityUser = new SecurityUser();
            securityUser.setUserId(StpUtil.getLoginIdAsLong());
            securityUser.setDataScopes((Set<DataScope>) StpUtil.getExtra(SecurityUser.Fields.dataScopes));
            securityUser.setRoleCodes((Set<String>) StpUtil.getExtra(SecurityUser.Fields.roleCodes));
            securityUser.setClientType(request.getHeader(HttpHeader.CLIENT_TYPE));
            SecurityUtil.setCurrentUser(securityUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) throws Exception {
        // 清除缓存
        SecurityUtil.clearCurrentUser();
    }
}
