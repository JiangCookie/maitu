package com.ljc.maitu.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author JYH
 * 2019/1/7 17:06
 */
public class JsonUtils {

    private static  final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @Description: 将对象转换成json字符串
     */
    public static String objectToJson(Object data){
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 将json结果集转换成对象
     */
    public static <T> T jsonToPojo(String jsondata, Class<T> beanType){
        try {
            T t = MAPPER.readValue(jsondata,beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

















}
