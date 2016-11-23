package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by zhengjuntong on 9/5/16.
 */
public class TouchEventTestActivity extends BaseActivity {


    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event);
        ButterKnife.bind(this);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log("onclick");
            }
        });
        btnTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                log("onTouch");
                return true;
            }
        });
    }



}
