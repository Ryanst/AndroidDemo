package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

/**
 * Created by zhengjuntong on 16/5/21.
 */
public class CustomViewActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
    }
}
