package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.databinding.ActivityNavBarBinding;

/**
 * Created by zhengjuntong on 16/6/22.
 */
public class ToolBarTestActivity extends BaseSlideActivity {

    ActivityNavBarBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nav_bar);
        binding.navBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClick(View view) {
        binding.navBar.setTitle("标题");
    }
}
