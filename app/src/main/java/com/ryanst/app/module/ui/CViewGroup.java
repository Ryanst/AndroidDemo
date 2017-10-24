package com.ryanst.app.module.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by zhengjt on 2017/8/21.
 */

public class CViewGroup extends LinearLayout {

    private static final String TAG = "CViewGroup";

    public CViewGroup(Context context) {
        super(context);
    }

    public CViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure:" + "widthMeasureSpec:" + widthMeasureSpec + "\t heightMeasureSpec" + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
    }

    @Override
    public Drawable getDividerDrawable() {
        return super.getDividerDrawable();
    }
}
