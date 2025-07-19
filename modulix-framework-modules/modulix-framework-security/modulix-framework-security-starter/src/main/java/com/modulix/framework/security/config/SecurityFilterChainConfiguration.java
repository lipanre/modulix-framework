package com.modulix.framework.security.config;

import com.modulix.framework.security.auth.AuthenticationServiceFactory;
import com.modulix.framework.security.auth.admin.account.AdminAccountAuthenticationFilter;
import com.modulix.framework.security.filter.TokenFilter;
import com.modulix.framework.security.handler.FilterExceptionHandler;
import com.modulix.framework.security.processor.IgnoreAuthProcessor;
import com.modulix.framework.security.service.TokenService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

/**
 * 安全过滤器链配置类
 *
 * @author lipanre
 */
@Configuration
public class SecurityFilterChainConfiguration {


    /**
     * 允许所有请求过滤器链
     * <br>
     * 所有资源都不需要认证就能访问
     */
    @Bean
    public SecurityFilterChain permitAllFilterChain(HttpSecurity http,
                                                    IgnoreAuthProcessor ignoreAuthProcessor) throws Exception {
        disable(http);
        PathPatternRequestMatcher[] matchers = ignoreAuthProcessor.getMatchers().toArray(new PathPatternRequestMatcher[0]);
        http.securityMatchers(configurer -> configurer.requestMatchers(matchers));
        return http.build();
    }

    /**
     * 认证过滤器链
     * <br>
     * 通过这个过滤器链进行认证
     */
    @Bean
    public SecurityFilterChain authenticationSecurityFilterChain(HttpSecurity http,
                                                                 AuthenticationManager authenticationManager,
                                                                 AuthenticationSuccessHandler authenticationSuccessHandler,
                                                                 AuthenticationFailureHandler authenticationFailureHandler,
                                                                 AccessDeniedHandler accessDeniedHandler,
                                                                 AuthenticationEntryPoint authenticationEntryPoint,
                                                                 @Value("${server.auth.admin.login.path:/auth/admin/login/**}") String loginPath,
                                                                 @Value("${server.auth.admin.login.username.path:/auth/admin/login}") String adminAccountLoginPath) throws Exception {
        initExceptionHandling(http, accessDeniedHandler, authenticationEntryPoint);

        http.securityMatcher(loginPath);

        // 管理端帐号登录
        AdminAccountAuthenticationFilter adminAccountLoginFilter = new AdminAccountAuthenticationFilter(adminAccountLoginPath);
        initFilter(adminAccountLoginFilter, authenticationManager, authenticationSuccessHandler, authenticationFailureHandler);
        http.addFilterBefore(adminAccountLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http,
                                                           AccessDeniedHandler accessDeniedHandler,
                                                           AuthenticationEntryPoint authenticationEntryPoint,
                                                           TokenConfigProperties tokenConfigProperties,
                                                           TokenService tokenService,
                                                           AuthenticationServiceFactory authenticationServiceFactory) {
        initExceptionHandling(http, accessDeniedHandler, authenticationEntryPoint);
        http.addFilterAfter(new TokenFilter(tokenConfigProperties, tokenService, authenticationServiceFactory), ExceptionTranslationFilter.class);
        return http.build();
    }

    /**
     * 初始化HttpSecurity
     */
    @SneakyThrows
    private void disable(HttpSecurity http) {
        http.sessionManagement(SessionManagementConfigurer::disable)
                .securityContext(SecurityContextConfigurer::disable)
                .headers(HeadersConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .logout(LogoutConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .anonymous(AnonymousConfigurer::disable);
    }

    @SneakyThrows
    private void initExceptionHandling(HttpSecurity http,
                                       AccessDeniedHandler accessDeniedHandler,
                                       AuthenticationEntryPoint authenticationEntryPoint) {
        disable(http);
        http.exceptionHandling(configurer -> configurer
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(new FilterExceptionHandler(), SecurityContextHolderFilter.class);
    }

    /**
     * 初始化过滤器，设置认证管理器，认证成功处理器，认证失败处理器
     *
     * @param filter                       需要初始化的过滤器
     * @param authenticationManager        认证管理器
     * @param authenticationSuccessHandler 认证成功处理器
     * @param authenticationFailureHandler 认证失败处理器
     */
    public void initFilter(AbstractAuthenticationProcessingFilter filter,
                           AuthenticationManager authenticationManager,
                           AuthenticationSuccessHandler authenticationSuccessHandler,
                           AuthenticationFailureHandler authenticationFailureHandler) {
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
    }
}
