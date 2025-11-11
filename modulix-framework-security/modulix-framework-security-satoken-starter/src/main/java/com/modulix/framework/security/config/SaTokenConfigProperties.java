package com.modulix.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * sa-token的一些扩展配置
 *
 * @author lipanre
 */
@Data
@ConfigurationProperties(prefix = "sa-token")
public class SaTokenConfigProperties {

    /**
     * 不需要认证的url列表
     */
    private List<String> ignoreAuthUrls;

}
