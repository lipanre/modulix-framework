package com.modulix.framework.web.config;

import com.modulix.framework.common.core.constant.ConfigConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jackson配置
 *
 * @author lipanre
 */
@Data
@ConfigurationProperties(prefix = ConfigConstant.CONFIG_PREFIX + "jackson")
public class JacksonConfigProperties {

    private String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private String localDatePattern =  "yyyy-MM-dd";

    private String localTimePattern = "HH:mm:ss";

}
