package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.widget.ScrollRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhengjuntong on 6/6/17.
 */

public class SimpleRecyclerActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recyclzer);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(linearLayoutManager);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("test" + i);
        }
        ScrollRecyclerAdapter adapter = new ScrollRecyclerAdapter(this, list, R.layout.item_list_view);
        rvList.setAdapter(adapter);
    }
}
