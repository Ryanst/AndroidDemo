package com.ryanst.app.bean;


import java.util.List;

/**
 * Created by zhengjuntong on 2/7/17.
 */

public class AddressBean {
    private int id;
    private String name;
    private List<AddressBean> sub;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AddressBean> getSub() {
        return sub;
    }

    public void setSub(List<AddressBean> sub) {
        this.sub = sub;
    }
}
