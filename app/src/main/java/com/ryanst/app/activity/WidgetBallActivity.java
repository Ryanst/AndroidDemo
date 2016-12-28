package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rlib.util.AndroidScreenUtil;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.widget.FloatBall;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 12/28/16.
 */

public class WidgetBallActivity extends BaseActivity {
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_ball);
        ButterKnife.bind(this);
        initBall();
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initBall() {
        FloatBall floatBall = new FloatBall.Builder(this)
                .setBottomMargin(AndroidScreenUtil.dip2px(this, 30))
                .setRightMargin(AndroidScreenUtil.dip2px(this, 30))
                .setHeight(AndroidScreenUtil.dip2px(this, 70))
                .setWidth(AndroidScreenUtil.dip2px(this, 70))
                .setRes(R.drawable.qipao)
                .setRootView(flContent)
                .build();

        floatBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WidgetBallActivity.this, v.getId() + "", Toast.LENGTH_SHORT).show();
            }
        });

        flContent.addView(floatBall.getBall());
    }
}
