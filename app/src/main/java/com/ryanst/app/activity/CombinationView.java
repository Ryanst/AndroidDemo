package com.ryanst.app.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryanst.app.R;

import butterknife.BindView;

/**
 * Created by zhengjuntong on 16/5/24.
 */
public class CombinationView extends FrameLayout {

    Button btnChangeColor;
    TextView tvText1;
    LinearLayout llMain;

    private int btnColor;
    private int size;
    private int ivBg;
    private String text;

    public CombinationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_combination, this);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CombinationViewStyleable);
        size = array.getDimensionPixelSize(R.styleable.CombinationViewStyleable_size, 0);
        btnColor = array.getColor(R.styleable.CombinationViewStyleable_btnColor, 0);
        ivBg = array.getResourceId(R.styleable.CombinationViewStyleable_ivBg, R.drawable.ic_explore);
        text = array.getString(R.styleable.CombinationViewStyleable_text);
        tvText1 = (TextView) findViewById(R.id.tv_text1);
        btnChangeColor = (Button) findViewById(R.id.btn_change_color);
        llMain = (LinearLayout) findViewById(R.id.ll_main);
    }

    public void initView() {
        tvText1.setText(text);
        tvText1.setTextSize(size);
        btnChangeColor.setBackgroundColor(btnColor);

        btnChangeColor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llMain.setBackgroundResource(ivBg);
            }
        });
    }


}
