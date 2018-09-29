package com.lu.library.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化辅助类
 */
public class DateUtil {


    private static Calendar calendar = null;
    public static String format(String pattern){
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        return format.format(new Date());
    }


    public static String format(int year, int mouth, int day,String split) {
        return year + split + String.format("%02d", mouth) + split + String.format("%02d", day);
    }


    public static int[] todayYearMonthDay() {
        calendar = Calendar.getInstance();

        int[] date = new int[3];
        date[0] = calendar.get(Calendar.YEAR);
        date[1] = calendar.get(Calendar.MONTH) + 1;
        date[2] = calendar.get(Calendar.DAY_OF_MONTH);
        return date;
    }
    public static int getTodayHour(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    public static int getTodayMin(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * @param dateStr 2016-07-26
     * @return [2016, 7, 26]
     */
    public static int[] yearMonthDay(String dateStr) {

        String[] s = dateStr.split("-");
        int[] date = new int[3];
        for (int i = 0; i < s.length && i < date.length; i++) {
            date[i] = Integer.parseInt(s[i]);
        }
        return date;
    }


    /**
     * 判断时间是否在时间段内
     *
     * @param date         当前时间 yyyy-MM-dd HH:mm:ss
     * @param strDateBegin 开始时间 00:00:00
     * @param strDateEnd   结束时间 00:05:00
     * @return
     */
    public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String strDate = sdf.format(date);   //2016-12-16 11:53:54
    // 截取当前时间时分秒 转成整型
        int tempDate = Integer.parseInt(strDate.substring(11, 13) + strDate.substring(14, 16) + strDate.substring(17, 19));
    // 截取开始时间时分秒  转成整型
        int tempDateBegin = Integer.parseInt(strDateBegin.substring(0, 2) + strDateBegin.substring(3, 5) + strDateBegin.substring(6, 8));
    // 截取结束时间时分秒  转成整型
        int tempDateEnd = Integer.parseInt(strDateEnd.substring(0, 2) + strDateEnd.substring(3, 5) + strDateEnd.substring(6, 8));

        if ((tempDate >= tempDateBegin && tempDate <= tempDateEnd)) {
            return true;
        } else {
            return false;
        }
    }
}
