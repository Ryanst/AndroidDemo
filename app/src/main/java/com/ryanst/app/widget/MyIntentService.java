package com.ryanst.app.widget;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zhengjuntong on 16/6/27.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    public MyIntentService(String name) {
        super(name);
    }

    //onHandleIntent在子线程执行,执行完后IntentServive自动停止
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("MyIntentService", "task finish");
    }
}
