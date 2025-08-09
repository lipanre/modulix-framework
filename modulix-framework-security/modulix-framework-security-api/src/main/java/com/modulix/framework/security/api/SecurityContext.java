package com.modulix.framework.security.api;

import cn.dev33.satoken.stp.StpUtil;
import com.modulix.framework.mybatis.plus.api.enums.DataScope;

import java.util.List;

/**
 * 安全上下文
 *
 * @author lipanre
 */
public interface SecurityContext {

    /**
     * 获取当前用户的数据权限
     *
     * @return 数据权限
     */
    default List<DataScope> getCurrentDataScopes() {
        return getDataScopes(StpUtil.getLoginIdAsLong());
    }

    /**
     * 获取制定用户的数据权限列表
     *
     * @param userId 用户ID
     * @return 数据权限列表
     */
    List<DataScope> getDataScopes(Long userId);

}
