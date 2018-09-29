package com.lu.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.id.app.comm.lib.utils
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/26 0026
 */
public class ReflectUtil {
    /**
     * 反射调用一个方法
     * @param target 对象
     * @param methodName 方法名
     * @param parameterTypes 方法参数类型
     * @param args 方法参数
     * @return 返回值
     */
    public static Object invokeMethod(Object target,String methodName,Class[] parameterTypes,Object... args){
        Method method= null;
        try {
            method = target.getClass().getDeclaredMethod(methodName,parameterTypes);
            method.setAccessible(true);
            return method.invoke(target,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * 反射调用一个方法
     * 此方法根据方法名称去获取方法，找到第一个就返回，传入的参数类型要匹配。
     * 如果类中没有同名方法，则可以调用此方法
     * @param target 对象
     * @param methodName 方法名
     * @param args 方法参数
     * @return 返回值
     */
    public static Object invokeMethod(Object target,String methodName,Object... args){
        try {
            Method[] methods = target.getClass().getDeclaredMethods();
            for(Method method:methods) {
                if(method.getName().equals(methodName)) {
                    method.setAccessible(true);
                    return method.invoke(target,args);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取字段
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> clazz,String fieldName){
        Field field= null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * 设置字段
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setField(Object obj, String fieldName,Object value){
        Field field=getField(obj.getClass(),fieldName);
        if (field!=null){
            try {
                field.set(obj,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
