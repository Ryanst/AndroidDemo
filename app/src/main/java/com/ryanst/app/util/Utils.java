package com.ryanst.app.util;

/**
 * Created by zhengjuntong on 16/5/5.
 */
public class Utils {
    public static String getCurrentThreadId() {
        return String.valueOf(Thread.currentThread().getId());
    }

    public static String getClassName(Object object){
        return object.getClass().getName();
    }
}
