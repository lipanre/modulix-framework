package com.modulix.framework.security.api.config;

import com.modulix.framework.security.api.SecurityServiceFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author lipanre
 */
@Configuration
public class SecurityConfig {

    @Bean
    public ServiceLocatorFactoryBean securityServiceLocatorFactoryBean() {
        ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
        serviceLocatorFactoryBean.setServiceLocatorInterface(SecurityServiceFactory.class);
        return serviceLocatorFactoryBean;
    }

}
