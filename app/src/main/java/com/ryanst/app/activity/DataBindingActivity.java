package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.bean.DataBindingBean;
import com.ryanst.app.bean.PlainObject;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.databinding.ActivityDataBindingBinding;

/**
 * Created by zhengjuntong on 16/5/4.
 */
public class DataBindingActivity extends BaseSlideActivity {

    //ActivityDataBindingBinding的命名来自于activity_data_binding 大写,去除_,然后在末尾加上Binding
    ActivityDataBindingBinding binding;
    DataBindingBean dataObject;
    private PlainObject plainObject;
    private int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        dataObject = new DataBindingBean("data1", "data2");
        binding.setDataObject(dataObject);

        plainObject = new PlainObject(new ObservableField<>("content"), "plain");
        binding.setPlainObject(plainObject);

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

        count = 0;

        binding.testField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                plainObject.content.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                    @Override
                    public void onPropertyChanged(Observable observable, int i) {

                    }
                });

                plainObject.content.set("content" + (count++));
                plainObject.setPlainText("plain" + count);

                //binding.setVariable(BR.plainObject, plainObject);
                //无需调用这行，即可实现数据改变驱动UI变化的效果
                //如果调用这行的话，不是ObservableField的plainText也可以实现数据改变从而改变UI的效果
                //所以ObservableField的优势在于可以直接set从而驱动UI改变
            }
        });
    }

    //是不能通过改变View改变数据的
    private void changeText() {
        binding.tvText1.setText("text1->data1");
        binding.tvText2.setText("text2->data2");
        String data1 = dataObject.getData1();
        String data2 = dataObject.getData2();
        toast("1:" + data1 + "2:" + data2);
    }

    //可以通过改变数据从而改变View，因为set方法中调用了notifyPropertyChanged
    private void changeObject() {
        dataObject.setData1("data1->text1");
        dataObject.setData2("data2->text2");
    }
}