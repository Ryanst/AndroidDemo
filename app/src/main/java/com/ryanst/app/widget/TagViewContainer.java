package com.ryanst.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryanst.app.R;

/**
 * Created by zhengjuntong on 3/23/17.
 */

public class TagViewContainer extends ViewGroup {

    private int position = 0;

    private int mGravity = Gravity.LEFT;

    public TagViewContainer(Context context) {
        super(context);
    }

    public TagViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public void addTag(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.black));
        addView(textView, position);
        position++;
        postInvalidate();
    }

    public void addTag2(String text) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.black));
        addView(textView, position);
        position++;
        invalidate();
    }
}
