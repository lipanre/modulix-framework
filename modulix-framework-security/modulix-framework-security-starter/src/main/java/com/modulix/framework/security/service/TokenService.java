package com.modulix.framework.security.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import javax.security.auth.login.CredentialExpiredException;

/**
 * token service
 *
 * @author lipanre
 */
@SuppressWarnings("SpringCacheAnnotationsOnInterfaceInspection")
@CacheConfig(cacheNames = "token")
public interface TokenService {

    /**
     * 创建token
     *
     * @param userId token携带的内容
     * @return token字符串
     */
    @Cacheable(key = "#userId")
    <T> String createAccessToken(T userId);

    /**
     * 获取access token解析器
     *
     * @param token access token
     * @param clazz token携带内容的类型
     * @return access token解析器
     */
    <T> T parseAccessToken(String token, Class<T> clazz);

    /**
     * 创建refresh token
     *
     * @param <T>      refresh token携带内容的类型
     * @param userId   refresh token携带的内容
     * @param clientId
     * @return refresh token字符串
     */
    <T> String createRefreshToken(T userId, String clientId);

    /**
     * 用户用户id
     *
     * @param refreshToken 刷新token
     * @param clazz 用户id类型
     * @return 用户id
     */
    <T> T getUserId(String refreshToken, Class<T> clazz) throws CredentialExpiredException;

    /**
     * 移除token
     *
     * @param userId   用户id
     * @param clientId 客户端id
     * @return 移除结果
     */
    Boolean removeToken(Long userId, String clientId);
}
