package com.lu.library;

import android.os.Environment;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.music
 * @description: ${TODO}{ 类注释}
 * @date: 2018/6/22 0022
 */

public interface Constant {
    /**
     * APP目录
     */
    String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhihuiju";

    /**
     * 文件存储路径
     */
    String INFO_PATH = APP_ROOT_PATH + "/info";
    String LOG_PATH = APP_ROOT_PATH + "/log";
    String PIC_PATH = APP_ROOT_PATH + "/pic";
    String CACHE_PATH = APP_ROOT_PATH + "/cache";
    String SERVER_PATH = APP_ROOT_PATH + "/server";
    String FILE_PATH = APP_ROOT_PATH + "/file/";
    String CRASH_PATH = LOG_PATH + "/crash/";
    String UPDATE_PATH = LOG_PATH + "/deivce_update/";


    String SHARE_SDK_API_KEY="";


    int REQUEST_CODE_PHOTO_NONE=-1;
    /**
     * 跳转选择相册的code
     */
    int REQUEST_CODE_PHOTO_PICK=2;
    /**
     * 跳转拍照界面的code
     */
    int REQUEST_CODE_PHOTO_CAMERA=1;
    /**
     * 跳转裁剪图片的code
     */
    int REQUEST_CODE_PHOTO_RESOULT=3;
    int REQUEST_CODE_PHOTO_CROP=4;
    int PERMISSIONS_REQUEST_CODE_PHOTO_CAMERA=100;

    /******************** 存储相关常量 ********************/
    /**
     * Byte与Byte的倍数
     */
    int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    int GB = 1073741824;

    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

    /******************** 时间相关常量 ********************/
    /**
     * 毫秒与毫秒的倍数
     */
    int MSEC = 1;
    /**
     * 秒与毫秒的倍数
     */
    int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    int MIN = 60000;
    /**
     * 时与毫秒的倍数
     */
    int HOUR = 3600000;
    /**
     * 天与毫秒的倍数
     */
    int DAY = 86400000;

    public enum TimeUnit {
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    }

    /******************** 正则相关常量 ********************/
    /**
     * 正则：手机号（简单）
     */
    String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    /**
     * 正则：电话号码
     */
    String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * 正则：身份证号码15位
     */
    String REGEX_IDCARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 正则：身份证号码18位
     */
    String REGEX_IDCARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 正则：邮箱
     */
    String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 正则：URL
     */
    String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    /**
     * 正则：汉字
     */
    String REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
     */
    String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 正则：IP地址
     */
    String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * 详情获取数据类型
     */
    int DETAIL_TYPE_DAY = 0;
    int DETAIL_TYPE_WEEK = 1;
    int DETAIL_TYPE_MONTH = 2;
    int DETAIL_TYPE_YEAR = 3;
}
