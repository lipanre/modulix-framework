package com.modulix.framework.web.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.annotation.Resource;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Configuration
public class DateConverterConfig {


    /**
     * 入参-形参、@RequestParam和@PathVariable时候-字符串转Date相关类
     */
    @Component
    public static class LocalDateConvert implements Converter<String, LocalDate> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public LocalDate convert(@NonNull String pattern) {
            if (StringUtils.isEmpty(pattern)) {
                return null;
            }
            return LocalDate.parse(pattern, timeConfigProperties.getLocalDateFormat());
        }
    }

    /**
     * 入参-形参、@RequestParam和@PathVariable时候-字符串转Date相关类
     */
    @Component
    public static class LocalDateTimeConvert implements Converter<String, LocalDateTime> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public LocalDateTime convert(@NonNull String pattern) {
            if (StringUtils.isEmpty(pattern)) {
                return null;
            }
            return LocalDateTime.parse(pattern, timeConfigProperties.getLocalDateTimeFormat());
        }
    }

    /**
     * 入参-形参、@RequestParam和@PathVariable时候-字符串转Date相关类
     */
    @Component
    public static class DateConvert implements Converter<String, Date> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public Date convert(@NonNull String pattern) {
            if (StringUtils.isEmpty(pattern)) {
                return null;
            }
            try {
                return DateUtils.parseDate(pattern, timeConfigProperties.getDatePattern());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 反序列化LocalDateTime
     */
    @Component
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            if (StringUtils.isEmpty(p.getValueAsString())) {
                return null;
            }
            return LocalDateTime.parse(p.getValueAsString(), timeConfigProperties.getLocalDateTimeFormat());
        }
    }

    /**
     * 序列化LocalDateTime为时间戳字符串
     */
    @Component
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (Objects.isNull(value)) {
                return;
            }
            gen.writeString(timeConfigProperties.getLocalDateTimeFormat().format(value));
        }
    }

    /**
     * 反序列化LocalDate
     */
    @Component
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
            if (StringUtils.isEmpty(p.getValueAsString())) {
                return null;
            }
            return LocalDate.parse(p.getValueAsString(), timeConfigProperties.getLocalDateTimeFormat());
        }
    }

    /**
     * 序列化LocalDate为时间戳
     */
    @Component
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Resource
        private TimeConfigProperties timeConfigProperties;


        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (Objects.isNull(value)) {
                return;
            }
            gen.writeString(timeConfigProperties.getLocalDateFormat().format(value));
        }
    }

    /**
     * 序列化LocalTime为时间戳
     */
    @Component
    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (Objects.isNull(value)) {
                return;
            }
            gen.writeString(timeConfigProperties.getLocalTimeFormat().format(value));
        }
    }

    /**
     * 序列化LocalTime为时间戳
     */
    @Component
    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

        @Resource
        private TimeConfigProperties timeConfigProperties;

        @Override
        public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            if (StringUtils.isEmpty(jsonParser.getValueAsString())) {
                return null;
            }
            return LocalTime.parse(jsonParser.getValueAsString(), timeConfigProperties.getLocalTimeFormat());
        }
    }
}