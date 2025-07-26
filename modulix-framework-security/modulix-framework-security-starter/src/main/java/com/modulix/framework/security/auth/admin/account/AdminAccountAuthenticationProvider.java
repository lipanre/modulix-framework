package com.modulix.framework.security.auth.admin.account;

import com.modulix.framework.security.api.auth.AuthenticationService;
import com.modulix.framework.security.api.auth.admin.AdminAccountAuthentication;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * 管理端帐号登录
 *
 * @author lipanre
 */
public class AdminAccountAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private AuthenticationService<AdminAccountAuthentication> authenticationService;

    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AdminAccountAuthentication adminAccountAuthentication = (AdminAccountAuthentication) authentication;
        AdminAccountAuthentication dbAuthentication = authenticationService.loadAuthentication(adminAccountAuthentication::getUsername);
        if (Objects.isNull(dbAuthentication)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!passwordEncoder.matches(adminAccountAuthentication.getPassword(), dbAuthentication.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        dbAuthentication.setAuthenticated(true);
        return dbAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(AdminAccountAuthentication.class);
    }
}
