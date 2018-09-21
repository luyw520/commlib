package com.lu.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化辅助类
 */
public class DateUtil {


    private static Calendar calendar = null;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MM-dd");

    /**
     * @return 当前格式化的日期yyyy-MM-dd
     */
    public static String getFormatDate2() {

        return simpleDateFormat2.format(new Date());
    }


    /**
     * 根据时分秒组合
     * @param hour 时
     * @param min 分
     * @param second 秒
     * @return  20180611054921 时间格式字符串
     */
    public static long getSportStartTime(int hour, int min, int second) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Date());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(date);
        stringBuffer.append(String.format("%02d", hour));
        stringBuffer.append(String.format("%02d", min));
        stringBuffer.append(String.format("%02d", second));
        return Long.parseLong(stringBuffer.toString());
    }

    /**
     * 返回一个年月日，时分秒为0的Date对象
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year,int month,int day){
        return new Date(year-1900, month - 1, day,0,0,0);
    }
    /**
     * @return 当前格式化的日期yyyy/MM/dd
     */
    public static String getFormatDate() {

        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    /**
     * @return xxxx年xx月
     */
    public static String getYearAndMouth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月");

        return sdf.format(new Date());
    }

    /**
     * @return x月x日
     */
    public static String getMouthAndDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d");

        return sdf.format(new Date());
    }


    public static String computerDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date now = null;
        try {
            date = sdf.parse("2015-11-22");
            now = sdf.parse(sdf.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf((now.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
    }




    /**
     * 时间格式化
     *
     * @param time 66
     * @return 01:06
     */
    public static String computeTime(long time) {

        int hour = (int) (time / 60 / 60);
        StringBuffer result = new StringBuffer();
        if (hour > 0) {
            if (hour < 10) {
                result.append("0");
            }
            time -= hour * 60 * 60;
            result.append(hour + ":");
        }
        int minute = (int) (time / 60);
        if (minute < 10) {
            result.append("0");
        }
        int second = (int) (time - minute * 60);
        result.append(minute + ":");

        if (second < 10) {
            result.append("0");
        }
        result.append(second);

        return result.toString();
    }

    /**
     * 时间格式化
     *
     * @param time
     * @return 5'55''
     */
    public static String computeTimeMS(int time) {
        int minute = time / 60;
        int second = time % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", minute));
        sb.append("\'");
        sb.append(String.format("%02d", second));
        sb.append("\'\'");
        return sb.toString();
    }

    /**
     * 格式化配速
     *
     * @param pace
     * @return
     */
    public static String computeTimePace(String pace) {
        if (pace.contains(".")) {
            String[] paces = pace.split("\\.");
            return String.format("%02d", Integer.parseInt(paces[0])) + "'" + String.format("%02d", Integer.parseInt(paces[1])) + "''";
        } else if (pace.contains(",")){
            String[] paces = pace.split(",");
            return String.format("%02d", Integer.parseInt(paces[0])) + "'" + String.format("%02d", Integer.parseInt(paces[1])) + "''";
        }else {
            return String.format("%02d", 0) + "'" + String.format("%02d", 0) + "''";
        }

    }

    /**
     * 时间格式化
     *
     * @param time 66
     * @return 00:01:06
     */
    public static String computeTimeHMS(long time) {

        int hour = (int) (time / 60 / 60);
        StringBuffer result = new StringBuffer();
        time -= hour * 60 * 60;
        result.append(String.format("%02d", hour));
        result.append(":");
        int minute = (int) (time / 60);
        int second = (int) (time - minute * 60);
        result.append(String.format("%02d", minute));
        result.append(":");
        result.append(String.format("%02d", second));

        return result.toString();
    }

    public static String format2(int year, int mouth, int day) {
        return year + "/" + String.format("%02d", mouth) + "/" + String.format("%02d", day);
    }

    public static String format3(int year, int mouth, int day) {
        return "" + year + String.format("%02d", mouth) + String.format("%02d", day);
    }

    public static String format(int year, int mouth, int day) {
        return year + "-" + String.format("%02d", mouth) + "-" + String.format("%02d", day);
    }

    /**
     * 将一个整数已两位数输出,如果不足两位数,则用0补
     *
     * @param mouth 1
     * @return 01
     */
    public static String format(int mouth) {
        return String.format("%02d", mouth);
    }

    /**
     * 将时间戳格式化
     *
     * @param time 1478482227078
     * @return 1970-01-18 10:41:22
     */
    public static String format(long time) {
        return simpleDateFormat.format(new Date(time));
    }

    public static String format(SimpleDateFormat dateFormat, int year, int month, int day) {
        return dateFormat.format(new Date(year, month, day));
    }

    public static String format(SimpleDateFormat dateFormat, Date date) {
        return dateFormat.format(date);
    }

    public static String formatAdjustDate(SimpleDateFormat dateFormat, Date date) {
//        date.setYear(date.getYear()-1900);
        Date adjustDate = (Date) date.clone();
        adjustDate.setYear(date.getYear() - 1900);
        return dateFormat.format(adjustDate);
    }

    public static boolean isToday(int year, int month, int day) {
        String today = simpleDateFormat2.format(new Date());
        String s = format(year, month, day);
        return today.equals(s);
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
     * 上传服务器的时间
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static  String getUpLoadServiceDate(int year, int month, int day) {
        return String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day) + " 00:00:00";
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
    /**
     * @return 2014-11-18
     */
    public static String getDay() {
        calendar = Calendar.getInstance();
        final StringBuffer buffer = new StringBuffer();

        final int year = calendar.get(Calendar.YEAR);
        if (year < 10) {
            buffer.append("0" + year);
        } else {
            buffer.append(String.valueOf(year));
        }
        buffer.append("-");
        final int mouth = (calendar.get(Calendar.MONTH) + 1);
        if (mouth < 10) {
            buffer.append("0" + mouth);
        } else {
            buffer.append(String.valueOf(mouth));
        }
        buffer.append("-");
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            buffer.append("0" + day);
        } else {
            buffer.append(String.valueOf(day));
        }

        return new String(buffer);
    }

    public static long getLongFromDateStr(String currentTimeMillis) {
        try {
            return simpleDateFormat.parse(currentTimeMillis).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date getDateByHMS(int hour, int minute, int second) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
}
