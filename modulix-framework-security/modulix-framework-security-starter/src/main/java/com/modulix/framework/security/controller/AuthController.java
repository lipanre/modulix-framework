package com.modulix.framework.security.controller;

import com.modulix.framework.security.api.LoginInfo;
import com.modulix.framework.security.api.annotation.IgnoreAuth;
import com.modulix.framework.security.service.AuthService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证相关接口
 *
 * @author lipanre
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 刷新token
     * @param loginInfo 登录信息，只需要传refreshToken即可
     * @return 刷新后的token信息
     */
    @IgnoreAuth
    @PostMapping("/refresh")
    public LoginInfo refresh(LoginInfo loginInfo) {
        return authService.refresh(loginInfo.getRefreshToken());
    }
}
