package com.modulix.framework.security.controller;

import com.modulix.framework.security.api.LoginInfo;
import com.modulix.framework.security.api.annotation.IgnoreAuth;
import com.modulix.framework.security.api.auth.UserInfo;
import com.modulix.framework.security.api.util.SecurityUtil;
import com.modulix.framework.security.common.AuthConstant;
import com.modulix.framework.security.service.AuthService;
import com.modulix.framework.web.aip.http.Response;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Response<LoginInfo> refresh(@RequestBody LoginInfo loginInfo) {
        return Response.success(authService.refresh(loginInfo.getRefreshToken()));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/user-info")
    public Response<UserInfo> userInfo() {
        return Response.success(authService.getUserInfo(SecurityUtil.getUserId()));
    }

    /**
     * 退出登录
     *
     * @return 退出结果
     */
    @PostMapping("/logout")
    public Response<Boolean> logout(@RequestHeader(AuthConstant.CLIENT_ID) String clientId) {
        return Response.success(authService.logout(SecurityUtil.getUserId(), clientId));
    }
}
