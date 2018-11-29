package com.lu.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.lu.library.log.LogUtil;

import junit.framework.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class UiaLibrary extends UiAutomatorTestCase {
	public String LINE = "\r\n";

	//	public static UiaLibrary library = null;
//	public static UiaLibrary getInstance() {
//		library = new UiaLibrary();
//		return library;
//	}
	public String screenshotDir="/sdcard/auitest/";

	public   String PACKAGE="com.veryfit2hr.second";
	public   String ID_PRE=PACKAGE+":id/";
	public String FIRST_ACTIVITY="com.ido.veryfitpro.module.bind.WelcomeActivity";
	public static final int CLICK_DELAY = 300;
	public static final int LAUNCH_TIMEOUT = 5000;
	public static final int NET_WORK_DELAY=2000;
	public static final int MAIN_ANIMTOR_DELAY=3000;
	public static final int DIALOG_dURATION=600;
	public static final int CLICK_NOW=100;
	public  String dirName;
	public final static String TAG="UI_TEST_";
	public void swipeLeft() {//左滑
		int y = UiDevice.getInstance().getDisplayHeight();
		int x = UiDevice.getInstance().getDisplayWidth();
		UiDevice.getInstance().swipe(x - 100, y / 2, 100, y / 2, 20);
		sleep(150);
	}

	public void swipeRight() {//右滑
		int y = UiDevice.getInstance().getDisplayHeight();
		int x = UiDevice.getInstance().getDisplayWidth();
		UiDevice.getInstance().swipe(100, y / 2, x - 100, y / 2, 20);
		sleep(150);
	}

	public void swipeDown() {//下滑
		int y = UiDevice.getInstance().getDisplayHeight();
		int x = UiDevice.getInstance().getDisplayWidth();
		UiDevice.getInstance().swipe(x / 2, 200, x / 2, y - 200, 20);
		sleep(150);
	}

	public void swipeUp() {//上滑
		int y = UiDevice.getInstance().getDisplayHeight();
		int x = UiDevice.getInstance().getDisplayWidth();
		UiDevice.getInstance().swipe(x / 2, y - 200, x / 2, 200, 20);
		sleep(150);
	}

	public void swipUpLittle() {//上滑一点点
		int x = UiDevice.getInstance().getDisplayWidth() / 2;
		int y = UiDevice.getInstance().getDisplayHeight() / 2;
		UiDevice.getInstance().swipe(x, y + 150, x, y - 150, 20);
		sleep(150);
	}

	public void swipDownLittle() {//下拉一点点
		int x = UiDevice.getInstance().getDisplayWidth() / 2;
		int y = UiDevice.getInstance().getDisplayHeight() / 2;
		UiDevice.getInstance().swipe(x, y - 150, x, y + 150, 20);
		sleep(150);
	}

	public String getNow() {//获取当前时间
		Date time = new Date();
		SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String c = now.format(time);
		return c;
	}


	//压缩图片
	public void compressPictureToJpeg(String oldPath, File newFile) throws FileNotFoundException {
		Bitmap bitmap = BitmapFactory.decodeFile(oldPath);//创建并实例化bitmap对象
		FileOutputStream out = new FileOutputStream(newFile);//创建文件输出流
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);//将图片转化为jpeg格式输出
	}

	//截取某个控件的图像
	public Bitmap getBitmapByResourceId(String id) throws UiObjectNotFoundException {
		Rect rect = getUiObjectByResourceId(id).getVisibleBounds();//获取控件的rect对象
		String path = screenShot("test");//截图
		Bitmap bitmap = BitmapFactory.decodeFile(path);//创建并实例化bitmap对象
		bitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());//截取bitmap实例
		return bitmap;
	}

	//获取某一坐标点的颜色值
	public int getColorPixel(int x, int y) {
		screenShot("test");//截图
		String path = "/mnt/sdcard/123/test.png";
		Bitmap bitmap = BitmapFactory.decodeFile(path);//新建并实例化bitmap对象
		int color = bitmap.getPixel(x, y);//获取坐标点像素颜色
//		output(color);//输出颜色值
		return color;
	}

	public int getRedPixel(int x, int y) {
		screenShot("test");//截图
		String path = "/mnt/sdcard/123/test.png";
		Bitmap bitmap = BitmapFactory.decodeFile(path);//新建并实例化bitmap对象
		int color = bitmap.getPixel(x, y);//获取坐标点像素颜色
//		output(color);//输出颜色值
		int red = Color.red(color);
		return red;
	}

	public int getGreenPixel(int x, int y) {
		screenShot("test");//截图
		String path = "/mnt/sdcard/123/test.png";
		Bitmap bitmap = BitmapFactory.decodeFile(path);//新建并实例化bitmap对象
		int color = bitmap.getPixel(x, y);//获取坐标点像素颜色
//		output(color);//输出颜色值
		int green = Color.green(color);
		return green;
	}

	public int getBluePixel(int x, int y) {
		screenShot("test");//截图
		String path = "/mnt/sdcard/123/test.png";
		Bitmap bitmap = BitmapFactory.decodeFile(path);//新建并实例化bitmap对象
		int color = bitmap.getPixel(x, y);//获取坐标点像素颜色
//		output(color);//输出颜色值
		int blue = Color.blue(color);
		return blue;
	}

	public int[] getRGBcolorPixel(int x, int y) {
		screenShot("testDemo");
		String path = "/mnt/sdcard/123/testDemo.png";
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		int color = bitmap.getPixel(x, y);
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);
		int[] rgb = {red, green, blue};
		return rgb;
	}

	//根据颜色判断状态
	public boolean isBlue(UiObject uiObject) throws UiObjectNotFoundException {
		screenShot("test");//截图
		String path = "/mnt/sdcard/123/test.png";
		Bitmap bitmap = BitmapFactory.decodeFile(path);//新建并实例化bitmap对象
		Rect rect = uiObject.getVisibleBounds();
		int x = rect.left;
		int xx = rect.right;
		int y = rect.top;
		int yy = rect.bottom;
		List<Integer> blueColor = new ArrayList<Integer>();
		for (int i = x; i < xx; i++) {
			for (int k = y; k < yy; k++) {
				int color = bitmap.getPixel(i, k);//获取坐标点像素颜色
				int red = Color.blue(color);
				blueColor.add(red);
			}
		}
		int sum = 0;
		for (int i = 0; i < blueColor.size(); i++) {
			sum += blueColor.get(i);
		}
//		output(sum/blueColor.size());
		return sum / blueColor.size() > 200 ? true : false;
	}

	/*
	 * 图像对比
	 * 默认图像宽高一致
	 */
	public boolean comparePicture(String path1, String path2, double limit) {
		Bitmap bitmap1 = BitmapFactory.decodeFile(path1);//创建并初始化bitmap对象
		Bitmap bitmap2 = BitmapFactory.decodeFile(path2);//创建并初始化bitmap对象
		int width = bitmap1.getWidth();//获取宽
		int height = bitmap1.getHeight();//获取高
		int total = 0;//统计相同次数
		int times = 0;//统计总次数
		//遍历像素点的颜色值，节省时间每次递增5个像素点
		for (int x = 0; x < width; x += 3) {
			for (int y = 0; y < height; y += 3) {
				int oldPic = bitmap1.getPixel(x, y);//获取颜色值
				int newPic = bitmap2.getPixel(x, y);//获取颜色值
//				int differ = Math.abs(ss - dd);//计算绝对差
				times++;
				if (oldPic == newPic) {//如果相等，则认为相同
					total++;
				}
			}
		}
		double differ = total * 1.0 / times;
		output(differ);
		return differ > 0.99 ? true : false;//返回统计结果
	}

	//获取视频播放进度条
	public double getVideoProgress(Bitmap bitmap) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		List<Integer> date = new ArrayList<Integer>();
		for (int i = 0; i < width; i++) {
			int color = bitmap.getPixel(i, height / 2);
			int red = Color.red(color);
//			output(red);
			date.add(red);
		}
		int date1 = 0, date2 = 0, date3 = 0, date4 = 0;
		int status1 = 0, status2 = 0;
		for (int i = 1; i < date.size() - 1; i++) {
			if (date.get(i) == 255 && status1 == 0) {
				status1++;
				date1 = i;
			}
			if (date.get(i) == 238 && status2 == 0) {
				status2++;
				date2 = i;
			}
			if (date.get(i + 1) < 238 && date.get(i) == 238) {
				date3 = i;
			}
			if (date.get(i + 1) < 165 && date.get(i) == 165) {
				date4 = i;
			}
		}
