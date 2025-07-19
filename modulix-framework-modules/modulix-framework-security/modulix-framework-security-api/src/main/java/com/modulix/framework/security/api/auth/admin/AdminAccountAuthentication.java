package com.modulix.framework.security.api.auth.admin;

import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import com.modulix.framework.security.api.auth.Authentication;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 管理端帐号认证
 *
 * @author lipanre
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminAccountAuthentication extends Authentication {

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private List<DataScope> dataScopes;

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            // 认证通过设置密码为空
            this.password = null;
        }
        super.setAuthenticated(authenticated);
    }
}
