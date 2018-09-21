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
    String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/lu/music";

    /**
     * 文件存储路径
     */
    String INFO_PATH = APP_ROOT_PATH + "/info";
    String LOG_PATH = APP_ROOT_PATH + "/log";
    String PIC_PATH = APP_ROOT_PATH + "/pic";
    String CACHE_PATH = APP_ROOT_PATH + "/cache";
    String FILE_PATH = APP_ROOT_PATH + "/file/";
    String CRASH_PATH = LOG_PATH + "/crash/";


    String SHARE_SDK_API_KEY="";
}
