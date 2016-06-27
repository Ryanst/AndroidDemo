package com.ryanst.app.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.core.BaseHandler;

/**
 * Created by zhengjuntong on 16/6/27.
 */
public class LeakTestActivity extends BaseActivity {

//    StaticHandler handler = new StaticHandler(this);

    BaseHandler<LeakTestActivity> handler = new BaseHandler<LeakTestActivity>(this) {
        @Override
        public void handleMyMessage(Message msg) {
            if (LeakTestActivity.this == null) {
                Log.d("LeakTestActivity", "handleMyMessage LeakTestActivity is null");
            } else {
                Log.d("LeakTestActivity", "handleMyMessage:" + LeakTestActivity.this.toString());
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        handler.sendEmptyMessageDelayed(0, 20000);
    }

    public void onClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume:" + LeakTestActivity.this.toString());
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        log("onDestroy:" + LeakTestActivity.this.toString());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log("finalize");
    }

    public static class StaticHandler extends BaseHandler<LeakTestActivity> {

        public StaticHandler(LeakTestActivity leakTestActivity) {
            super(leakTestActivity);
        }

        @Override
        public void handleMyMessage(Message msg) {
            if (mReference.get() == null) {
                Log.d("LeakTestActivity", "handleMyMessage LeakTestActivity is null");
            } else {
                Log.d("LeakTestActivity", "handleMyMessage:" + mReference.get().toString());
            }
        }
    }
}
