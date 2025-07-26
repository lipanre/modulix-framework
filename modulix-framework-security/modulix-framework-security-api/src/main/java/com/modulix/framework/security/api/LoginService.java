package com.modulix.framework.security.api;

/**
 * 登录service
 *
 * @author lipanre
 */
public interface LoginService {

    /**
     * 增加登录信息
     *
     * @param userId 用户id
     * @param refreshToken refreshToken
     * @param <T> id类型
     */
    <T> void recordLoginInfo(T userId, String refreshToken);

    /**
     * 获取用户id字符串表示
     *
     * @param refreshToken 刷新token
     * @return 用户id字符串表示
     */
    String getUserId(String refreshToken);
}
