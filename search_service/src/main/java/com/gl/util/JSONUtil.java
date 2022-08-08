package com.gl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JSONUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSON(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String json,Class<T> clazz){
        T obj = null;
        try {
            obj = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Map<String,String> parseMap(String json){
        try {
            Map<String,String> map = objectMapper.readValue(json,Map.class);
            return map;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
