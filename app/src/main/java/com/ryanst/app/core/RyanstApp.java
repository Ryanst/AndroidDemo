package com.ryanst.app.core;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.github.moduth.blockcanary.BlockCanary;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ryanst.app.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;
import hugo.weaving.internal.Hugo;

/**
 * Created by kevin on 16/5/9.
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
        Logger.init(RYANST_LOG).logLevel(LogLevel.FULL);
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
