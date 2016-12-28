package com.ryanst.app.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rlib.util.AndroidScreenUtil;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 12/27/16.
 */

public class BallActivity extends BaseActivity implements View.OnTouchListener {
    @BindView(R.id.activities)
    Button ivBall;
    @BindView(R.id.root)
    FrameLayout flRoot;
    private int _xDelta;
    private int _yDelta;
    private int screenWidth;
    private int screenHeight;
    private int maxMarginLeft;
    private int maxMarginTop;
    private int downX;
    private int downY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        ButterKnife.bind(this);

        int size = AndroidScreenUtil.dip2px(this, 70);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                size, size);

        screenWidth = AndroidScreenUtil.getScreenMetrics(this).x;
        screenHeight = AndroidScreenUtil.getScreenMetrics(this).y;

        maxMarginLeft = screenWidth - size;
        layoutParams.leftMargin = maxMarginLeft - AndroidScreenUtil.dip2px(this, 30);
        maxMarginTop = screenHeight - size - AndroidScreenUtil.dip2px(this, 25);
        layoutParams.topMargin = maxMarginTop - AndroidScreenUtil.dip2px(this, 30);
        layoutParams.bottomMargin = 0;
        layoutParams.rightMargin = 0;

        ivBall.setLayoutParams(layoutParams);
        ivBall.setOnTouchListener(this);
    }

    public boolean onTouch(View view, MotionEvent event) {
        dealOnTouch(view, event);
        return true;
    }

    private void dealOnTouch(View view, MotionEvent event) {
        final int touchX = (int) event.getRawX();
        final int touchY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = touchX;
                downY = touchY;
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) view
                        .getLayoutParams();
                _xDelta = touchX - lParams.leftMargin;
                _yDelta = touchY - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                if (downX == touchX && downY == touchY) {
                    Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view
                        .getLayoutParams();

                int leftMargin;

                if (touchX - _xDelta <= 0) {
                    leftMargin = 0;
                } else if (touchX - _xDelta < maxMarginLeft) {
                    leftMargin = touchX - _xDelta;
                } else {
                    leftMargin = maxMarginLeft;
                }

                layoutParams.leftMargin = leftMargin;

                int topMargin;

                if (touchY - _yDelta <= 0) {
                    topMargin = 0;
                } else if (touchY - _yDelta < maxMarginTop) {
                    topMargin = touchY - _yDelta;
                } else {
                    topMargin = maxMarginTop;
                }

                layoutParams.topMargin = topMargin;

                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                view.setLayoutParams(layoutParams);
                break;
        }
        flRoot.invalidate();
    }
}
