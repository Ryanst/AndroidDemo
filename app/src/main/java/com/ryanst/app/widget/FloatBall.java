package com.ryanst.app.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhengjuntong on 12/28/16.
 */

public class FloatBall implements View.OnTouchListener {

    public static final int TOP_STATUS_BAR_HEIGHT = 25;
    private Params params;

    private Context context;
    private View ball;
    private View.OnClickListener onClickListener;
    private int maxMarginLeft;
    private int maxMarginTop;
    private int downX;
    private int downY;
    private int xDelta;
    private int yDelta;

    private FloatBall(Context context) {
        this.context = context;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public View getBall() {
        return ball;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.onClickListener = listener;
    }

    private void init() {

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                params.width, params.height);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        maxMarginLeft = screenWidth - params.width;
        layoutParams.width = params.width;
        layoutParams.height = params.height;
        layoutParams.leftMargin = maxMarginLeft - params.rightMargin;
        maxMarginTop = screenHeight - params.height - (int) (context.getResources().getDisplayMetrics().density * TOP_STATUS_BAR_HEIGHT);
        layoutParams.topMargin = maxMarginTop - params.bottomMargin;
        layoutParams.bottomMargin = 0;
        layoutParams.rightMargin = 0;

        if (params.ball == null) {
            ball = new View(context);
        } else {
            ball = params.ball;
        }

        if (params.resId != 0) {
            ball.setBackgroundResource(params.resId);
        }

        ball.setLayoutParams(layoutParams);
        ball.setOnTouchListener(this);
        ViewCompat.setElevation(ball, 64);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int touchX = (int) event.getRawX();
        final int touchY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                downX = touchX;
                downY = touchY;
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) ball
                        .getLayoutParams();
                xDelta = touchX - lParams.leftMargin;
                yDelta = touchY - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                if (downX == touchX && downY == touchY) {
                    if (onClickListener != null) {
                        onClickListener.onClick(ball);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ball
                        .getLayoutParams();

                int leftMargin;

                if (touchX - xDelta <= 0) {
                    leftMargin = 0;
                } else if (touchX - xDelta < maxMarginLeft) {
                    leftMargin = touchX - xDelta;
                } else {
                    leftMargin = maxMarginLeft;
                }

                layoutParams.leftMargin = leftMargin;

                int topMargin;

                if (touchY - yDelta <= 0) {
                    topMargin = 0;
                } else if (touchY - yDelta < maxMarginTop) {
                    topMargin = touchY - yDelta;
                } else {
                    topMargin = maxMarginTop;
                }

                layoutParams.topMargin = topMargin;
                layoutParams.rightMargin = 0;
                layoutParams.bottomMargin = 0;
                ball.setLayoutParams(layoutParams);
                break;
        }

        ball.getRootView().invalidate();
        return true;
    }

    public static class Builder {
        private Params P;

        public Builder(Context context) {
            P = new Params(context);
        }

        public Builder setRightMargin(int rightMargin) {
            P.rightMargin = rightMargin;
            return this;
        }

        public Builder setBottomMargin(int bottomMargin) {
            P.bottomMargin = bottomMargin;
            return this;
        }

        public Builder setWidth(int width) {
            P.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            P.height = height;
            return this;
        }

        public Builder setRes(int resId) {
            P.resId = resId;
            return this;
        }

        public Builder setBall(View view) {
            P.ball = view;
            return this;
        }

        public FloatBall build() {
            FloatBall floatBall = new FloatBall(P.context);
            floatBall.setParams(P);
            floatBall.init();
            return floatBall;
        }
    }

    private static class Params {
        public static final int DEFAULT_BALL_WIDTH = 180;
        public static final int DEFAULT_BALL_HEIGHT = 180;
        private Context context;
        private int rightMargin;
        private int bottomMargin;
        private int resId;
        private int width = DEFAULT_BALL_WIDTH;
        private int height = DEFAULT_BALL_HEIGHT;
        private View ball;

        public Params(Context context) {
            this.context = context;
        }
    }
}
