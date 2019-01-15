package com.oj.judge.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author m969130721@163.com
 * @date 18-6-20 下午4:07
 */
public class JsonUtil {

    private static Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        //对象的所有字段全部序列化
        MAPPER.setSerializationInclusion(Include.ALWAYS);
        //取消自动转timestamps
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //忽略　在json字符串中存在,但是java对象中不存在对应属性的情况,防止报错
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //日期格式
        MAPPER.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
    }

    public static <T> String obj2String (T obj){
        if(obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("parse object to String error ",e);
            return null;
        }
    }

    public static <T> String obj2StringPretty (T obj){
        if(obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("parse object to String error ",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : MAPPER.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("parse String to Object error ",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ?  (T)str : MAPPER.readValue(str,typeReference);
        } catch (Exception e) {
            log.warn("parse String to Object error ",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... beanClass){
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(collectionClass,beanClass);
        try {
            return MAPPER.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("parse String to Object error ",e);
            return null;
        }
    }


}
