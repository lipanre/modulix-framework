package com.modulix.framework.security.config;

import com.modulix.framework.security.util.RSAUtil;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;

/**
 * {@code description}
 * token配置
 *
 * <br>
 * {@code date} 2025/2/6 10:12
 */
@Data
@Validated
public class TokenConfigProperties {

    /**
     * token header
     * <br>
     * default value: "Authorization"
     * 前端请求头部存放token的key
     */
    private String header = "Authorization";

    /**
     * 模拟token前缀
     */
    private String monitorPrefix = "m9L{!{hd@9YHpK=";

    /**
     * access token secret
     * <br>
     * 使用sha*算法加密，密钥为accessSecret
     */
    @NotEmpty(message = "accessToken secret 不能为空")
    private String accessSecret;

    /**
     * access token过期时间
     * <br>
     * 默认10分钟
     */
    private Duration accessExpiration = Duration.ofMinutes(10);

    /**
     * refresh token rsa 私钥
     */
    @NotNull(message = "请配置refreshToken rsa私钥文件地址")
    private Resource refreshSecretPrivate;

    /**
     * refresh token rsa 公钥
     */
    @NotNull(message = "请配置refreshToken rsa公钥文件地址")
    private Resource refreshSecretPublic;

    /**
     * refreshToken过期时间
     * <br>
     * 默认30天
     */
    private Duration refreshExpiration = Duration.ofDays(30);

    /**
     * 获取access token secret key
     *
     * @return access token secret key
     */
    public SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取refresh token rsa 私钥
     *
     * @return refresh token rsa 私钥
     * @throws InvalidKeySpecException 当私钥格式不正确时抛出
     */
    @SneakyThrows
    public Key getRefreshTokenSecretKey() {
        String contentAsString = refreshSecretPrivate.getContentAsString(StandardCharsets.UTF_8);
        return RSAUtil.getPrivateKey(StringUtils.deleteWhitespace(contentAsString));
    }

    /**
     * 获取refresh token rsa 公钥
     *
     * @return refresh token rsa 公钥
     * @throws InvalidKeySpecException 当公钥格式不正确时抛出
     */
    @SneakyThrows
    public PublicKey getRefreshTokenPublicKey() {
        String contentAsString = refreshSecretPublic.getContentAsString(StandardCharsets.UTF_8);
        return RSAUtil.getPublicKey(StringUtils.deleteWhitespace(contentAsString));
    }


}
