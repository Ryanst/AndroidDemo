package com.ryanst.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/9.
 */

public class RyanstApplication extends Application {

    private static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ButterKnife.setDebug(BuildConfig.DEBUG);
        LeakCanary.install(this);
    }

    public static Context getApplication(){
        return application;
    }
}
