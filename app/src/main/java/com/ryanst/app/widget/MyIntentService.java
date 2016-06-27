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

    //onHandleIntent在子线程执行,操作结束后IntentService自动停止，调用服务的onDestory
    //可以使用线程完成IntentService完成的任务，在服务的onBind方法中return null，然后在startCommand中开启线程，当子线程代码最后调用stopSelf()
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
