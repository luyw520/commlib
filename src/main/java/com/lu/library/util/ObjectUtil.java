package com.lu.library.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.util
 * @description: ${TODO}{ 类注释}
 * @date: 2018/7/16 0016
 */

public class ObjectUtil {
    /**
     * 获取传入Class对面里面泛型
     * 得到泛型类P的对象
     * @return Class对象的第一个泛型对象
     */
    public  static <P> P getParameterizedType(Class<?> clazz){
        //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
        //这里type结果是 com.dfsj.generic.GetInstanceUtil<com.dfsj.generic.User>
        Type type = clazz.getGenericSuperclass();
        // 判断 是否泛型
        if (type instanceof ParameterizedType) {
            // 返回表示此类型实际类型参数的Type对象的数组.
            // 当有多个泛型类时，数组的长度就不是1了
            Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
            Class<?> c= (Class) ptype[0];
            try {
                return (P)c.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public static <T> T checkNotNull(T obj) {
        if(obj == null) {
            throw new NullPointerException();
        } else {
            return obj;
        }
    }

    /**
     * 检测集合是否为空
     * @param collection
     * @return
     */
    public static boolean isCollectionEmpty(Collection collection){
        if (collection==null){
            return true;
        }
        if (collection.isEmpty()){
            return true;
        }
        return false;
    }

}
