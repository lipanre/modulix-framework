package com.modulix.framework.security.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.security.spec.InvalidKeySpecException;

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
     * @param <T>     refresh token携带内容的类型
     * @param userId refresh token携带的内容
     * @return refresh token字符串
     */
    <T> String createRefreshToken(T userId);

    /**
     * 获取refresh token解析器
     *
     * @param token refresh token
     * @param clazz refresh token携带内容的类型
     * @return refresh token解析器
     * @param <T> refresh token携带内容的类型
     * @throws InvalidKeySpecException refresh token解析失败
     */
    <T> T parseRefreshToken(String token, Class<T> clazz) throws InvalidKeySpecException;
}
