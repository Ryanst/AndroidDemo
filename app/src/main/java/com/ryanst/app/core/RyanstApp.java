package com.ryanst.app.core;

import android.app.Application;
import android.content.Context;

import com.ryanst.app.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/9.
 */

public class RyanstApp extends Application {

    private static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ButterKnife.setDebug(BuildConfig.DEBUG);
        LeakCanary.install(this);
        initUncaughtException();
    }

    public static Context getApplication() {
        return application;
    }


    public void initUncaughtException() {
        //设置该CrashHandler为程序的默认处理器
        UCEHandler uceHandler = new UCEHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(uceHandler);
    }
}
