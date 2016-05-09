package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.activity.databinding.ActivityDataBindingBinding;
import com.ryanst.app.demo.DataBindingObject;

/**
 * Created by kevin on 16/5/4.
 */
public class DataBindingActivity extends BaseActivity {

    //ActivityDataBindingBinding的命名来自于activity_data_binding 大写,去除_,然后在末尾加上Binding
    ActivityDataBindingBinding binding;
    DataBindingObject dataObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, com.ryanst.app.activity.R.layout.activity_data_binding);
        dataObject = new DataBindingObject("data1", "data2");
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
        Toast.makeText(this, data1 + "  " + data2, Toast.LENGTH_LONG);
    }


}