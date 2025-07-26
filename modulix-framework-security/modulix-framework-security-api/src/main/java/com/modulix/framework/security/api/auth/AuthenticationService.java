package com.modulix.framework.security.api.auth;

import java.util.function.Supplier;

/**
 * authentication service
 *
 * @author lipanre
 */
public interface AuthenticationService<T extends Authentication> {

    /**
     * 加载认证信息
     *
     * @param fieldSupplier 认证唯一值加载函数
     * @return 认证信息
     * @param <F> 认证唯一值类型
     */
    <F> T loadAuthentication(Supplier<F> fieldSupplier);

    /**
     * 通过用户id获取用户认证信息
     *
     * @param userId 用户id
     * @return 认证信息
     */
    T loadAuthenticationById(Long userId);

    /**
     * 获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    UserInfo getUserInfo(Long userId);
}
