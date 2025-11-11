package com.modulix.framework.security.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.Objects;

/**
 * sa-token自动配置类
 *
 * @author lipanre
 */
@Configuration
@EnableConfigurationProperties(SaTokenConfigProperties.class)
public class SaTokenAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private SaTokenConfigProperties saTokenConfigProperties;


    /**
     * 默认所有接口都需要认证之后才能走下去
     *
     * @param registry 拦截器注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInfoInterceptor());
        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
                .excludePathPatterns(Objects.nonNull(saTokenConfigProperties.getIgnoreAuthUrls()) ?
                        saTokenConfigProperties.getIgnoreAuthUrls() : Collections.emptyList())
                .addPathPatterns("/**");
    }

    // Sa-Token 整合 jwt (Stateless 无状态模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForStateless();
    }
}
