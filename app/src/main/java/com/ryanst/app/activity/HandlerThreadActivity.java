package com.ryanst.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.util.Utils;

/**
 * Created by zhengjuntong on 16/5/5.
 */

public class HandlerThreadActivity extends BaseSlideActivity {

    private TextView mTvServiceInfo;

    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler;
    private boolean isUpdateInfo;

    private static final int MSG_UPDATE_INFO = 0x110;

    //与UI线程管理的handler
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlerthread);
        //创建后台线程
        initBackThread();
        mTvServiceInfo = (TextView) findViewById(R.id.id_textview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始查询
        isUpdateInfo = true;
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

    }

    private void initBackThread() {
        mCheckMsgThread = new HandlerThread("check-message-coming");
        mCheckMsgThread.start();
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //模拟耗时
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkForUpdate();
                if (isUpdateInfo) {
                    Log.d("handleMessage", Utils.getCurrentThreadId()); //子线程 5587
                    mCheckMsgHandler.sendEmptyMessageDelayed(MSG_UPDATE_INFO, 1000);
                }
            }
        };
    }

    /**
     * 模拟从服务器解析数据
     */
    private void checkForUpdate() {
        //mHandler.post的作用
        //这个mHandler存在的意义在于能在子线程的代码中(mCheckMsgHandler的handleMessage中)切换到主线程以便执行UI操作
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("run", Utils.getCurrentThreadId());// 主线程 1
                String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                result = String.format(result, (int) (Math.random() * 3000 + 1000));
                mTvServiceInfo.setText(Html.fromHtml(result));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        mCheckMsgThread.quit();
    }


}