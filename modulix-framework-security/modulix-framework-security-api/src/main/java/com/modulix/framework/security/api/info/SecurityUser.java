package com.modulix.framework.security.api.info;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Collection;

/**
 * 用户信息
 *
 * @author lipanre
 */
@Data
@FieldNameConstants
public class SecurityUser {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 当前用户数据权限列表
     */
    private Collection<String> dataScopes;

    /**
     * 当前用户角色编码列表
     */
    private Collection<String> roleCodes;

    /**
     * 客户端类型
     */
    private String clientType;
}
