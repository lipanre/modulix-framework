package com.modulix.framework.security.api.auth;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author lipanre
 */
@Data
public class UserInfo {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色编码列表
     */
    private List<String> roles;

    /**
     * 按钮编码列表
     */
    private List<String> buttons;


}
