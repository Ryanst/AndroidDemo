package com.ryanst.app.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.github.moduth.blockcanary.BlockCanary;
import com.ryanst.app.BuildConfig;
import com.ryanst.app.widget.AppBlockCanaryContext;
import com.ryanst.app.widget.UCEHandler;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 16/5/9.
 */

public class RyanstApp extends Application {

    private static Context application;
    public static final String RYANST_LOG = "Juntong";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ButterKnife.setDebug(BuildConfig.DEBUG);
        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
//        initUncaughtException();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
