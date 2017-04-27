package com.ryanst.app.activity.launchMode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 1/4/17.
 */

public class LaunchModeActivity3 extends BaseActivity {
    @BindView(R.id.btn_simple)
    Button btnSimple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        ButterKnife.bind(this);
        btnSimple.setText("前往A");
        toast("C");
    }

    public void onClick(View view) {
        startActivity(new Intent(this, LaunchModeActivity.class));
    }
}
