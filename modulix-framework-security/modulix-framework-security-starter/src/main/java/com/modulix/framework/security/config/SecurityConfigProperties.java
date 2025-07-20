package com.modulix.framework.security.config;

import com.modulix.framework.common.core.constant.ConfigConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 安全配置属性
 *
 * @author lipanre
 */
@Data
@ConfigurationProperties(prefix = ConfigConstant.CONFIG_PREFIX + "security")
public class SecurityConfigProperties {

    /**
     * token配置属性
     */
    @NestedConfigurationProperty
    private TokenConfigProperties token;

    /**
     * 是否启用单账号登录
     */
    private Boolean enableSingleLogin = false;

}
