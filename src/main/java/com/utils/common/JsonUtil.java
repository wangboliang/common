package com.utils.common;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Json序列化工具
 * </p>
 *
 * @author wangliang
 * @since 2017/8/1
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
        //允许从非JSON数组值到单元素数组和集合（添加隐式“数组包装器”）的自动转换
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        //允许空数组值（JSON中的“[]”）反序列化为空值(null)
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        //允许空字符串值反序列化为常规POJO的空值(null)
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public static <T> String objectToJson(T data) {
        String json = null;
        if (data != null) {
            try {
                json = mapper.writeValueAsString(data);
            } catch (Exception e) {
                throw new RuntimeException("objectToJson method error: " + e);
            }
        }
        return json;
    }

    public static <T> T jsonToObject(String json, Class<T> cls) {
        T object = null;
        if (!StringUtils.isEmpty(json) && cls != null) {
            try {
                if (json.startsWith("\"{")) {
                    json = json.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\"");
                }
                object = mapper.readValue(json, cls);
            } catch (Exception e) {
                throw new RuntimeException("objectToJson method error: " + e);
            }
        }
        return object;
    }

    public static List<Map<String, Object>> jsonToList(String json) {
        List<Map<String, Object>> list = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (json.startsWith("\"[")) {
                    json = json.replace("\"[", "[").replace("]\"", "]").replace("\\\"", "\"");
                }
                list = mapper.readValue(json, List.class);
            } catch (Exception e) {
                throw new RuntimeException("objectToJson method error: " + e);
            }
        }
        return list;
    }

    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> maps = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (json.startsWith("\"{")) {
                    json = json.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\"");
                }
                maps = mapper.readValue(json, Map.class);
            } catch (Exception e) {
                throw new RuntimeException("objectToJson method error: " + e);
            }
        }
        return maps;
    }

}
