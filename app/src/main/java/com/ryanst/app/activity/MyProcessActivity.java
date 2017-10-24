package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.core.RyanstApp;

/**
 * Created by zhengjt on 2017/9/13.
 */

public class MyProcessActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        Logger.i("XXXXXXXX", RyanstApp.RYANST_LOG);
        Toast.makeText(this, RyanstApp.RYANST_LOG, Toast.LENGTH_SHORT).show();
    }
}
