package com.ryanst.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ryanst.app.BaseActivity;

/**
 * Created by kevin on 16/5/4.
 */
public class HandlerActivity extends BaseActivity {


    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
    }

    public void onClick(View view) {
        handler = new Handler();

        Log.d("HandlerActivity", Thread.currentThread().getId() + "");// 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("new Thread run", Thread.currentThread().getId() + "");// 4953
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("handler.post run", Thread.currentThread().getId() + ""); // 1
                        Toast.makeText(HandlerActivity.this, "子线程Id:" + Thread.currentThread().getId(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    /**
     *
     public void dispatchMessage(Message msg) {
         if (msg.callback != null) {
            handleCallback(msg);
         } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                return;
            }
            }
         handleMessage(msg);
         }
     }

     private final void handleCallback(Message message) {
        message.callback.run();
     }

     public final boolean post(Runnable r){
        return  sendMessageDelayed(getPostMessage(r), 0);
     }
     private final Message getPostMessage(Runnable r) {
         Message m = Message.obtain();
         m.callback = r;
         return m;
     }

     以上可知handle.post(r),r.run()中跑的代码是在Looper的loop方法调用dispatchMessage,dispatchMessage调用handleCallback,
     handleCallback在调用r.run()的,所以r.run()是在Handle所在的线程跑的,同理,view的post:

     public boolean post(Runnable action) {
        Handler handler;
        if (mAttachInfo != null) {
            handler = mAttachInfo.mHandler;
        } else {
            ViewRoot.getRunQueue().post(action);
            return true;
        }
        return handler.post(action);
     }

     Activity中的runOnUiThread()方法:
     public final void runOnUiThread(Runnable action) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(action);
        } else {
            action.run();
        }
     }

     */
}
