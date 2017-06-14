package com.example1.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hulan on 2017/1/14.
 */
public class JacksonUtils {

    private final static ObjectMapper objectMapper=new ObjectMapper();
    private JacksonUtils() {

    }
    public static ObjectMapper getInstance(){
        return objectMapper;
    }

    /**
     * javaBean、列表数组转换为json字符串
     */
    public static String obj2json(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }
    /**
     * javaBean、列表数组转换为json字符串,忽略空值
     */
    public static String obj2jsonIgnoreNull(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(obj);
    }

    /**
     *  json 转JavaBean
     */

    public static <T> T json2pojo(String jsonString,Class<T> clazz) throws Exception {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
        return objectMapper.readValue(jsonString,clazz);
    }

    /**
     *  json字符串转换为map
     */
    public static <T> Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString,Map.class);
    }

    /**
     *json字符串转换为map
     */
    public static <T> Map<String, T> json2map(String jsonString, Class<T> clazz) throws Exception {
        Map<String,Map<String,Object>> map=objectMapper.readValue(jsonString, new TypeReference<Map<String,T>>(){});
        Map<String,T> result=new HashMap<String, T>();
        for(Map.Entry<String,Map<String,Object>> entry:map.entrySet()){
            result.put(entry.getKey(),map2pojo(entry.getValue(),clazz));
        }
        return result;
    }

    /**
     *  与javaBean json数组字符串转换为列表
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) throws Exception {

        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst =  (List<T>)objectMapper.readValue(jsonArrayStr, javaType);
        return lst;
    }


    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    /**
     * map  转JavaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz){
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * map 转json
     * @param map
     * @return
     */
    public static String mapToJson(Map map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
