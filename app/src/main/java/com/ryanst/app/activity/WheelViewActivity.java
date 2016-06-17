package com.ryanst.app.activity;

import android.os.Bundle;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.view.WheelViewUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhengjuntong on 16/6/17.
 */
public class WheelViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void onClick(View view) {
        final List<String> list = Arrays.asList(
                "小学一年级", "小学二年级", "小学三年级", "小学四年级", "小学五年级", "初一", "初中二年级", "初中三年级", "初中四年级",
                "初中1年级",
                "初中2年级",
                "初中3年级",
                "初中4年级",
                "初中5年级",
                "初中6年级");

        WheelViewUtil.createWheelViewDialog(WheelViewActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        toast(WheelViewUtil.result);
                        break;
                    case R.id.tv_positive:
                        break;
                    default:
                        break;
                }
            }
        }, list, 2, "小学五年级");
    }
}
