package com.modulix.framework.web.config;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.modulix.framework.web.handler.GlobalExceptionHandler;
import com.modulix.framework.web.jackson.LongToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lipanre
 */
@Configuration
@EnableConfigurationProperties(TimeConfigProperties.class)
public class WebConfiguration {

    @Bean
    public GlobalExceptionHandler  globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * Json序列化和反序列化转换器，用于转换Post请求体中的json以及将我们的对象序列化为返回响应的json
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper(JavaTimeModule javaTimeModule) {

        JsonMapper jsonMapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
                .build();

        // 全局配置序列化返回 JSON 处理
        // JSON Long ==> String
        // 自定义字符串转化规则ToStringSerializer换成自定义的LongToStringSerializer
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, new LongToStringSerializer());
        simpleModule.addSerializer(Long.TYPE, new LongToStringSerializer());

        jsonMapper.registerModule(simpleModule);
        jsonMapper.registerModule(javaTimeModule);
        return jsonMapper;
    }

    @Bean
    public JavaTimeModule javaTimeModule(TimeConfigProperties timeConfigProperties) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern(timeConfigProperties.getLocalDateTimePattern());
        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(timeConfigProperties.getLocalDatePattern());
        DateTimeFormatter localTimeFormatter = DateTimeFormatter.ofPattern(timeConfigProperties.getLocalTimePattern());

        // LocalDateTime
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormatter));

        // LocalDate
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(localDateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(localDateFormatter));

        // LocalTime
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(localTimeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(localTimeFormatter));
        return javaTimeModule;
    }

}
