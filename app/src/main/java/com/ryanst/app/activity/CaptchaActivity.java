package com.ryanst.app.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.util.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 4/27/17.
 */

public class CaptchaActivity extends BaseActivity {

    @BindView(R.id.iv_captcha)
    ImageView ivCaptcha;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        ButterKnife.bind(this);
        createCaptcha();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCaptcha();
            }
        });
    }

    private void createCaptcha() {
        Bitmap bitmap = CodeUtils.getInstance().createBitmap();
        ivCaptcha.setImageBitmap(bitmap);
    }
}
