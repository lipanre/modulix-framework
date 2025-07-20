package com.modulix.framework.mybatis.plus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.modulix.framework.mybatis.plus.aop.DataBaseOperationAspect;
import com.modulix.framework.mybatis.plus.aop.PageRequestAspect;
import com.modulix.framework.mybatis.plus.api.page.PaginationInterceptor;
import com.modulix.framework.mybatis.plus.manager.PostOperationManager;
import com.modulix.framework.mybatis.plus.meta.BaseDomainMetaObjectHandler;
import com.modulix.framework.mybatis.plus.permission.DataPermissionHandlerImpl;
import com.modulix.framework.security.api.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * {@code description}
 * mybatis-plus通用配置类
 *
 * <br>
 * {@code date} 2025/2/7 10:35
 */
@Configuration
@MapperScan("**.mapper")
public class MybatisPlusConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(DataPermissionHandler dataPermissionHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new DataPermissionInterceptor(dataPermissionHandler));
        interceptor.addInnerInterceptor(new PaginationInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        return interceptor;
    }

    @Configuration
    @ConditionalOnClass({Aspect.class, HttpServletRequest.class, HttpServletResponse.class, RequestContextHolder.class})
    public static class AopConfiguration {
        /**
         * 分页切面
         *
         * @return 分页切面
         */
        @Bean
        public PageRequestAspect pageRequestAspect() {
            return new PageRequestAspect();
        }

        /**
         * 检查切面
         *
         * @return 检查切面
         */
        @Bean
        public DataBaseOperationAspect checkAspect() {
            return new DataBaseOperationAspect();
        }

        /**
         * 前置操作管理器
         *
         * @return 前置操作管理器对象
         */
        @Bean(initMethod = "registerStatement")
        public PostOperationManager postOperationManager() {
            return new PostOperationManager();
        }
    }

    @Configuration
    @ConditionalOnClass(SecurityUtil.class)
    public static class SecurityConfiguration {

        @Bean
        public BaseDomainMetaObjectHandler baseDomainMetaObjectHandler() {
            return new BaseDomainMetaObjectHandler();
        }

        @Bean
        public DataPermissionHandler dataPermissionHandler() {
            return new DataPermissionHandlerImpl();
        }

    }

}
