package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rlib.util.AndroidScreenUtil;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.core.RyanstApp;
import com.ryanst.app.util.Captcha;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 4/27/17.
 */

public class CaptchaActivity extends BaseActivity {
    public static final int CAPTCHA_WIDTH = 131;
    public static final int CAPTCHA_HEIGHT = 53;

    @BindView(R.id.iv_captcha)
    ImageView ivCaptcha;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_check)
    Button btnCheck;
    private Captcha captcha;

    public static int FONT_SIZE = AndroidScreenUtil.dip2px(RyanstApp.getApplication(), 40);
    public static int BASE_PADDING_LEFT = AndroidScreenUtil.dip2px(RyanstApp.getApplication(), 24);
    public static int RANGE_PADDING_LEFT = AndroidScreenUtil.dip2px(RyanstApp.getApplication(), 8);
    public static int BASE_PADDING_TOP = AndroidScreenUtil.dip2px(RyanstApp.getApplication(), 40);
    public static int RANGE_PADDING_TOP = AndroidScreenUtil.dip2px(RyanstApp.getApplication(), 8);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        ButterKnife.bind(this);
        createCaptcha();
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captcha != null) {
                    captcha.refresh();
                    ivCaptcha.setImageBitmap(captcha.getImage());
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCaptchaCorrect()) {
                    toast("正确");
                } else {
                    toast("错误");
                }
            }
        });
    }

    private void createCaptcha() {
        captcha = new Captcha.Builder()
                .setCodeLength(4)
                .setFontSize(FONT_SIZE)
                .setLineNumber(3)
                .setBasePaddingLeft(BASE_PADDING_LEFT)
                .setRangePaddingLeft(RANGE_PADDING_LEFT)
                .setBasePaddingTop(BASE_PADDING_TOP)
                .setRangePaddingTop(RANGE_PADDING_TOP)
                .setWidth(AndroidScreenUtil.dip2px(this, CAPTCHA_WIDTH))
                .setHeight(AndroidScreenUtil.dip2px(this, CAPTCHA_HEIGHT))
                .build();

        ivCaptcha.setImageBitmap(captcha.getImage());
    }

    private boolean inputCaptchaCorrect() {
        String captchaInput = etInput.getText().toString();
        if (captcha != null && !TextUtils.isEmpty(captcha.getCode())) {
            if (captchaInput.toLowerCase().equals(captcha.getCode().toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}



















