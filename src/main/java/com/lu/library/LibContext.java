package com.lu.library;

import android.content.Context;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.lu.library
 * @description: ${TODO}{ 类注释}
 * @date: 2018/9/21 0021
 */
public class LibContext {
    static LibContext libContext=new LibContext();
    public static LibContext getInstance(){
        return libContext;
    }
    public static Context getAppContext(){
        return null;
    }
}
