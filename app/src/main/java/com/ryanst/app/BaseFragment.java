package com.ryanst.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.johnpersano.supertoasts.SuperToast;

/**
 * Created by kevin on 16/5/6.
 */
public class BaseFragment extends Fragment {

    private SuperToast superToast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BaseFragment", getClass().getName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void toast(String message) {
        if (superToast == null) {
            superToast = SuperToast.create(getActivity(), message, 1000);
            superToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        }
        superToast.setText(message);
        superToast.show();
    }
}
