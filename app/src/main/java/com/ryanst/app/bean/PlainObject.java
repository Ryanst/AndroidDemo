package com.ryanst.app.bean;

import android.databinding.ObservableField;


/**
 * Created by zhengjuntong on 1/13/17.
 */

public class PlainObject {

    public PlainObject(ObservableField<String> content, String plainText) {
        this.content = content;
        this.plainText = plainText;
    }

    public ObservableField<String> content = new ObservableField<>();


    private String plainText = new String();

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
}
