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

public class LaunchModeActivity2 extends BaseActivity {
    @BindView(R.id.btn_simple)
    Button btnSimple;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        ButterKnife.bind(this);
        btnSimple.setText("前往C");
        toast("B");
    }

    public void onClick(View view) {
        startActivity(new Intent(this, LaunchModeActivity3.class).addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS));
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        toast("onSaveInstanceState 2");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        toast("onRestoreInstanceState 2");
    }
}
