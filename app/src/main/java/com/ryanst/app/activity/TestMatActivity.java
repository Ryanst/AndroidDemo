package com.ryanst.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

/**
 * Created by zhengjuntong on 2/24/17.
 */
public class TestMatActivity extends BaseActivity {

    private static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        context = this;
    }
}
