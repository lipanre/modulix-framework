package com.modulix.framework.security.handler;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.modulix.framework.security.api.LoginInfo;
import com.modulix.framework.security.config.TokenConfigProperties;
import com.modulix.framework.security.service.TokenService;
import com.modulix.framework.web.aip.constant.ResponseCode;
import com.modulix.framework.web.aip.http.Response;
import com.modulix.framework.web.aip.util.WebUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 全局认证成功处理器
 *
 * @author lipanre
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Resource
    private TokenConfigProperties tokenConfigProperties;


    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication instanceof com.modulix.framework.security.api.auth.Authentication auth) {
            LoginInfo loginInfo = new LoginInfo();
            String clientId = NanoIdUtils.randomNanoId();
            loginInfo.setToken(tokenService.createAccessToken(auth.getId()));
            loginInfo.setRefreshToken(tokenService.createRefreshToken(auth.getId(), clientId));
            loginInfo.setExpire(tokenConfigProperties.getAccessExpiration().getSeconds());
            loginInfo.setHeader(tokenConfigProperties.getHeader());
            loginInfo.setClientId(clientId);
            WebUtil.response(response, Response.success(loginInfo));
            return;
        }
        WebUtil.response(response, Response.fail(ResponseCode.LOGIN_AGAIN, "登录异常，请稍后重试"));
    }
}
