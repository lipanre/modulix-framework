package com.modulix.framework.security.util;

import io.jsonwebtoken.io.Decoders;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * {@code description}
 * rsa 工具类
 *
 * <br>
 * {@code date} 2025/2/6 13:59
 */
public class RSAUtil {

    private final static KeyFactory RSA;

    static {
        try {
            RSA = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取私钥
     *
     * @param privateBase64 私钥字符串
     * @return 私钥
     * @throws InvalidKeySpecException 私钥字符串格式错误
     */
    public static PrivateKey getPrivateKey(String privateBase64) throws InvalidKeySpecException {
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(Decoders.BASE64.decode(privateBase64));
        return RSA.generatePrivate(ks);
    }

    /**
     * 获取公钥
     *
     * @param publicBase64 公钥字符串
     * @return 公钥
     * @throws InvalidKeySpecException 公钥字符串格式错误
     */
    public static PublicKey getPublicKey(String publicBase64) throws InvalidKeySpecException {
        X509EncodedKeySpec ks = new X509EncodedKeySpec(Decoders.BASE64.decode(publicBase64));
        return RSA.generatePublic(ks);
    }
}
