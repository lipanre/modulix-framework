package com.modulix.framework.security.service;

import com.modulix.framework.security.api.LoginInfo;

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
}
