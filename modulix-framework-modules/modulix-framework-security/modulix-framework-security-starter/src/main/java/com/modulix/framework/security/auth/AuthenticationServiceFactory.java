package com.modulix.framework.security.auth;

import com.modulix.framework.security.api.auth.Authentication;
import com.modulix.framework.security.api.auth.AuthenticationService;

/**
 * @author lipanre
 */
public interface AuthenticationServiceFactory {

    /**
     *
     * @param name service名
     * @return service对象
     */
    <T extends Authentication> AuthenticationService<T> getAuthenticationService(String name);

}
