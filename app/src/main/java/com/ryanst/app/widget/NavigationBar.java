package com.ryanst.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ryanst.app.R;


public class NavigationBar extends FrameLayout {

    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private CharSequence title;
    private CharSequence subTitle;
    private boolean showBack;

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View navbarView = inflate(getContext(), R.layout.layout_navigation_bar, null);

        toolbar = (Toolbar) navbarView.findViewById(R.id.toolbar);
        tvTitle = (TextView) navbarView.findViewById(R.id.tv_title);
        tvSubTitle = (TextView) navbarView.findViewById(R.id.tv_sub_title);

        TypedArray typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.tool_bar);
        title = typeArray.getString(R.styleable.tool_bar_title_text);
        subTitle = typeArray.getString(R.styleable.tool_bar_subtitle_text);
        showBack = typeArray.getBoolean(R.styleable.tool_bar_back, true);
        typeArray.recycle();
        tvTitle.setText(title);
        tvSubTitle.setText(subTitle);
        if (showBack) {
            toolbar.setNavigationIcon(R.drawable.back);
        }

        addView(navbarView);
    }

    public NavigationBar(Context context) {
        super(context);
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setSubtitle(CharSequence title) {
        tvSubTitle.setText(title);

    }

    public void setNavigationOnClickListener(OnClickListener listener) {
        toolbar.setNavigationOnClickListener(listener);
    }

    @Override
    public void setBackgroundColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setSubTitleColor(int color) {
        tvSubTitle.setTextColor(color);
    }

    public Toolbar getbar() {
        return toolbar;
    }

    public void setBackVisibility(Boolean isVisiable) {
        if (isVisiable) {
            toolbar.setNavigationIcon(R.drawable.back);
        } else {
            toolbar.setNavigationIcon(null);
        }
    }
}
