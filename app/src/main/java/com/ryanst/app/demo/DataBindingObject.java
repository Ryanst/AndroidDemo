package com.ryanst.app.demo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by kevin on 16/5/4.
 */
public class DataBindingObject extends BaseObservable {

    public DataBindingObject(String data1, String data2) {
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
        notifyPropertyChanged(com.ryanst.app.activity.BR.data1);
    }

    @Bindable
    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
        notifyPropertyChanged(com.ryanst.app.activity.BR.data2);
    }
}
