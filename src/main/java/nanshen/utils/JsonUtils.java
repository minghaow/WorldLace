/*
 * @(#)JsonUtils.java, 2014-12-23.
 *
 * Copyright 2014 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package nanshen.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Map;

/**
 * 提供java对象和json转换相关的方法
 * <p />
 * 这段代码是基于Jackson实现的，Jackson是一个相当优秀的json库，
 * 若对这部分代码有疑问，请查询Jackson的相关文档。
 * <p />
 * 大部分json输出格式上的问题，可以通过添加Jackson相关注解解决。
 *
 * @author WANG Minghao
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = getMapper();

    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 当json字符串中含有未知的属性时不会抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * 获取集合的正确类型，防止从json转换为集合对象时丢失集合内容的类型
     * <p />
     * 如<code>List&lt;Set&lt;Integer>></code>，可以调用
     * <code>getCollectionType(List.class, Integer.class)</code>
     *
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json json字符串
     * @param klass 要转换的对象的类型
     * @return
     */
    public static <T> T fromJson(String json, Class<T> klass) {
        try {
            return MAPPER.readValue(json, klass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json字符串转换为对象
     *
     * @param json json字符串
     * @param type 要转换的对象的类型
     * @return
     */
    public static <T> T fromJson(String json, JavaType type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过设置特殊的Deserializer，将json字符串转换为对象
     *
     * @param json json字符串
     * @param klass 要转换的对象的类型
     * @param deserializers 对象类型到Deserializer的映射，当遇到指定的对象类型时将使用指定的Deserializer
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> klass, Map<Class<?>,JsonDeserializer<?>> deserializers) {
        ObjectMapper mapper = getMapper();
        Module module = new SimpleModule("", Version.unknownVersion(), deserializers);
        mapper.registerModules(module);
        try {
            return mapper.readValue(json, klass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换为json字符串
     *
     * @param value 要转换的对象
     * @return
     */
    public static <T> String toJson(T value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换为人类可读的格式化json
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String toPrettyJson(T value) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
