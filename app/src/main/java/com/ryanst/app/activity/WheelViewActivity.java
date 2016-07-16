package com.ryanst.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.view.WheelView;
import com.ryanst.app.view.WheelViewDialog;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 16/6/17.
 */
public class WheelViewActivity extends BaseSlideActivity {

    private WheelView.OnWheelViewListener wheelViewListener;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
        ButterKnife.bind(this);
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


        wheelViewListener = new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                result = item;
            }
        };

        View rootView = getLayoutInflater().inflate(R.layout.dialog_change_grade, null);
        WheelView wheelView = (WheelView) rootView.findViewById(R.id.wheelView);

        final WheelViewDialog dialog = new WheelViewDialog.Builder(this, rootView, wheelView, list)
                .setDefaultIndex(3)
                .setFlingSpeed(3)
                .setOffset(3)
                .setTextPadding(10)
                .setSelectTextColor(getResources().getColor(R.color.red_text_color))
                .setTextColor(getResources().getColor(R.color.dark_gray))
                .setTextSize(13)
                .setWheelViewListener(wheelViewListener)
                .create();

        TextView tvCancel = (TextView) rootView.findViewById(R.id.tv_cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        TextView tvConfirm = (TextView) rootView.findViewById(R.id.tv_confirm);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                toast("您选择了:" + result);
            }
        });
    }
}
