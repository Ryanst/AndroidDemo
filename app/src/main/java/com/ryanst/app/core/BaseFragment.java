package com.ryanst.app.core;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.johnpersano.supertoasts.SuperToast;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by zhengjuntong on 16/5/6.
 */
public class BaseFragment extends Fragment {

    private SuperToast toast;

    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        Log.d("BaseFragment", getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    protected void toast(String message) {
        if (toast == null) {
            toast = SuperToast.create(getActivity(), message, 1000);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        toast.setText(message);
        toast.show();
    }

    public void logger(String message) {
        Logger.d(message);
    }

    public void logger(String tag, String message) {
        Logger.t(tag).d(message);
    }

    public void log(String message) {
        Log.d(getClass().getName(), message);
    }

    public void onClick(View view) {

    }
}