//		output(date1, date2, date3, date4);
//		output((date2 + date3 - date1 * 2.00)/(date4 - date1)/2);
		return (date2 + date3 - date1 * 2.00) / (date4 - date1) / 2;
	}

	public String getDayHourMinute() {//获取日期小时分钟
		Date time = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-HH-mm");
		String name = format.format(time);
		return name;
	}
	public String screenShotByDir(String name) {//截图并命名
		return screenShot(name,dirName);
	}

	/**
	 * 如果dirName存在，则截图在dirName目标下，否则截图在screenshotDir目标下
	 * @param name
	 * @return
	 */
	public String screenShot(String name) {//截图并命名
		LogUtil.d2(TAG+name);
		if (!TextUtils.isEmpty(dirName)){
			return screenShotByDir(name);
		}
		return screenShot(name,null);
	}
	public String screenShot(String name,String dir) {//截图并命名
		File file ;
		if (TextUtils.isEmpty(dir)){
			file = new File(screenshotDir);
		}else{
			file = new File(screenshotDir+dir);
		}

		if (!file.exists()) {
			file.mkdirs();
		}
		File files = new File(file , name + ".png");
		if (files.exists()){
			files.delete();
		}
		UiDevice.getInstance().takeScreenshot(files);
		output(name + ".png 截图成功！");
		String path = files.getAbsolutePath();
		return path;
	}


	public void circle(int x, int y, int r) {//画圆的方法
		double d = (double) (Math.PI / 30);//角度
		double[] xxx = new double[61];
		for (int i = 0; i < 61; i++) {
			xxx[i] = Math.cos(i * d);
		}
		//获取x坐标
		double[] yyy = new double[61];
		for (int i = 1; i < 61; i++) {
			yyy[i] = Math.sin(i * d);
		}
		//获取y坐标
		int[] xxx1 = new int[61];
		for (int i = 0; i < 61; i++) {
			xxx1[i] = (int) (xxx[i] * 200);
		}
		//转化坐标值类型
		int[] yyy1 = new int[61];
		for (int i = 0; i < 61; i++) {
			yyy1[i] = (int) (yyy[i] * 200);
		}
		//转化坐标值类型
		Point[] p = new Point[61];
		for (int i = 0; i < 61; i++) {
			p[i] = new Point();
			p[i].x = xxx1[i] + x;
			p[i].y = y - yyy1[i] + 50;
		}
		//建立点数组
		UiDevice.getInstance().swipe(p, 2);
	}

	public void heart(int x, int y, int r) {//画心形的方法
		double d = (double) (Math.PI / 30);
		double[] angle = new double[61];//设置角度差
		for (int i = 0; i < 61; i++) {
			angle[i] = i * d;
		}
		//建立一个角度差double数组
		double[] ox = new double[61];
		for (int i = 0; i < 61; i++) {
			ox[i] = r * (2 * Math.cos(angle[i]) - Math.cos(2 * angle[i]));
		}
		//计算x坐标
		double[] oy = new double[61];
		for (int i = 0; i < 61; i++) {
			oy[i] = r * (2 * Math.sin(angle[i]) - Math.sin(2 * angle[i]));
		}
		//计算y坐标
		Point[] heart = new Point[61];
		for (int i = 0; i < 61; i++) {
			heart[i] = new Point();
			heart[i].x = (int) oy[i] + x;
			heart[i].y = -(int) ox[i] + y;
		}
		//建立一个点数组，这里坐标一定要转化一下，不然是倒着的心形
		UiDevice.getInstance().swipe(heart, 2);
	}

	public UiObject getUiObjectByText(String text) {//通过文本获取控件
		return new UiObject(new UiSelector().text(text));
	}

	public UiObject getUiObjectByTextContains(String text) {
		return new UiObject(new UiSelector().textContains(text));
	}
	public UiObject getOjectByResourceId(String resourceId){
		UiObject uiObject=new UiObject(new UiSelector().resourceId(ID_PRE+resourceId));
		if (!uiObject.exists()){
			output("resourceId cannot find ["+ID_PRE+resourceId+"]");
		}

		return uiObject;
	}
	public UiObject getUiObject(String res){

		return getUiObject(res,true);
	}
	public UiObject getUiObject(String res, boolean isAssertExist){
		UiObject oneButton = getUiObjectByTextOrDescription(res);
		if (!oneButton.exists()){
			output("getUiObjectByTextOrDescription cannot find ["+res+"]");
			oneButton=getOjectByResourceId(res);
		}
		if (isAssertExist) {
			Assert.assertEquals(oneButton.exists(), true);
		}
		return oneButton;
	}
	public UiObject getChild(String parent, String child){
		UiObject gridview=getUiObject(parent);
		UiObject uiObject=null;
		try {
			uiObject= gridview.getChild(new UiSelector().text(child));
			if (!uiObject.exists()){
				uiObject=gridview.getChild(new UiSelector().description(child));
			}
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
		return uiObject;
	}
	public UiObject getUiObjectWaitIfExist(String res, int waitTime){
		UiObject oneButton = getUiObjectByTextOrDescription(res);
		if (!oneButton.exists()){
			oneButton=getOjectByResourceId(res);
		}
		if (!oneButton.exists()){
			oneButton.waitForExists(waitTime);
		}
		return oneButton;
	}
	public UiObject getUiObjectByTextWaitIfExist(String res, int waitTime){
		UiObject oneButton = getUiObjectByText(res);
		if (!oneButton.exists()){
			oneButton.waitForExists(waitTime);
		}
		return oneButton;
	}

	public void swipeLeft(String resourceId,int step){
		UiObject uiObject=getUiObject(resourceId);

//		assertTrue("["+resourceId+"] not found", false);
		try {
			output("["+resourceId+"]"+"isScrollable:"+uiObject.isScrollable());
			uiObject.swipeLeft(step);
		} catch (UiObjectNotFoundException e) {

			e.printStackTrace();
		}
		sleep(CLICK_DELAY);
	}
	//通过text开始文字查找控件
	public UiObject getUiObjectByStartText(String text) {
		return new UiObject(new UiSelector().textStartsWith(text));
	}

	public UiObject getUiObjectByStartDesc(String desc) {
		return new UiObject(new UiSelector().descriptionStartsWith(desc));
	}

	public UiObject getUiObjectByTextClassName(String text, String classname) {//通过文本和类名获取控件
		return new UiObject(new UiSelector().text(text).className(classname));
	}

	public UiObject getUiObjectByTextResourceId(String text, String id) {//通过文本和id获取对象
		return new UiObject(new UiSelector().text(text).resourceId(id));
	}

	public UiObject getUiObjectByResourceIdClassName(String id, String type) {
		return new UiObject(new UiSelector().resourceId(id).className(type));
	}
	public UiObject getUiObjectByTextOrDescription(String text) {
		UiObject uiObject = new UiObject(new UiSelector().text(text));
		boolean textExist=uiObject.exists();
		if (!textExist){
			uiObject = new UiObject(new UiSelector().description(text));
		}
//		textExist=uiObject.exists();
//		assertTrue("["+text+"] view not found", textExist);
		return uiObject;
	}

	public UiObject getUiObjectByResourceId(String id) {//通过资源ID获取控件
		return getOjectByResourceId(id);
	}

	public UiObject getUiObjectByDesc(String desc) {//通过desc获取控件
		return new UiObject(new UiSelector().description(desc));
	}

	public UiObject getUiObjectByStartDescContains(String desc) {
		return new UiObject(new UiSelector().descriptionContains(desc));
	}

	public UiObject getUiObjectByDescContains(String desc) {
		return new UiObject(new UiSelector().descriptionContains(desc));
	}

	public UiObject getUiObjectByClassName(String type) {//通过classname获取控件
		return new UiObject(new UiSelector().className(type));
	}

	public UiObject getUiObjectByResourceIdIntance(String id, int instance) {//通过id和instance获取控件
		return new UiObject(new UiSelector().resourceId(id).instance(instance));
	}

	//长按控件
	public void longclickUiObectByResourceId(String id) throws UiObjectNotFoundException {
		int x = getUiObjectByResourceId(id).getBounds().centerX();
		int y = getUiObjectByResourceId(id).getBounds().centerY();
		UiDevice.getInstance().swipe(x, y, x, y, 300);//最后一个参数单位是5ms
	}

	public void longclickUiObectByDesc(String desc) throws UiObjectNotFoundException {
		int x = getUiObjectByDesc(desc).getBounds().centerX();
		int y = getUiObjectByDesc(desc).getBounds().centerY();
		UiDevice.getInstance().swipe(x, y, x, y, 300);//最后一个参数单位是5ms
	}

	public void longclickUiObectByText(String text) throws UiObjectNotFoundException {
		int x = getUiObjectByText(text).getBounds().centerX();
		int y = getUiObjectByText(text).getBounds().centerY();
		UiDevice.getInstance().swipe(x, y, x, y, 300);//最后一个参数单位是5ms
	}

	//点击中心
	public void clickCenter() {
		int x = UiDevice.getInstance().getDisplayWidth();
		int y = UiDevice.getInstance().getDisplayHeight();
		clickPiont(x / 2, y / 2);
	}

	public void writeText(String text) throws UiObjectNotFoundException {//输入文字
//		getUiObjectByClassName("android.widget.EditText").setText(Utf7ImeHelper.e(text));
	}

	public UiScrollable getUiScrollabe() {//获取滚动控件
		return new UiScrollable(new UiSelector().scrollable(true));
	}

	public UiScrollable getUiScrollableByResourceId(String id) {//获取滚动对象
		return new UiScrollable(new UiSelector().scrollable(true).resourceId(id));
	}

	public void getChildByTextOfUiScrollableByClassName(String type, String text) throws UiObjectNotFoundException {
		getScrollableByClassName(type).getChildByText(new UiSelector().text(text), text).clickAndWaitForNewWindow();
	}

	public UiObject getUiObjectByResourIdIndex(String id, int index) {//通过ID和index获取控件
		return new UiObject(new UiSelector().resourceId(id).index(index));
	}

	public void randomClickOpiton() throws UiObjectNotFoundException {
		int num = getUiObjectByClassName("android.widget.ListView").getChildCount();
		int i = new Random().nextInt(num);
		getUiObjectByResourceIdIntance("com.gaotu100.superclass:id/simpleitemview_left_text", i).clickAndWaitForNewWindow();
	}

	public void outputBegin(String text) {//输出开始
		LogUtil.d2(TAG+text + "..-. ...- 测试开始！");
	}

	public void outputNow() {//输出当前时间
		System.out.println(getNow());
	}

	public void outputOver(String text) {//输出结束
		LogUtil.d2(TAG+text + "..-. ...- 测试结束！");
	}
	private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//明显输出
	public void output(String text) {
//		System.out.println(text);
//		Log.i("UI_TEST",text);
		writeLog(text);
		LogUtil.d2(TAG+text);
	}
	//明显输出
	public void outputStart(int index,String text) {
//		System.out.println(text);
//		Log.i("UI_TEST",text);
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(TAG);
		for (int i=1;i<index;i++){
			stringBuffer.append(" ");
		}
		for (int i=0;i<10-index;i++){
			stringBuffer.append("*");
		}
		stringBuffer.append(text);
		stringBuffer.append("开始");
		for (int i=0;i<10-index;i++){
			stringBuffer.append("*");
		}
		writeLog(stringBuffer.toString());
		LogUtil.d2(stringBuffer.toString());
	}
	private static boolean writeStringToFile(String path, String data){
		boolean isSuccess = true;
		File file = new File(path);
		if (!file.exists()){
			try {
				isSuccess = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				isSuccess = false;
			}
		}
		if (isSuccess) {
			try {
				FileWriter writer = new FileWriter(path,true);
				writer.write(data);
				writer.write("\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				isSuccess = false;
			}
		}

		return isSuccess;
	}
	public void writeLog(String log){
		log +="["+myLogSdf.format(new Date())+"]"+log;
		writeStringToFile(new File(screenshotDir,"ui_result.txt").getAbsolutePath(),log);
	}
	void deleteLogFile(){
		new File(screenshotDir,"ui_result.txt").delete();
	}
	//明显输出
	public void outputEnd(int index,String text) {
//		System.out.println(text);
//		Log.i("UI_TEST",text);
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append(TAG);
		for (int i=1;i<index;i++){
			stringBuffer.append(" ");
		}
		for (int i=0;i<5-index;i++){
			stringBuffer.append("*");
		}
		stringBuffer.append(text);
		stringBuffer.append("结束");
		writeLog(stringBuffer.toString());
		LogUtil.d2(stringBuffer.toString());
	}


	public void output(int... num) {//方法重载
		for (int i = 0; i < num.length; i++) {
			LogUtil.d2("第" + (i + 1) + "个：" + num[i]);
		}
	}

	public void output(Object... object) {
		for (int i = 0; i < object.length; i++) {
			LogUtil.d2("第" + (i + 1) + "个：" + object[i]);
		}
	}

	public void output(Object object) {
		LogUtil.d2(TAG+object.toString());
	}

	public void pressTimes(int keyCode, int times) {//对于一个按键按多次
		for (int i = 0; i < times; i++) {
			sleep(200);
			UiDevice.getInstance().pressKeyCode(keyCode);
		}
	}

	public void waitForUiObjectByText(String text) {//等待对象出现
//		Date start = new Date();
//		boolean key = true;
//		while(key){
//			sleep(200);
//			UiObject it = new UiObject(new UiSelector().text(text));
//			if (it.exists()) {
//				key = false;
//			}
//			Date end = new Date();
//			long time = end.getTime() - start.getTime();
//			if (time>10000) {
//				output("超过10s没有出现！");
//				key = false;
//			}
//		}
		getUiObjectByText(text).waitForExists(10000);
	}

	public void waitForUiObjectByStartText(String text) {
		getUiObjectByStartText(text).waitForExists(10000);
	}

	//输出时间差
	public void outputTimeDiffer(Date start, Date end) {
		long time = end.getTime() - start.getTime();
		double differ = (double) time / 1000;
		output("总计用时" + differ + "秒！");
	}

	//获取子控件点击
	public void getScrollChildByText(String text) throws UiObjectNotFoundException {
		UiObject child = getUiScrollabe().getChildByText(new UiSelector().text(text), text);
		child.clickAndWaitForNewWindow();
	}

	//通过classname获取滚动控件
	public UiScrollable getScrollableByClassName(String type) {
		return new UiScrollable(new UiSelector().scrollable(true).className(type));
	}

	public void waitForUiObjectByClassName(String type) throws UiObjectNotFoundException {//等待控件出现
		getUiObjectByClassName(type).waitForExists(10000);
	}

	public void waitForUiObjectByTextContains(String text) {//等待对象出现
//		Date start = new Date();
//		boolean key = true;
//		while(key){
//			sleep(1000);
//			UiObject it = new UiObject(new UiSelector().textContains(text));
//			if (it.exists()) {
//				key = false;
//			}
//			Date end = new Date();
//			long time = end.getTime() - start.getTime();
//			if (time>10000) {
//				output("超过10s没有出现！");
//				key = false;
//			}
//		}
		getUiObjectByText(text).waitForExists(10000);
	}

	public void waitForUiObjectByDesc(String desc) {//等待对象出现
//		Date start = new Date();
//		boolean key = true;
//		while(key){
//			sleep(1000);
//			UiObject it = new UiObject(new UiSelector().description(desc));
//			if (it.exists()) {
//				key = false;
//			}
//			Date end = new Date();
//			long time = end.getTime() - start.getTime();
//			if (time>10000) {
//				output("超过10s没有出现！");
//				key = false;
//			}
//		}
		getUiObjectByDesc(desc).waitForExists(10000);
	}

	public void waitForUiObjectByResourceId(String id) {//等待对象出现
//		Date start = new Date();
//		boolean key = true;
//		while(key){
//			sleep(1000);
//			UiObject it = new UiObject(new UiSelector().resourceId(id));
//			if (it.exists()) {
//				key = false;
//			}
//			Date end = new Date();
//			long time = end.getTime() - start.getTime();
//			if (time>10000) {
//				output("超过10s没有出现！");
//				key = false;
//			}
//		}
		getUiObjectByResourceId(id).waitForExists(10000);
	}

	public void waitForUiObject(UiSelector selector) {//等待对象出现
//		Date start = new Date();
//		boolean key = true;
//		while(key){
//			sleep(1000);
//			UiObject it = new UiObject(selector);
//			if (it.exists()) {
//				key = false;
//				}
//			Date end = new Date();
//			long time = end.getTime() - start.getTime();
//			if (time>10000) {
//				output("超过10秒没有出现！");
//				key = false;
//				}
//			}
		new UiObject(selector).waitForExists(10000);
	}

	public String getTextByResourceId(String id) throws UiObjectNotFoundException {
		return getUiObjectByResourceId(id).getText();
	}

	public String getDescByResourceI1d(String id) throws UiObjectNotFoundException {
		return getUiObjectByResourceId(id).getContentDescription();
	}

	public String getTextByResourceIdClassName(String id, String type) throws UiObjectNotFoundException {
		return getUiObjectByResourceIdClassName(id, type).getText();
	}

	//获取兄弟控件的文本
	public String getTextByBrother(String myid, String brotherid) throws UiObjectNotFoundException {
		return getUiObjectByResourceId(myid).getFromParent(new UiSelector().resourceId(brotherid)).getText();
	}

	public void writeTextByResourceId(String id, String text) throws UiObjectNotFoundException {
//		getUiObjectByResourceId(id).setText(Utf7ImeHelper.e(text));
	}

	public void clickPiont(int x, int y) {//点击某一个点
		UiDevice.getInstance().click(x, y);
	}

	public void getUiObjectByResoureIdAndclickRightHalf(String id) throws UiObjectNotFoundException {
		//获取控件大小
		Rect sss = getUiObjectByResourceId(id).getBounds();
		//计算中心偏移量
		clickPiont(sss.centerX() + sss.width() / 4, sss.centerY());
	}

	//点击控件左半边
	public void getUiObjectByResoureIdAndclickLeftHalf(String id) throws UiObjectNotFoundException {
		//获取控件大小
		Rect sss = getUiObjectByResourceId(id).getBounds();
		//计算中心偏移量
		clickPiont(sss.centerX() - sss.width() / 4, sss.centerY());
	}

	public void setShort() {//设置短等待
		Configurator.getInstance().setActionAcknowledgmentTimeout(500);
	}

	public void setFast() {//设置短等待
		Configurator.getInstance().setActionAcknowledgmentTimeout(100);
	}

	public void setLong() {//设置长等待
		Configurator.getInstance().setActionAcknowledgmentTimeout(1500);
	}

	//清除中文文本
	public void clearTextByResourceId(String id) throws UiObjectNotFoundException {
		String name = getUiObjectByResourceId(id).getText();
//		output(name.length());
		UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_MOVE_END);
//		UiDevice.getInstance().pressKeyCode(KeyEvent.KEYCODE_MOVE_HOME);
		//如果光标在最后
		pressTimes(KeyEvent.KEYCODE_DEL, name.length());
		//如果光标在最开始
//		pressTimes(KeyEvent.KEYCODE_FORWARD_DEL, name.length());
	}

	//把string类型转化为int
	public int changeStringToInt(String text) {
//		return Integer.parseInt(text);
		return Integer.valueOf(text);
	}

	//等待文本控件并点击
	public void waitForClassNameAndClick(String type) throws UiObjectNotFoundException {
		waitForUiObjectByClassName(type);
//		getUiObjectByText(type).waitForExists(10000);
		getUiObjectByClassName(type).clickAndWaitForNewWindow();
	}

	public void waitForTextAndClick(String text) throws UiObjectNotFoundException {
		waitForUiObjectByText(text);
//		getUiObjectByText(text).waitForExists(10000);
		getUiObjectByText(text).clickAndWaitForNewWindow();
	}

	//通过开始文字查找控件并点击
	public void waitForStartTextAndClick(String text) throws UiObjectNotFoundException {
		getUiObjectByStartText(text).waitForExists(10000);
		getUiObjectByStartText(text).clickAndWaitForNewWindow();
	}

	public void waitForTextContainsAndClick(String text) throws UiObjectNotFoundException {
		getUiObjectByTextContains(text).waitForExists(10000);
		getUiObjectByTextContains(text).clickAndWaitForNewWindow();
	}

	public void waitForStartDescAndClick(String desc) throws UiObjectNotFoundException {
		getUiObjectByStartDesc(desc).waitForExists(10000);
		getUiObjectByStartDesc(desc).clickAndWaitForNewWindow();
	}

	public void waitForDescContainsAndClick(String desc) throws UiObjectNotFoundException {
		getUiObjectByDescContains(desc).waitForExists(10000);
		getUiObjectByDescContains(desc).clickAndWaitForNewWindow();
	}

	//等待资源id并点击
	public void waitForResourceIdAndClick(String id) throws UiObjectNotFoundException {
		waitForUiObjectByResourceId(id);
//		getUiObjectByResourceId(id).waitForExists(10000);
		getUiObjectByResourceId(id).clickAndWaitForNewWindow();
	}

	//等待desc并点击
	public void waitForDescAndClick(String desc) throws UiObjectNotFoundException {
		waitForUiObjectByDesc(desc);
		getUiObjectByDesc(desc).clickAndWaitForNewWindow();
	}

	//打开APP
	public void startClassApp() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("am start -n com.gaotu100.superclass/.activity.main.SplashActivity").waitFor();
	}

	public void startWechat() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("am start -n com.tencent.mm/.ui.LauncherUI").waitFor();
	}

	//关闭APP
	public void stopClassApp() throws InterruptedException, IOException {
		sleep(500);
		Runtime.getRuntime().exec("am force-stop com.gaotu100.superclass").waitFor();
		sleep(500);
	}

	//打开alertover
	public void stopAlertover() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("am force-stop com.alertover.app").waitFor();
		sleep(500);
	}

	//关闭alertover
	public void startAlertover() throws IOException, InterruptedException {
		sleep(500);
		Runtime.getRuntime().exec("am start -n com.alertover.app/.activity.LoginActivity").waitFor();
	}

	//打开或者关闭wifi
	public void closeOrOpenWifi() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("am start -n run.wifibutton/.WifiButtonActivity").waitFor();
		sleep(1000);
	}

	//关闭微信
	public void stopWechat() throws InterruptedException, IOException {
		sleep(500);
		Runtime.getRuntime().exec("am force-stop com.tencent.mm").waitFor();
		Thread.sleep(500);
	}

	//关闭支付宝
	public void stopAlipay() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("am force-stop com.eg.android.AlipayGphone").waitFor();
		Thread.sleep(500);
	}

	//打开APP
	public void startWishApp(String packageName,String activity) throws IOException {
		Runtime.getRuntime().exec("am start -n "+packageName+"/"+activity);
	}
	public void clickIdAndStartActivity(String id){
		UiObject uiObject=getOjectByResourceId(id);
		try {
			uiObject.click();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
		sleep(CLICK_DELAY);
	}
	public void clickAndStartActivity(String text){
		UiObject uiObject=getUiObjectByText(text);
		try {
			uiObject.click();
		} catch (UiObjectNotFoundException e) {
			e.printStackTrace();
		}
		sleep(CLICK_DELAY);
	}
	//关闭APP
	public void stopWishApp() throws InterruptedException, IOException {
		sleep(500);
		Runtime.getRuntime().exec("am force-stop com.chaojizhiyuan.superwish").waitFor();
		sleep(500);
	}

	//获取随机数
	public int getRandomInt(int num) {
		return new Random().nextInt(num);
	}

	//验证跳转支付宝
	public void verifySkipAlipay() {
		waitForUiObjectByText("添加银行卡付款");
		String packagename = UiDevice.getInstance().getCurrentPackageName();
		assertEquals("跳转支付宝失败！", "com.eg.android.AlipayGphone", packagename);
	}

	//验证跳转支付宝
	public void verifySkipWechat() {
		waitForUiObjectByText("使用零钱支付");
		String packagename = UiDevice.getInstance().getCurrentPackageName();
		assertEquals("跳转微信失败！", "com.tencent.mm", packagename);
	}

	//屏幕提醒
	public void warningTester() throws RemoteException {
		UiDevice.getInstance().sleep();//灭屏
		sleep(1200);//休眠
		if (UiDevice.getInstance().isScreenOn()) {//获取屏幕状态
			return;//如果亮屏状态则结束运行
		} else {
			UiDevice.getInstance().wakeUp();//如果的灭屏状态则重新运行本方法
			warningTester();//递归
		}
	}

	//向前滚动
	public boolean scrollForward() throws UiObjectNotFoundException {
		return getUiScrollabe().scrollForward(50);
	}

	//向后滚动
	public boolean scrollBackward() throws UiObjectNotFoundException {
		return getUiScrollabe().scrollBackward(50);
	}

	public void deleteScreenShot() {//删除截图文件夹
		File file = new File("/mnt/sdcard/123/");
		if (file.exists()) {//如果file存在
			File[] files = file.listFiles();//获取文件夹下文件列表
			for (int i = 0; i < files.length; i++) {//遍历删除
				files[i].delete();
			}
			file.delete();//最后删除文件夹，如果不存在直接删除文件夹
		} else {
			output("文件夹不存在！");
		}

	}
	public void pressBack(){
		UiDevice.getInstance().pressBack();
	}
	public String formatTime(long time){
		return String.format("%02dmin %02ds",time/1000/60,time/1000%60);
	}
}
