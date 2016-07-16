package com.ryanst.app.widget;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.ryanst.app.R;
import com.ryanst.app.core.MainActivity;

import hugo.weaving.DebugLog;

/**
 * Created by zhengjuntong on 16/6/27.
 */

public class MyService extends Service {

    public static final String TAG = "MyService";

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate:" + Thread.currentThread().getId() + "");//thread:1
//        startForeService();
    }

    private void startForeService() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //Ticker:statusBar的提示
        builder.setTicker("Foreground Service Start");
        builder.setContentTitle("Foreground Service");
        builder.setContentText("Make this service run in the foreground.");
        Notification notification = builder.build();

        int serviceId = 1;
        startForeground(serviceId, notification);
        Log.d(TAG, "onCreate() executed");
    }

    @DebugLog
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand:" + Thread.currentThread().getId() + "");//thread:1
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Logger.d("run:" + Thread.currentThread().getId() + "");
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("MyService", "task finish");
            }
        }).start();
//        stopSelf();这里如果调用stopSelf,效果和IntentService类似,都是执行完耗时任务之后结束自己的服务
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {

        public void startDownload() {
            Logger.d("Binder startDownload:" + Thread.currentThread().getId() + "");//thread:1
            // 执行具体的下载任务
        }
    }
}