package com.modulix.framework.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.stp.StpUtil;
import com.modulix.framework.security.api.SecurityService;
import com.modulix.framework.security.api.SecurityServiceFactory;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.util.StringUtils;

/**
 * 模拟登录拦截器
 *
 * @author lipanre
 */
public class MockAuthInterceptor extends AbstractHandlerInterceptor {

    @Resource
    private SaTokenConfig saTokenConfig;

    @Resource
    private SaTokenConfigProperties saTokenConfigProperties;

    /**
     * 需要知道是哪个客户端发起的请求,然后调用对应的service来获取登录用户的信息
     */
    @Resource
    private SecurityServiceFactory securityServiceFactory;

    @Override
    public boolean preHandle(SecurityService securityService, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String token = request.getHeader(saTokenConfig.getTokenName());
        if (StringUtils.startsWithIgnoreCase(token, saTokenConfigProperties.getMockTokenPrefix())) {
            Long userId = Long.parseLong(token.substring(saTokenConfigProperties.getMockTokenPrefix().length()));
            // 如果已经登录了,就直接创建一个登录会话数据,避免重复登录
            if (StpUtil.isLogin(userId)) {
                StpUtil.createLoginSession(userId, securityService.createLoginParameter(userId));
                return true;
            }
            // 直接模拟登录
            StpUtil.login(userId, securityService.createLoginParameter(userId));
        }
        return true;
    }


}
