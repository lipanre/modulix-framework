package com.modulix.framework.security.config;

import com.modulix.framework.security.util.RSAUtil;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
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
    private String accessSecret = "8F4EwUF29EAO7hdIOMPM20KG9U5DYW46SMvoXgXvSYIbJAX90uif1Ibiw00ZT1DE";

    /**
     * access token过期时间
     * <br>
     * 默认10分钟
     */
    private Duration accessExpiration = Duration.ofMinutes(10);

    /**
     * refresh token rsa 私钥
     */
    private String refreshSecretPrivate = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC37IlFpUV3qA0WvQsPE64ZBzdVaV7KXjzU0a7oJiHtdQeRz49Dg2k6nPWTZtxfie2tFWELMaAqAAVsWvx3q4nZ2ktzlyQM6JUw0hIbV9bIV8W/yCTAX8o/9x9fVjQQRqt9znTojrmGGwMIn7mqCHFKWbbx3TKMyKEmln14zfH1alZ4000K+Wr2+g3T4syF9f/c+FJ5g3yCFoEF0dAS90N1QzEHB3Iqu64k4KO3aWtHYxmn3bqsMaE7bB9yQsrFFWfHIArJeI53NDYlEip/IEGmAyuxbVZo1wbcgX7T+Egn3qOOSemTzqOSIyw6L/Lo4R4iZDKLEi68J61t+OGEWus5AgMBAAECggEAVVMnFcfH8+xi8hcOX9a18EeAPKAPCRvx5ZtHJMy39aTSt6rOYDhBI594cAMKnknrGN5vsFU74/Axk+Sb9lp+r7HNShkmcNSfjdAO9V9pNXU+uKt9nT9zIPygscZYjepzGYCjjNXVrKiwXhpXdFHJWQBI1mjTUCutUy0ClX4ZT0vUw5JsSEPQ88NNdO5mP7oRWfUKqY/4EcW8On+lGwkOG+gGd3eE7MJt+fEnAK/Vb7UdelGxh1Sm1rd7By9hOlXZ4Cb9Buu78Ze0g6iLRKOKo/b1/ulxSgPm9glPEFXs+K+XLpo2cn8evSnGXF8G4OM0tRVnMJ/6F5wRufU9saEalwKBgQDxtpxq45Bo5h4gJGzEPd0Vbl/J7U23ID3SEBtvc/hyzS4xfaBbMsdUCjQ8dud3fXvjS6860O/oL9Z5Ae9bs9iVqBRHnb1ZGS1wqupef854gc/2loS0xSIBqsMnRyYIa0GlKr/K8DGn8SHgk8eK0HFRdSV10anqn2xWfn+RKliZhwKBgQDCy4IytRGfY+ZnyHYk/jiXaOeeNjGOP+tZyvxbGaY9IO0JnIZauhKD0pRhnKQ84yVqBaWh3Fu1wiEY6I2HaYWATJDpb8jO28MmGDVWRCWXLkg/41qAvy0PuE13Oaf9fabMzSQRXxtakB9n0GDxv2kUWaqYnmLGOzPlQF/TM2aFPwKBgHTtSJdwhBAqmZIk7LS11Hgkj5PoBrs6jo1Km2r758jpNppcUjErzD78PK6vOmOUQVT/7MGABz4DvjWFWRu8UV58E2tdhBZQ1ql1FLEoFGI5MbfwsjucTUoPKB6dDWnvMK9MkcEDFpJOVHwGIPYmxVqJV9i51v+XpVnmEaHQoVGLAoGATTwlrndOunUAyQuZlIdb5uTIWVnBglZjuaGbN3/PAxPICHSQbO60IqJfBOj30dB6aDQ+/RODJWkIeHjUjOlQ7pLtLjGNXD+Ue8lDdKXmha/rI7HAk6W5dy8rP+eTbRXiCfgYoe6kyN62JRJhLdkoD9KRqpu6LEETDYXduL77mj8CgYAODM62Yl3TXofxhUemcpsc/jzamKgj8yvWoUCBjXXePvl6qA3oye9HCWjOjetkfbSJngVrrIrOf8is4EjSYZVajY8DjGYf6mv+q/N52fy9MHoN8G8OfVYZI0q4S2G/uNoo935pdxTsFRns05kEpcX8MUgdjyi4xk3IOxTsROxZ/Q==";

    /**
     * refresh token rsa 公钥
     */
    private String refreshSecretPublic = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt+yJRaVFd6gNFr0LDxOuGQc3VWleyl481NGu6CYh7XUHkc+PQ4NpOpz1k2bcX4ntrRVhCzGgKgAFbFr8d6uJ2dpLc5ckDOiVMNISG1fWyFfFv8gkwF/KP/cfX1Y0EEarfc506I65hhsDCJ+5qghxSlm28d0yjMihJpZ9eM3x9WpWeNNNCvlq9voN0+LMhfX/3PhSeYN8ghaBBdHQEvdDdUMxBwdyKruuJOCjt2lrR2MZp926rDGhO2wfckLKxRVnxyAKyXiOdzQ2JRIqfyBBpgMrsW1WaNcG3IF+0/hIJ96jjknpk86jkiMsOi/y6OEeImQyixIuvCetbfjhhFrrOQIDAQAB";

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
    public Key getRefreshTokenSecretKey() throws InvalidKeySpecException {
        return RSAUtil.getPrivateKey(refreshSecretPrivate);
    }

    /**
     * 获取refresh token rsa 公钥
     *
     * @return refresh token rsa 公钥
     * @throws InvalidKeySpecException 当公钥格式不正确时抛出
     */
    public PublicKey getRefreshTokenPublicKey() throws InvalidKeySpecException {
        return RSAUtil.getPublicKey(refreshSecretPublic);
    }


}
