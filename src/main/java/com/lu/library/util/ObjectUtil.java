package com.lu.library.util;

import android.os.Build;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import androidx.collection.SimpleArrayMap;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.util
 * @description: ${TODO}{ 类注释}
 * @date: 2018/7/16 0016
 */

public class ObjectUtil {
    public static <E> List<E> deepCopy(List<E> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            @SuppressWarnings("unchecked")
            List<E> dest = (List<E>) in.readObject();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<E>();
        }
    }
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
    public static Class<?> getParameterizedType(Class<?> clazz,boolean isInterface){
        //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
        Type type = clazz.getGenericSuperclass();
        try {
            if (isInterface) {
                type = clazz.getGenericInterfaces()[0];
            }
            // 判断 是否泛型
            if (type instanceof ParameterizedType) {
                // 返回表示此类型实际类型参数的Type对象的数组.
                // 当有多个泛型类时，数组的长度就不是1了
                Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
                Class<?> c = (Class) ptype[0];
                return c;
            }
        }catch (Exception e){
            e.printStackTrace();
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
    /**
     * 检测集合是否为空
     * @param collection
     * @return
     */
    public static boolean isCollectionEmpty(Object collection){
        if (collection==null){
            return true;
        }
        return false;
    }

    /**
     * Return whether object is empty.
     *
     * @param obj The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray
                    && ((android.util.LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**深度克隆
     * Deep clone.
     *
     * @param data The data.
     * @param type The type.
     * @param <T>  The value type.
     * @return The object of cloned.
     */
    public static <T> T deepClone(final T data, final Type type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(data), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
