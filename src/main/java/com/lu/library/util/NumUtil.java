package com.lu.library.util;

import java.math.BigDecimal;

public class NumUtil {

    /**
     * 把浮点数转换成字符串
     *
     * @param num
     * @param len
     * @return
     */
    public static String float2String(float num, int len) {
        String newNum = num + "";
        int index = getIsNumIndex(newNum);
        String firstNum = newNum.substring(0, index);
        String secondNum = newNum.substring(index + 1, newNum.length());
        int length = secondNum.length();
        len = len < 0 ? 0 : len;
        int surplusLen = length - len;
        System.out.println("firstNum=" + firstNum + ",secondNum=" + secondNum
                + ",surplusLen=" + surplusLen);
        if (surplusLen > 0) {
            secondNum = secondNum.substring(0, len);
        } else if (surplusLen < 0) {
            for (int i = 0; i < (-surplusLen); i++) {
                secondNum = secondNum + "0";
            }
        }

        if (len > 0) {
            return firstNum + "." + secondNum;
        } else {
            return firstNum;
        }

    }

    /**
     * 判断一个字符串是不是0~9的数字
     *
     * @param str
     * @return
     */
    public static boolean strIsChangeInt(String str) {
        return str.matches("[0-9]+");
    }

    /**
     * 获取新的字符串数组
     *
     * @param compareStr
     * @return
     */
    public static String[] getStringArray(String compareStr) {
        StringBuilder sb = new StringBuilder("");
        int index = -1;
        for (int i = 0; i < compareStr.length(); i++) {
            String str = compareStr.substring(i, i + 1);
            if (strIsChangeInt(str)) {
                sb.append(str);
            } else {
                index = i;
                break;
            }
        }
        return new String[]{sb.toString(), index + ""};
    }
    /**
     *d=0.22545 num=2 return 0.22<br/>
     *d=0.22545 num=3 return 0.225<br/>
     *d=0.22545 num=4 return 0.2254<br/>
     *
     * @param d 截取2位小数点
     * @return
     */
    public static String format2Point(double d){
        return String.valueOf(formatPoint(d,2));
    }
    /**
     *d=0.22545 num=2 return 0.22
     *d=0.22545 num=3 return 0.225
     *d=0.22545 num=4 return 0.2254
     *
     * @param d
     * @param num 保留的小数点位数
     * @return 截取num位小数点
     */
    public static double formatPoint(double d,int num){
        double sprt=Math.pow(10, num);
        int tempd=(int) (d*sprt);
        return (double)tempd/sprt;
    }

    /**
     * 获取不是数字的下标值
     *
     * @param compareStr
     * @return
     */
    public static int getIsNumIndex(String compareStr) {
        int index = -1;
        for (int i = 0; i < compareStr.length(); i++) {
            String str = compareStr.substring(i, i + 1);
            if (!strIsChangeInt(str)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * float保留两位小数(4舍五入)
     *
     * @param f
     * @return
     */
    public static float float2float(float f) {
        BigDecimal big = new BigDecimal(f);
        return big.setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
    }

    /**
     * 返回一个整数
     *
     * @param str
     * @return
     */
    public static Integer isEmptyInt(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        return parseInt(str);
    }
    public static Integer parseInt(String str){
        try {
            return Integer.parseInt(str);
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * 返回一个字符串
     *
     * @param str
     * @return
     */
    public static String isEmptyStr(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        return str;
    }

    /**
     * 返回一个浮点数
     *
     * @param str
     * @return
     */
    public static Float isEmptyFloat(String str) {
        return parseFloat(str);
    }
    /**
     * 返回一个浮点数
     *
     * @param str
     * @return
     */
    public static float parseFloat(String str) {
        if (str == null || str.equals("")) {
            return -1f;
        }
        if (str.contains(",")){
            str=str.replaceAll(",",".");
        }
        try{
            return Float.parseFloat(str);
        }catch (Exception e){
            return -1f;
        }

    }
    /**
     * 返回一个浮点数
     *
     * @param str
     * @return
     */
    public static float valueOf(String str) {
        return parseFloat(str);
    }

    /**
     * value 在min-max之间
     * 超出范围设定
     * @param value
     * @param max
     * @param min
     * @return
     */
    public static int range(int value,int max,int min){
        value=Math.min(max,value);
        value=Math.max(value,min);
        return value;
    }
}