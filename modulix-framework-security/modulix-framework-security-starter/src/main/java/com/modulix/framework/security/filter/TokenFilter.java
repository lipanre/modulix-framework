package com.modulix.framework.security.filter;

import com.modulix.framework.security.api.auth.Authentication;
import com.modulix.framework.security.api.auth.AuthenticationService;
import com.modulix.framework.security.auth.AuthenticationServiceFactory;
import com.modulix.framework.security.config.TokenConfigProperties;
import com.modulix.framework.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * @author lipanre
 */
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    /**
     * 客户端类型
     */
    private static final String CLIENT_TYPE = "client-type";

    private final TokenConfigProperties tokenConfigProperties;

    private final TokenService tokenService;

    private final AuthenticationServiceFactory authenticationServiceFactory;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(tokenConfigProperties.getHeader());
        if (StringUtils.isEmpty(token)) {
            throw new AuthorizationDeniedException("请先认证");
        }
        Long userId = null;
        if (token.startsWith(tokenConfigProperties.getMonitorPrefix())) {
            userId = Long.parseLong(token.substring(tokenConfigProperties.getMonitorPrefix().length()));
        } else {
            userId = tokenService.parseAccessToken(token, Long.class);
        }
        AuthenticationService<?> authenticationService = getAuthenticationService(request);
        if (Objects.isNull(authenticationService)) {
            throw new AuthorizationDeniedException("未知客户端");
        }
        Authentication authentication = authenticationService.loadAuthenticationById(userId);
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private AuthenticationService<?> getAuthenticationService(HttpServletRequest request) {
        return authenticationServiceFactory.getAuthenticationService(request.getHeader(CLIENT_TYPE));
    }
}
