package com.modulix.framework.security.api.util;

import com.modulix.framework.mybatis.plus.api.enums.DataScope;
import com.modulix.framework.security.api.auth.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * security 工具类
 *
 * @author lipanre
 */
public class SecurityUtil {

    /**
     * 获取当前登录用户id
     *
     * @return 用户id
     */
    public static Long getUserId() {
        return getAuthInfo(Authentication::getId);
    }

    /**
     * 获取数据权限列表
     *
     * @return 数据权限列表
     */
    public static List<DataScope> getDataPermissions() {
        return getAuthInfo(Authentication::getDataScopes);
    }

    public static Boolean isLogin() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return false;
        }
        return true;
    }


    private static <T> T getAuthInfo(Function<Authentication, T> authInfoFunction) {
        if (!isLogin()) {
            throw new RuntimeException("未登录");
        }
        return authInfoFunction.apply((Authentication) SecurityContextHolder.getContext().getAuthentication());
    }

}
