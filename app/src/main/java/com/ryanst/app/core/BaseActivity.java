package com.ryanst.app.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;

import com.github.johnpersano.supertoasts.SuperToast;
import com.orhanobut.logger.Logger;
import com.ryanst.app.widget.CloseAllActivityEvent;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by kevin on 16/5/6.
 */
public class BaseActivity extends AppCompatActivity {

    private SuperToast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        Log.d("BaseActivity", getClass().getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(CloseAllActivityEvent closeEvent) {
        this.finish();
    }

    protected void toast(String message) {
        if (toast == null) {
            toast = SuperToast.create(this, message, 1000);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        toast.setText(message);
        toast.show();
    }

    public void logger(String message) {
        Logger.d(getClass().getName(), message);
    }

    public void log(String message) {
        Log.d(getClass().getName(), message);
    }
}
