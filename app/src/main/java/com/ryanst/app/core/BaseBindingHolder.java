package com.ryanst.app.core;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kevin on 16/3/12.
 */
public class BaseBindingHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BaseBindingHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}
