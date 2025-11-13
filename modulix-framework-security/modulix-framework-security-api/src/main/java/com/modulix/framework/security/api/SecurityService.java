package com.modulix.framework.security.api;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import com.modulix.framework.security.api.info.SecurityUser;

import java.util.Set;

/**
 * 安全上下文
 *
 * @author lipanre
 */
public interface SecurityService {

    /**
     * 安全上下文服务Bean名称后缀
     */
    String BEAN_NAME_SUFFIX = "SecurityService";


    /**
     * 获取当前用户的数据权限
     *
     * @return 数据权限
     */
    default Set<DataScope> getCurrentDataScopes() {
        return getDataScopes(StpUtil.getLoginIdAsLong());
    }

    /**
     * 获取制定用户的数据权限列表
     *
     * @param userId 用户ID
     * @return 数据权限列表
     */
    Set<DataScope> getDataScopes(Long userId);

    /**
     * 获取当前登录用户的角色编码列表
     *
     * @return 角色编码列表
     */
    default Set<String> listCurrentRoleCodes() {
        return listRoleCode(StpUtil.getLoginIdAsLong());
    }

    /**
     * 查询指定用户的角色编码列表
     *
     * @param userId 用户id
     * @return 角色编码列表
     */
    Set<String> listRoleCode(Long userId);

    /**
     * 创建登录参数
     *
     * @param userId 用户id
     * @return 登录参数
     */
    default SaLoginParameter createLoginParameter(Long userId) {
        SaLoginParameter loginParameter = new SaLoginParameter();
        loginParameter.setExtra(SecurityUser.Fields.dataScopes, getDataScopes(userId));
        loginParameter.setExtra(SecurityUser.Fields.roleCodes, listRoleCode(userId));
        return loginParameter;
    }

}
