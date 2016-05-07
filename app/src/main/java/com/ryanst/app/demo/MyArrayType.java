package com.ryanst.app.demo;

import java.util.ArrayList;

/**
 * Created by kevin on 16/5/5.
 */
public class MyArrayType<T> extends ArrayList<String> {
    ArrayList<T> list;

    public MyArrayType(int capacity, ArrayList<T> list) {
        super(capacity);
        this.list = list;
    }
}
