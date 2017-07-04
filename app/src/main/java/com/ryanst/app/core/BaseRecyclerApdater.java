package com.ryanst.app.core;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 16/3/11.
 */
public abstract class BaseRecyclerApdater<T> extends RecyclerView.Adapter<BaseBindingHolder> {
    public Context context;
    protected List<T> list = new ArrayList<>();
    private int itemLayoutId;

    public BaseRecyclerApdater(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    public BaseBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                itemLayoutId,
                parent, false);
        BaseBindingHolder holder = new BaseBindingHolder(dataBinding.getRoot());
        holder.setBinding(dataBinding);
        return holder;
    }


    @Override
    public abstract void onBindViewHolder(BaseBindingHolder holder, int position);

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
