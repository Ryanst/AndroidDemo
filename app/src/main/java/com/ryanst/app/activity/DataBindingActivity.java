package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.databinding.ActivityDataBindingBinding;
import com.ryanst.app.bean.DataBindingBean;

/**
 * Created by kevin on 16/5/4.
 */
public class DataBindingActivity extends BaseSlideActivity {

    //ActivityDataBindingBinding的命名来自于activity_data_binding 大写,去除_,然后在末尾加上Binding
    ActivityDataBindingBinding binding;
    DataBindingBean dataObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        dataObject = new DataBindingBean("data1", "data2");
        binding.setDataObject(dataObject);
        binding.btnChangeTextToObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText();
            }
        });

        binding.btnChangeObjectToText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeObject();
            }
        });
    }

    private void changeObject() {
        dataObject.setData1("data1->text1");
        dataObject.setData2("data2->text2");
    }

    private void changeText() {
        binding.tvText1.setText("text1->data1");
        binding.tvText2.setText("text2->data2");
        String data1 = dataObject.getData1();
        String data2 = dataObject.getData2();
        Toast.makeText(this, "1:" + data1 + "2:" + data2, Toast.LENGTH_LONG).show();
    }
}