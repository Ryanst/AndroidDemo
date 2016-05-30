package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ryanst.app.core.BaseSlideActivity;

/**
 * Created by kevin on 16/5/5.
 */
public class JunitTestActivity extends BaseSlideActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public int addNum(int a, int b) {
        return a + b;
    }
}
