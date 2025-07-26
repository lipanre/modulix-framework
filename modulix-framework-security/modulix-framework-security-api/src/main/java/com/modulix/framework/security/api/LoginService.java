package com.modulix.framework.security.api;

import java.time.Duration;

/**
 * 登录service
 *
 * @author lipanre
 */
public interface LoginService {

    /**
     * 增加登录信息
     *
     * @param userId            用户id
     * @param refreshToken      refreshToken
     * @param refreshExpiration refreshToken过期时间
     * @param clientId 客户端id
     */
    void recordLoginInfo(String userId, String refreshToken, Duration refreshExpiration, String clientId);

    /**
     * 获取用户id字符串表示
     *
     * @param refreshToken 刷新token
     * @return 用户id字符串表示
     */
    String getUserId(String refreshToken);

    /**
     * 移除登录信息
     *
     * @param userId   用户id
     * @param clientId 客户端id
     * @return 移除结果
     */
    Boolean removeLoginInfo(Long userId, String clientId);
}
