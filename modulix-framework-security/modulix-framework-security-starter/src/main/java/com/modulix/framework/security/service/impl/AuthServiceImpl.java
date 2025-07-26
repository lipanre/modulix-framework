package com.modulix.framework.security.service.impl;

import com.modulix.framework.security.api.LoginInfo;
import com.modulix.framework.security.api.auth.Authentication;
import com.modulix.framework.security.api.auth.AuthenticationContext;
import com.modulix.framework.security.api.auth.AuthenticationService;
import com.modulix.framework.security.api.auth.UserInfo;
import com.modulix.framework.security.auth.AuthenticationServiceFactory;
import com.modulix.framework.security.config.TokenConfigProperties;
import com.modulix.framework.security.service.AuthService;
import com.modulix.framework.security.service.TokenService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lipanre
 */
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private TokenService tokenService;

    @Resource
    private TokenConfigProperties tokenConfigProperties;

    @Resource
    private AuthenticationServiceFactory authenticationServiceFactory;


    @Override
    @SneakyThrows
    public LoginInfo refresh(String refreshToken) {
        LoginInfo loginInfo = new LoginInfo();
        Long userId = tokenService.getUserId(refreshToken, Long.class);
        loginInfo.setToken(tokenService.createAccessToken(userId));
        loginInfo.setHeader(tokenConfigProperties.getHeader());
        loginInfo.setExpire(tokenConfigProperties.getAccessExpiration().getSeconds());
        return loginInfo;
    }

    @Override
    public Boolean logout(Long userId, String clientId) {
        return tokenService.removeToken(userId, clientId);
    }

    @Override
    public UserInfo getUserInfo(Long userId) {
        AuthenticationService<Authentication> authenticationService = AuthenticationContext.getAuthenticationService();
        return authenticationService.getUserInfo(userId);
    }
}
