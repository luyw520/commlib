package com.lu.library.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.util;
 * @description: ${TODO}{ google gson 解析辅助类封装了gson解析字符串成对象和集合方法并且捕获了异常}
 * @date: 2018/5/30
 */
public class GsonUtil {



    /**
     * 把对象转成json字符串
     * 若解析异常何返回null
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        try {
            return gson.toJson(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }


    /**
     * json字符串解析成List集合
     * 解析出错则返回Null
     * @return  List or null
     */
    public static <T> List<T> fromJson2List(String jsonString) {

        return fromJsonToCollect(jsonString);
    }
    /**
     *gson字符串转换为Map集合
     *解析出错则返回Null
     * @return  Map
     */
    public static <K,V> Map<K,V> fromJson2Map(String jsonString) {

        return fromJsonToCollect(jsonString);
    }
    /**
     * gson字符串转换为Map集合
     *
     */
    public static <K,V> Map json2Map(String jsonString,Map<K,V> result) {
        return fromJsonToCollect(jsonString);
    }
    /**
     *jsonString 字符串转换为集合对象
     * 解析出错返回null
     * @param jsonString json字符串
     * @return T 集合
     */
    public static <T> T fromJsonToCollect(String jsonString) {
        T result = null;
        Gson gson = new Gson();
        if (TextUtils.isEmpty(jsonString)){
            return null;
        }
        try {
            result = gson.fromJson(jsonString, new TypeToken<T>() {
            }.getType());
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }



    /**
     *
     * @param gsonStr json字符串
     * @param clazz 返回的类型
     * @param <T>
     * @return  解析错误返回null
     */
    public static <T> T fromJson(String gsonStr,Class<T> clazz){
        Gson gson=new Gson();
        try {
            return gson.fromJson(gsonStr,clazz);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param gsonStr json字符串
     * @param clazz   返回的类型
     * @param <T>
     * @return 解析错误返回null
     */
    public static <T> T fromJson(String gsonStr, Class<T> clazz, Class<?> typeClazz) {
        Gson gson = new Gson();
        try {
            Type objectType = type(clazz, typeClazz);
            return gson.fromJson(gsonStr, objectType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
