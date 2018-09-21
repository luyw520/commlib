package com.lu.library.util;

/**
 * Created by zhouzj on 2018/7/26.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.id.app.comm.lib.IdoApp;


abstract public class SharePreferenceUtils {

    private static String mName = "veryfit2.2_multi_new_app";
    private static SharedPreferences.Editor mEditor;

    /**
     * 夜间模式，保存抬腕识别时间段
     */
    public static String WRIST_TIME_DISTANCE = "Wrist_TIME_DISATACE";

    /**
     * 夜间模式，保存心率检测时间段
     */
    public static String HEART_TIME_DISTANCE = "Heart_TIME_DISATACE";

    /**
     * 夜间模式，保存睡眠检测时间段
     */
    public static String SLEEP_TIME_DISTANCE = "Sleep_TIME_DISATACE";

    /**
     * 夜间模式，保存勿扰模式设置时间段
     */
    public static String DISTURB_TIME_DISATACE = "Disturb_TIME_DISATACE";

    /**
     * 是否为第一次同步个人信息
     */
    public static final String FIRST_SYNC = "FIRST_SYNC";
    /**
     * 保存星期开始日下标
     */
    public static final String WEEK_START_INDEX = "WEEK_START_INDEX";

    /**
     * 保存久坐
     */
    public static final String DEVICE_SPORT_SET = "DEVICE_SPORT_SET";

    /**
     * 设置SharePreferenceName 默认是sharepreference_datas
     *
     * @param name
     */
    public static void setSharePreferenceName(String name) {
        mName = name;
    }

    /**
     * 保存闹钟功能列表
     */
    public static final String SUPPORT_ALARM_NUM = "SUPPORT_ALARM_NUM";



    /**
     * put string val
     *
     * @param context
     * @param key
     * @param val
     */
    public static void putString(Context context, String key, String val) {
        put(context, key, val);
    }

    /**
     * put string val
     *
     * @param key
     * @param val
     */
    public static void putString(String key, String val) {
        put(IdoApp.getAppContext(), key, val);
    }

    /**
     * get string val
     *
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        return (String) get(context, key, "");
    }

    /**
     * get string val
     *
     * @param context
     * @param key
     * @param defaultVal
     * @return
     */
    public static String getString(Context context, String key, String defaultVal) {
        return (String) get(context, key, defaultVal);
    }

    /**
     * put int val
     *
     * @param context
     * @param key
     * @param val
     */
    public static void putInt(Context context, String key, int val) {
        put(context, key, val);
    }

    /**
     * get int val
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        return (int) get(context, key, 0);
    }
    /**
     * get int val
     *
     * @param key
     * @return
     */
    public static int getInt( String key) {
        return (int) get(IdoApp.getAppContext(), key, 0);
    }

    /**
     * get int val
     *
     * @param context
     * @param key
     * @param defaultVal
     * @return
     */
    public static int getInt(Context context, String key, int defaultVal) {
        return (int) get(context, key, defaultVal);
    }

    /**
     * put bool val
     *
     * @param context
     * @param key
     * @param val
     */
    public static void putBool(Context context, String key, Boolean val) {
        put(context, key, val);
    }

    /**
     * put bool val
     *
     * @param key
     * @param val
     */
    public static void putBool(String key, Boolean val) {
        put(IdoApp.getAppContext(), key, val);
    }

    /**
     * get bool val
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean getBool(Context context, String key) {
        return (boolean) get(context, key, false);
    }

    /**
     * get bool val
     *
     * @param context
     * @param key
     * @param defaultVal
     * @return
     */
    public static boolean getBool(Context context, String key, Boolean defaultVal) {
        return (boolean) get(context, key, defaultVal);
    }

    /**
     * put float val
     *
     * @param context
     * @param key
     * @param val
     */
    public static void putFloat(Context context, String key, Float val) {
        put(context, key, val);
    }

    /**
     * get float val
     *
     * @param context
     * @param key
     * @return
     */
    public static float getFloat(Context context, String key) {
        return (float) get(context, key, 0.0f);
    }

    /**
     * get float val
     *
     * @param context
     * @param key
     * @param defaultVal
     * @return
     */
    public static float getFloat(Context context, String key, Float defaultVal) {
        return (float) get(context, key, defaultVal);
    }

    /**
     * put long int val
     *
     * @param context
     * @param key
     * @param val
     */
    public static void putLong(Context context, String key, Long val) {
        put(context, key, val);
    }

    /**
     * get long val
     *
     * @param context
     * @param key
     * @return
     */
    public static long getLong(Context context, String key) {
        return (long) get(context, key, 0L);
    }

    /**
     * get long val
     *
     * @param context
     * @param key
     * @param defaultVal
     * @return
     */
    public static long getLong(Context context, String key, Long defaultVal) {
        return (long) get(context, key, defaultVal);
    }

    /**
     * 获取系统设置周的开始日
     *
     * @return
     */
    public static int getWeekStartIndex( String key, int defaultVal) {
        return (int) get(IdoApp.getAppContext(), key, defaultVal);
    }

    /**
     * 获取智能提醒设置状态
     *
     * @return
     */
    public static Object getNoticeOff(Context context, String key, Object object) {

        return (Object) get(context, key, object);
    }

    /**
     * 设置智能提醒设置状态
     *
     * @return
     */
    public static void setNoticeOff(Context context, String key, Object object) {
        put(context, key, object);
    }
    /**
     * 设置智能提醒设置总开关状态
     *
     * @return
     */
    public static boolean getToggleSwitchState(Context context, String key, boolean defaultVal) {
        return (boolean) get(context, key, defaultVal);
    }
    /**
     *
     */
    public static boolean getFirstSportSet(Context context, String key, boolean isFirst) {
        return (Boolean) get(context, key, isFirst);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(Context context, String key) {
        if (mEditor == null) {
            setEditor(context);
        }
        mEditor.remove(key);
        mEditor.commit();
    }


    /**
     * 清除所有的数据
     */
    public static void clear(Context context) {
        if (mEditor == null) {
            setEditor(context);
        }
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * 查询某个key是否存在
     *
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mName, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    private static void setEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mName, Context.MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
    }

    private static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(mName, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }
    }

    private static void put(Context context, String key, Object object) {
        if (mEditor == null) {
            setEditor(context);
        }

        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {
            mEditor.putString(key, object.toString());
        }
        mEditor.commit();
    }


}
