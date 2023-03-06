package com.lu.library;

import android.content.Context;



/**
 * Created by lyw.
 */
public class LibContext {
    static LibContext libContext=new LibContext();
    private Context context;
    public static LibContext getInstance(){
        return libContext;
    }
    public static Context getAppContext(){
        return libContext.context;
    }
    public void init(Context context){
        this.context=context.getApplicationContext();
    }

}
