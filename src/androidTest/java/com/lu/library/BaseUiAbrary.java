package com.lu.library;

import android.graphics.Rect;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.text.TextUtils;

import junit.framework.Assert;

import java.io.File;
import java.util.Random;


/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro
 * @description: ${TODO}{ 类注释}
 * @date: 2018/10/23 0023
 */
public class BaseUiAbrary extends UiaLibrary {
    public static String titleRightId = "layout_right";
    public static String titleLeftId = "layout_left";


    public void clickSet() {
        click(titleRightId, 1000 + CLICK_DELAY);
    }

    public void clickBack() {
        click(titleLeftId);
    }

    /**
     * 删除dirName目录下面的文件
     */
    public void deleteScreenShotByDir() {//截图并命名
        deleteScreenShotFile(dirName);
    }

    public boolean random() {
        Random random = new Random();
        if (random.nextInt(11) % 2 == 0) {
            return true;
        }
        return false;
    }

    public void clickScreen() {
        UiDevice.getInstance().click(100, 100);
    }

    public void clickListViewByResourceId(String id) {
        UiObject listView = getOjectByResourceId(id);
        Rect rect = null;
        try {
            rect = listView.getBounds();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiDevice.getInstance().click(rect.left + 100, rect.top + 100);
    }
    public void clickListView(Class clazz, Class childClazz,int index){
        UiScrollable functionItems = new UiScrollable(new UiSelector().className(clazz));
        Assert.assertEquals(functionItems.exists(), true);
        try {
            functionItems.getChildByInstance(new UiSelector().className(childClazz),index);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void clickListViewByResourceId(String id, int offLeft, int offTop) {
        UiObject listView = getOjectByResourceId(id);
        Rect rect = null;
        try {
            rect = listView.getBounds();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        UiDevice.getInstance().click(rect.left + offLeft, rect.top + offTop);
    }

    public void clickScrollable(Class clazz, Class childClazz, String text) {

        UiScrollable functionItems = new UiScrollable(new UiSelector().className(clazz));
        Assert.assertEquals(functionItems.exists(), true);
        UiObject apps = null;
        try {
            apps = functionItems.getChildByText(new UiSelector().className(childClazz), text);
            apps.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            Assert.assertEquals(false, true);

        }
    }
    public void longClickScrollable(Class clazz, Class childClazz, String text) {

        UiScrollable functionItems = new UiScrollable(new UiSelector().className(clazz));
        Assert.assertEquals(functionItems.exists(), true);
        UiObject apps = null;
        try {
            apps = functionItems.getChildByText(new UiSelector().className(childClazz), text);
            apps.longClick();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            Assert.assertEquals(false, true);

        }
    }

    public void clickWaitExist(String text) {
        UiObject bind = getUiObjectByText(text);
        bind.waitForExists(LAUNCH_TIMEOUT);
        try {
            bind.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置text字符串
     *
     * @param text
     * @param content
     */
    public void setText(String text, String content) {
        try {
            getUiObject(text).setText(content);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置text字符串
     *
     * @param id
     * @param content
     */
    public void setTextById(String id, String content) {
        try {
            getUiObject(id).setText(content);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果控件存在就点击。
     *
     * @param id
     */
    public void clickIfViewExist(String id) {
        clickIfViewExist(id, CLICK_DELAY);
    }

    /**
     * 如果控件存在就点击。
     *
     * @param id
     */
    public void clickIfViewExist(String id, int delay) {
        UiObject uiObject = getUiObject(id, false);
        if (uiObject.exists()) {
            click(id, delay);
        }
    }

    /**
     * 如果控件存在就点击。
     * 不存在就跳过
     * 试用于系统弹出框
     *
     * @param text
     */
    public void clickIfViewExistByText(String text) {
        clickIfViewExist(text);
    }

    public void clickByResourceId(String resourceId) {
        try {
            UiObject oneButton = getOjectByResourceId(resourceId);
            oneButton.click();
            sleep(CLICK_DELAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据text或者desctiption或者id去查找控件
     *
     * @param text
     */
    public void click(String text) {
        click(text, CLICK_DELAY);
    }

    /**
     * 根据text或者desctiption或者id去查找控件
     *
     * @param text
     */
    public void clickWaitExist(String text, boolean time) {
        click(text, CLICK_DELAY);
    }

    /**
     * 根据text或者desctiption或者id去查找控件
     *
     * @param text
     */
    public void clickNow(String text) {
        click(text, CLICK_NOW);
    }

    /**
     * 根据text或者desctiption或者id去查找控件
     *
     * @param text
     */
    public void click(String text, int clickDelay) {
        try {
            UiObject oneButton = getUiObject(text);
            oneButton.click();
            sleep(clickDelay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickAndWaitForNewWindow(String text) {
        try {
            UiObject oneButton = getUiObjectByTextOrDescription(text);
            oneButton.clickAndWaitForNewWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机左滑或者右滑
     *
     * @param resourceId
     * @param step
     */
    public void randomSwipLeftOrRight(String resourceId, int step) {
        if (random()) {
            swipeLeft(resourceId, step);
        } else {
            swipeRight(resourceId, step);
        }
    }

    /**
     * 随机上滑或者下滑
     *
     * @param resourceId
     * @param step
     */
    public void randomSwipUpOrDown(String resourceId, int step) {
        if (random()) {
            //往下划
            swipeDown(resourceId, step);
        } else {
            swipeUp(resourceId, step);
        }
    }

    public void swipeUp(String resourceId, int step) {
        UiObject uiObject = getUiObject(resourceId);
        try {
            uiObject.swipeUp(step);
        } catch (UiObjectNotFoundException e) {
            output("[" + resourceId + "] not found");
            e.printStackTrace();
            assertTrue("[" + resourceId + "] not found", false);
        }
        sleep(CLICK_DELAY);
    }

    public void swipeDown(String resourceId, int step) {
        UiObject uiObject = getUiObject(resourceId);

        try {
            uiObject.swipeDown(step);
        } catch (UiObjectNotFoundException e) {
            output("[" + resourceId + "] not found");
            e.printStackTrace();
            assertTrue("[" + resourceId + "] not found", false);
        }
        sleep(CLICK_DELAY);
    }

    public void swipeDown2(String resourceId, int step) {
        UiObject uiObject = getUiObject(resourceId);

        try {
//            InstrumentationUiAutomatorBridge bridge= (InstrumentationUiAutomatorBridge) ReflectUtil.invokeMethod(UiDevice.getInstance(),"getAutomatorBridge",new Class[]{});

//            UiDevice.getInstance().drag(1500,1500,1800,1800,3);
//            UiDevice.getInstance().swipe(500,500,800,800,3);
            uiObject.swipeDown(step);
        } catch (Exception e) {
            assertTrue("[" + resourceId + "] not found", false);
            e.printStackTrace();
        }
        sleep(CLICK_DELAY);
    }

    public void swipeRight(String resourceId, int step) {
        UiObject uiObject = getUiObject(resourceId);

        try {
            output("[" + resourceId + "]" + "isScrollable:" + uiObject.isScrollable());
            uiObject.swipeRight(step);
        } catch (UiObjectNotFoundException e) {
            assertTrue("[" + resourceId + "] not found", false);
            e.printStackTrace();
        }
        sleep(CLICK_DELAY);
    }

    /**
     * 如果dirName为空，删除dirName目录下的文件。如果不为空，删除screenshotDir目录下的文件
     */
    public void deleteScreenShotFile() {
        if (!TextUtils.isEmpty(dirName)) {
            deleteScreenShotFile(dirName);
            return;
        }
        File file = new File(screenshotDir);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null && file.length() > 0) {

            for (File file1 : files) {
                if (file1.isFile()) {
                    file1.delete();
                }

            }

        }
    }

    public void deleteScreenShotFile(String dir) {
        File file = new File(screenshotDir + dir);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null && file.length() > 0) {

            for (File file1 : files) {
                file1.delete();

            }

        }
    }

}
