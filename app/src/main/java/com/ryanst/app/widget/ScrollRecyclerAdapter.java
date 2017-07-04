package com.ryanst.app.widget;

import android.content.Context;

import com.ryanst.app.core.BaseBindingHolder;
import com.ryanst.app.core.BaseRecyclerApdater;

import java.util.List;

/**
 * Created by zhengjuntong on 6/6/17.
 */

public class ScrollRecyclerAdapter extends BaseRecyclerApdater<String> {
    public ScrollRecyclerAdapter(Context context, List<String> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void onBindViewHolder(BaseBindingHolder holder, int position) {

    }
}
