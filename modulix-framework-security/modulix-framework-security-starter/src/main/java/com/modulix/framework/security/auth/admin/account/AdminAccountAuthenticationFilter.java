package com.modulix.framework.security.auth.admin.account;

import com.modulix.framework.security.api.auth.admin.AdminAccountAuthentication;
import com.modulix.framework.web.aip.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;

/**
 * @author lipanre
 */
public class AdminAccountAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AdminAccountAuthenticationFilter(String url) {
        super(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, url));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        AdminAccountAuthentication adminAccountAuthentication = WebUtil.readBody(request, AdminAccountAuthentication.class);
        return getAuthenticationManager().authenticate(adminAccountAuthentication);
    }
}
