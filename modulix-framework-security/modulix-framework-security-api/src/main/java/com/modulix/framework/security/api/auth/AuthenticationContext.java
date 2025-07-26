package com.modulix.framework.security.api.auth;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 认证上下文
 *
 * @author lipanre
 */
public class AuthenticationContext {

    private static final TransmittableThreadLocal<AuthenticationService<Authentication>> AUTHENTICATION_SERVICE_TTL = new TransmittableThreadLocal<>();


    public static AuthenticationService<Authentication> getAuthenticationService() {
        return AUTHENTICATION_SERVICE_TTL.get();
    }

    public static void setAuthenticationService(AuthenticationService<Authentication> authenticationService) {
        AUTHENTICATION_SERVICE_TTL.set(authenticationService);
    }

    public static void clear() {
        AUTHENTICATION_SERVICE_TTL.remove();
    }

}
