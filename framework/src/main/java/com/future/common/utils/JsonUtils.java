package com.future.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * JSON 工具类
 *
 * @author JonZhang
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static @NotNull ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static JsonNode readTree(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize any Java value as a String
     *
     * @param obj the object to serialize
     * @return json string
     */
    public static @Nullable String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize a json content as any Java value
     *
     * @param content the json content to deserialize
     * @param clazz   Java value type
     * @param <T>     Java value type
     * @return Java value
     */
    public static <T> @Nullable T toObject(String content, Class<T> clazz) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return objectMapper.readValue(content, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize a json content as any Java value, eg:
     * <pre>
     *     toObject("[\"a\", \"b\"]", new TypeReference&lt;List&lt;String&gt;&gt;(){});
     * </pre>
     *
     * @param content   the json content to deserialize
     * @param reference Java value type
     * @param <T>       Java value type
     * @return Java value
     */
    public static <T> @Nullable T toObject(String content, @NotNull TypeReference<T> reference) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        try {
            return objectMapper.readValue(content, reference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
