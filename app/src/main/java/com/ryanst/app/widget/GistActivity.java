package com.ryanst.app.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

/**
 * Created by zhengjuntong on 16/6/24.
 */
public class GistActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
    }

    private void fitSystem() {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(true);
    }
}
