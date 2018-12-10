package com.lu.library.util;

public class UnitFormat {

    /**
     * 英寸转厘米
     *
     * @param data
     * @return
     */
    public static int newInch2cm(int[] data) {
//        return Math.round((data[0] * 12 + data[1]) * 3.0172414f);
        return inch2cm(data);
    }

    /**
     * 厘米转英寸
     *
     * @param data
     * @return
     */
    public static int newCm2inch(int[] data) {
        return Math.round((data[0] + data[1]) / 3.0172414f);
    }


    /**
     * 这个方法会改变传入参数里面的数据
     * @param inch inch[0] 英尺 inch[1] 英寸
     * @return cm
     */
    @Deprecated
    public static int inch2cm(int[] inch) {
        inch[1] = inch[0] * 12 + inch[1];
        return Math.round(inch[1] * 2.5399999f);
    }
    /**
     * 英尺 英寸转厘米
     * @param inch inch[0] 英尺 inch[1] 英寸
     * @return cm
     */
    public static int inch2cmNew(int[] inch) {
        int result = inch[0] * 12 + inch[1];
        return Math.round(result * 2.5399999f);
    }

    /**
     * @param inch inch[0] 英尺 inch[1] 英寸
     * @return cm
     */
    public static int inch2cm(int inch) {
        return Math.round(inch * 2.5399999f);
    }

    public static int inch2inch(int[] inch) {
        return inch[0] * 12 + inch[1];
    }

    public static int[] inch2inch(int inch) {
        return new int[]{inch / 12, inch % 12};
    }

    /**
     * @param inchs
     * @return cm
     */
    public static int inchs2cm(int inchs) {
        return Math.round(inchs * 2.5399999f);
    }

    /**
     * @param cm
     * @return inch inch[0] 英尺 inch[1] 英寸
     */
    public static int[] cm2inch(int cm) {
        int[] inch = new int[2];
        inch[1] = cm2inchs(cm);
        inch[0] = inch[1] / 12;
        inch[1] = inch[1] % 12;
        return inch;
    }

    /*public static float km2mile(float km) {
        String format = String.format("%.2f", km * 0.62137f);
        format = format.replace(',', '.');
        return Float.parseFloat(format.substring(0, format.length() - 1));
    }*/

    /**
     * 公里换算成英里
     *
     * @param km
     * @return
     */
    public static float km2mile(float km) {
//        return km * 0.62137f;
        return km * 0.6213712f;
    }

    /**
     * 英里换算成公里
     *
     * @param mile
     * @return
     */
    public static float mile2km(float mile) {

//        return mile * 1.609344f;
        return mile * 1.609344f;
    }

    public static int ft2in(int ft) {
        return ft * 12;
    }

    /**
     * @param cm
     * @return inch 英寸
     */
    public static int cm2inchs(int cm) {
        return Math.round(cm / 2.5399999f);
    }

    /*public static int kg2lb(int kg) {
        return Math.round(2.2046226f * kg);
    }

    public static int lb2kg(int lb) {
        return Math.round(lb / 2.2046226f);
    }*/

    /**
     * 千克和磅的转换
     * 1公斤(kg)=2.2046226磅(lb)
     * 1磅(lb)=0.4535924千克(kg)
     */

    public static float kg2lb(float kg) {
        return Math.round(2.2046226f * kg);
    }

    public static float lb2kg(float lb) {
        return Math.round(lb * 0.4535924f);
    }

    //千克转石英
    public static int kg2st(int kg){
        return (int) Math.round(kg*0.157);
    }
    //磅转石英
    public static int lb2st(int lb){
        return (int) Math.round(lb*0.071);
    }
    //石英转千克
    public static int st2kg(int st){
        return (int) Math.round(st*6.35);
    }
    //千克转磅
    public static int st2lb(int st){
        return st*14;
    }
}