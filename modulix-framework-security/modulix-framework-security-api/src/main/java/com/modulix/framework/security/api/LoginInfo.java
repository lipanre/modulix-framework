package com.modulix.framework.security.api;

import lombok.Data;

/**
 * 登录信息
 *
 * @author lipanre
 */
@Data
public class LoginInfo {

    /**
     * access token
     */
    private String token;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * 过期时间，单位：s
     */
    private Long expire;

    /**
     * 请求头
     */
    private String header;

    /**
     * 客户端id
     */
    private String clientId;

}
