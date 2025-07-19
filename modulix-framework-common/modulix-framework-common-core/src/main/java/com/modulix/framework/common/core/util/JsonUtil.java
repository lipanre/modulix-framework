package com.modulix.framework.common.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;

/**
 * json工具类
 *
 * @author lipanre
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

    /**
     * 将对象转换为json字符串
     *
     * @param data 对象
     * @return  json字符串
     * @param <T> 对象类型
     */
    @SneakyThrows
    public static <T> String toJson(T data) {
        return objectMapper.writeValueAsString(data);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json json字符串
     * @param clazz 对象类型
     * @return 对象
     * @param <T> 对象类型
     */
    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json json字符串
     * @param typeReference 类型引用
     * @return 对象
     * @param <T> 对象类型
     */
    @SneakyThrows
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        return objectMapper.readValue(json, typeReference);
    }
}