package com.modulix.framework.security.api;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import com.modulix.framework.security.api.common.RoleCode;
import com.modulix.framework.security.api.info.SecurityUser;

import java.util.Objects;
import java.util.Set;

/**
 * security 工具类
 *
 * @author lipanre
 */
public class SecurityUtil {

    private static final TransmittableThreadLocal<SecurityUser> currentUser = new TransmittableThreadLocal<>();


    /**
     * 设置当前线程的用户信息
     *
     * @param user 用户信息
     */
    public static void setCurrentUser(SecurityUser user) {
        currentUser.set(user);
    }

    /**
     * 获取当前线程的用户信息
     *
     * @return 用户信息
     */
    public static SecurityUser getCurrentUser() {
        return currentUser.get();
    }

    /**
     * 清除当前线程的用户信息
     */
    public static void clearCurrentUser() {
        currentUser.remove();
    }

    /**
     * 判断当前登录用户是不是超级管理员
     *
     * @return true - 是超级管理员 <br>
     * false - 不是超级管理员
     */
    public static Boolean isSuperAdmin() {
        SecurityUser user = getCurrentUser();
        return Objects.nonNull(user.getRoleCodes()) && user.getRoleCodes().contains(RoleCode.SUPER_ADMIN_ROLE_CODE);
    }

    /**
     * 判断当前登录用户是不是管理员
     *
     * @return true - 是管理员 <br>
     * false - 不是管理员
     */
    public static Boolean isAdmin() {
        SecurityUser user = getCurrentUser();
        return Objects.nonNull(user.getRoleCodes()) && user.getRoleCodes().contains(RoleCode.ADMIN_ROLE_CODE);
    }

    /**
     * 判断当前登录用户是否登录
     *
     * @return true - 已登录 <br>
     * false - 未登录
     */
    public static Boolean isLogin() {
        return Objects.nonNull(getCurrentUser());
    }

    /**
     * 获取当前登录用户的数据权限列表
     *
     * @return 当前登录用户的数据权限列表
     */
    public static Set<DataScope> getCurrentDataScopes() {
        SecurityUser user = getCurrentUser();
        return user.getDataScopes();
    }

    /**
     * 获取当前登录用户的客户端类型
     *
     * @return 客户端类型
     */
    public static String getClientType() {
        SecurityUser user = getCurrentUser();
        return user.getClientType();
    }
}
