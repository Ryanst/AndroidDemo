package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ryanst.app.core.BaseActivity;

/**
 * Created by kevin on 16/5/9.
 */
public class PhotoTestActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_test);
    }

    public void onClick(View view) {
        popSelectWindow();
    }

    private void popSelectWindow() {

    }
}
