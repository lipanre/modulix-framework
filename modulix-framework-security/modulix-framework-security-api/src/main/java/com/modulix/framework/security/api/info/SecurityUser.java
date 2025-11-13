package com.modulix.framework.security.api.info;

import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Set;

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
    private Object userId;

    /**
     * 当前用户数据权限列表
     */
    private Set<DataScope> dataScopes;

    /**
     * 当前用户角色编码列表
     */
    private Set<String> roleCodes;

    /**
     * 客户端类型
     */
    private String clientType;
}
