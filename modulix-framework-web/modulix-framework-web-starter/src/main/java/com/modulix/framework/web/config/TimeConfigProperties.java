package com.modulix.framework.web.config;

import com.modulix.framework.common.core.constant.ConfigConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.format.DateTimeFormatter;

/**
 * jackson配置
 *
 * @author lipanre
 */
@Data
@ConfigurationProperties(prefix = ConfigConstant.CONFIG_PREFIX + "jackson")
public class TimeConfigProperties {

    private String localDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private String localDatePattern =  "yyyy-MM-dd";

    private String localTimePattern = "HH:mm:ss";

    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    public DateTimeFormatter getLocalDateTimeFormat() {
        return getDateTimeFormat(localDateTimePattern);
    }

    public DateTimeFormatter getLocalDateFormat() {
        return getDateTimeFormat(localDatePattern);
    }

    public DateTimeFormatter getLocalTimeFormat() {
        return getDateTimeFormat(localTimePattern);
    }

    public DateTimeFormatter getDateTimeFormat(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

}
