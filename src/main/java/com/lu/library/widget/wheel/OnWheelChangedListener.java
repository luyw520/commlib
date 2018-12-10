package com.lu.library.widget.wheel;

/**
 * @author: sslong
 * @package: com.veryfit.
 * @description: ${TODO}{一句话描述该类的作用}
 * @date: 2016/5/18 15:54
 */
public interface OnWheelChangedListener {
    /**
     * Callback method to be invoked when current item changed
     *
     * @param wheel    the wheel view whose state has changed
     * @param oldValue the old value of current item
     * @param newValue the new value of current item
     */
    void onChanged(WheelView wheel, int oldValue, int newValue);



}