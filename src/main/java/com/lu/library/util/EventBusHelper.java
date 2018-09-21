package com.lu.library.util;


import com.lu.library.base.BaseMessage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.util
 * @description: ${TODO}{ EventBus封装类}
 * @date: 2018/8/2 0002
 */
public class EventBusHelper {

    public static void register(Object o){
        EventBus.getDefault().register(o);
    }
    public static void unregister(Object o){
        EventBus.getDefault().unregister(o);
    }
    public static void post(int type){
       post(new BaseMessage<>(type));
    }
    public static void post(BaseMessage message){
        EventBus.getDefault().post(message);
    }
}
