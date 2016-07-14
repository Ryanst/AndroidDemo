package com.ryanst.app.widget;

import android.os.Environment;

/**
 * Created by kevin on 16/3/29.
 */
public class Constant {
    public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
    public static final String ROOTPATH = SDCARD + "/.ryanst";
    public static final String TEMP_PATH = ROOTPATH + "/temp";
    public static final String IMAGE_PATH = ROOTPATH + "/image";
    public static final String WEB_CACHE_PATH = ROOTPATH + "/cache/webcache.db";
    public static final String AUDIO_CACHE_PATH = ROOTPATH + "/audio";// 语音本地缓存路径
    public static final String VIDEO_CACHE_PATH = ROOTPATH + "/video";// 视频本地缓存路径
    public static final int WEB_CACHE_SIZE = 100 * 1024 * 1024;
    public static final int DISK_CACHE = 512 * 1024 * 1024;
    public static final int MEM_CACHE = 100 * 1024 * 1024;

    public static String APK_PATH = ROOTPATH + "/ryanst";

}
