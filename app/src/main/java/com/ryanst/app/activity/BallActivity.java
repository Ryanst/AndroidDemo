package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by zhengjuntong on 12/27/16.
 */

public class BallActivity extends BaseActivity implements View.OnTouchListener {
    @BindView(R.id.activities)
    ImageView activities;
    @BindView(R.id.fl_content)
    RelativeLayout flContent;
    @BindView(R.id.btn_test)
    Button btnTest;
    private int xDelta;
    private int yDelta;
    private boolean click = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        ButterKnife.bind(this);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = !click;
                Toast.makeText(BallActivity.this, "Hello: " + click, Toast.LENGTH_SHORT).show();
                if (click) {
                    activities.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(BallActivity.this, "Click", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    activities.setOnClickListener(null);
                }
            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                150, 50);
        layoutParams.leftMargin = 50;
        layoutParams.topMargin = 50;
        layoutParams.bottomMargin = -250;
        layoutParams.rightMargin = -250;
        activities.setLayoutParams(layoutParams);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int X = (int) event.getRawX();
        int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) activities
                        .getLayoutParams();
                xDelta = X - lParams.leftMargin;
                yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) activities
                        .getLayoutParams();
                layoutParams.leftMargin = X - xDelta;
                layoutParams.topMargin = Y - yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                activities.setLayoutParams(layoutParams);
                break;
        }
        flContent.invalidate();
        return true;
    }
}
