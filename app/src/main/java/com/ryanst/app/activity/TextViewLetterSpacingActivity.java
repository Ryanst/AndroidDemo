package com.ryanst.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 16/5/24.
 */
public class TextViewLetterSpacingActivity extends BaseActivity {

    @BindView(R.id.tv_text1)
    TextView tvText1;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_text3)
    TextView tvText3;
    @BindView(R.id.tv_text5)
    TextView tvText5;
    @BindView(R.id.tv_text10)
    TextView tvText10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview_letter_spacing);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvText5.setLetterSpacing(2.0f);
        }
    }
}
