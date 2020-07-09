package com.scaleamer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<>();
    private static SimpleDateFormat getSimpleDateFormat(){
        SimpleDateFormat simpleDateFormat = simpleDateFormatThreadLocal.get();
        if(simpleDateFormat==null){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormatThreadLocal.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }
    public static String Date2String(Date date){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        return simpleDateFormat.format(date);
    }

    public static Date String2Date(String str){
        Date date = null;
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return date;
    }

    public static boolean compareDate(Date date_1, Date date_2){
        if(date_1==date_2){
            return true;
        }
        return Date2String(date_1).equals(Date2String(date_2));
    }
}
