package com.modulix.framework.security.config;

import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
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
    private String monitorPrefix;

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


}
