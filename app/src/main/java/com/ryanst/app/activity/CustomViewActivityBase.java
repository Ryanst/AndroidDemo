package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.view.CombinationView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 16/5/21.
 */
public class CustomViewActivityBase extends BaseSlideActivity {
    @BindView(R.id.combinationView)
    CombinationView combinationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        combinationView.initView();
    }
}
