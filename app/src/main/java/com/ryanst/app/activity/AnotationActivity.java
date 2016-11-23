package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 11/23/16.
 */
public class AnotationActivity extends BaseActivity {
    @BindView(R.id.tv_title1)
    TextView tvTitle1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotation);
        ButterKnife.bind(this);
        testAnotation();
    }

    private void testAnotation() {
        String str = null;
        setMyTitle(str);
    }

    @NonNull
    private String setMyTitle(@NonNull String str) {
        tvTitle1.setText(str);
        return null;
    }
}
