package com.modulix.framework.security.api.auth;

import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

/**
 * @author lipanre
 */
public abstract class Authentication extends AbstractAuthenticationToken {

    /**
     * 获取用户唯一标识
     *
     * @return 用户唯一标识
     */
    public abstract Long getId();

    /**
     * 数据权限范围
     */
    public abstract List<DataScope> getDataScopes();

    public Authentication() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
