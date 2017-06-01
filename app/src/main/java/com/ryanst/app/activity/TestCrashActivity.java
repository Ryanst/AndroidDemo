package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

/**
 * Created by zhengjuntong on 5/31/17.
 */

public class TestCrashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
    }

    public void onClick(View view) {
        int[] in = new int[1];
        int a = in[100];
    }
}
