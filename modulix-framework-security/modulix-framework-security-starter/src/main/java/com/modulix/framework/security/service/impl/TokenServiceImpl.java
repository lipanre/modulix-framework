package com.modulix.framework.security.service.impl;

import com.modulix.framework.common.core.util.JsonUtil;
import com.modulix.framework.security.config.TokenConfigProperties;
import com.modulix.framework.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;

import java.security.Key;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.Date;

/**
 * token serviceImpl
 *
 * @author lipanre
 */
public class TokenServiceImpl implements TokenService {

    @Resource
    private TokenConfigProperties tokenConfigProperties;


    @Override
    public <T> String createAccessToken(T userId) {
        String accessSubject = JsonUtil.toJson(userId);
        return createToken(accessSubject, tokenConfigProperties.getAccessTokenSecretKey(), tokenConfigProperties.getAccessExpiration());
    }

    @Override
    public <T> T parseAccessToken(String token, Class<T> clazz) {
        String tokenJson = null;
        if (token.startsWith(tokenConfigProperties.getMonitorPrefix())) {
            // 增加一个测试token逻辑，方便测试
            // 以某一个字符串开头的token，后面跟上用户id，则直接模拟这个用户已经登录
            tokenJson = token.substring(tokenConfigProperties.getMonitorPrefix().length());
        } else {
            tokenJson = ((Claims) Jwts.parser().verifyWith(tokenConfigProperties.getAccessTokenSecretKey()).build().parse(token).getPayload()).getSubject();
        }
        return JsonUtil.fromJson(tokenJson, clazz);
    }

    @Override
    @SneakyThrows
    public <T> String createRefreshToken(T userId) {
        String refreshSubject = JsonUtil.toJson(userId);
        return createToken(refreshSubject, tokenConfigProperties.getRefreshTokenSecretKey(), tokenConfigProperties.getRefreshExpiration());
    }

    @Override
    public <T> T parseRefreshToken(String token, Class<T> clazz) throws InvalidKeySpecException {
        return JsonUtil.fromJson(((Claims) Jwts.parser().verifyWith(tokenConfigProperties.getRefreshTokenPublicKey()).build().parse(token).getPayload()).getSubject(), clazz);
    }


    /**
     * 创建token
     *
     * @param content token携带的内容
     * @param key 签名密钥
     * @param expire 过期时间
     * @return token
     */
    private String createToken(String content, Key key, Duration expire) {
        return Jwts.builder()
                .subject(content)
                .expiration(getExpiration(expire))
                .issuedAt(new Date())
                .signWith(key)
                .compact();
    }

    /**
     * 获取过期时间
     *
     * @param expireAt 过期时间
     * @return 过期时间
     */
    private Date getExpiration(Duration expireAt) {
        return new Date(System.currentTimeMillis() + expireAt.toMillis());
    }


}
