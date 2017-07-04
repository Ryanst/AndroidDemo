package com.ryanst.app.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ryanst.app.BR;


/**
 * Created by zhengjuntong on 16/5/4.
 */
public class DataBindingBean extends BaseObservable {

    public DataBindingBean(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    private String data1;
    private String data2;

    @Bindable
    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
        notifyPropertyChanged(BR.data1);
    }

    @Bindable
    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
//        notifyPropertyChanged(BR.data2);
    }
}
