package com.modulix.framework.security.service;

import com.modulix.framework.security.api.LoginInfo;
import com.modulix.framework.security.api.auth.UserInfo;

/**
 * @author lipanre
 */
public interface AuthService {

    /**
     * 刷新token信息
     *
     * @param refreshToken refreshToken
     * @return 刷新后的token信息
     */
    LoginInfo refresh(String refreshToken);

    /**
     * 退出登录
     *
     * @param userId 用户id
     * @return 退出结果
     */
    Boolean logout(Long userId, String clientId);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfo getUserInfo(Long userId);
}
