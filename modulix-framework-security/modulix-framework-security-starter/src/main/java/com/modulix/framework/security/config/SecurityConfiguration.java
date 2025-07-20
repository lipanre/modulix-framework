package com.modulix.framework.security.config;

import com.modulix.framework.security.auth.AuthenticationServiceFactory;
import com.modulix.framework.security.auth.admin.account.AdminAccountAuthenticationProvider;
import com.modulix.framework.security.controller.AuthController;
import com.modulix.framework.security.handler.AccessDeniedHandlerImpl;
import com.modulix.framework.security.handler.AuthenticationEntryPointImpl;
import com.modulix.framework.security.handler.AuthenticationFailureHandlerImpl;
import com.modulix.framework.security.handler.AuthenticationSuccessHandlerImpl;
import com.modulix.framework.security.processor.IgnoreAuthProcessor;
import com.modulix.framework.security.service.AuthService;
import com.modulix.framework.security.service.TokenService;
import com.modulix.framework.security.service.impl.AuthServiceImpl;
import com.modulix.framework.security.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * security 配置
 *
 * @author lipanre
 */
@Configuration
@EnableConfigurationProperties(SecurityConfigProperties.class)
public class SecurityConfiguration {

    @Bean
    public TokenConfigProperties tokenConfigProperties(SecurityConfigProperties securityConfigProperties) {
        return securityConfigProperties.getToken();
    }

    /**
     * 忽略认证processor
     */
    @Bean(initMethod = "init")
    public IgnoreAuthProcessor ignoreAuthProcessor(@Value("${server.error.path:${error.path:/error}}") String errorPath) {
        return new IgnoreAuthProcessor(errorPath);
    }

    /**
     * 访问被拒绝
     * <br>
     * 已经认证，但是权限不够
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * 访问被拒绝
     * <br>
     * 未认证访问受保护的资源
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * 全局认证失败处理器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandlerImpl();
    }

    /**
     * 全局认证成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenService tokenService() {
        return new TokenServiceImpl();
    }

    @Bean
    public AuthenticationProvider adminAuthenticationProvider() {
        return new AdminAccountAuthenticationProvider();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider... authenticationProviders) {
        return new ProviderManager(authenticationProviders);
    }

    @Bean
    public ServiceLocatorFactoryBean authenticationServiceLocatorFactoryBean() {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(AuthenticationServiceFactory.class);
        return serviceLocatorFactoryBean;
    }

    @Bean
    public AuthService authService() {
        return new AuthServiceImpl();
    }

    @Bean
    public AuthController  authController() {
        return new AuthController();
    }
}
