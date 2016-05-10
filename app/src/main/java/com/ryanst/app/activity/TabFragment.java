package com.ryanst.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/10.
 */
public class TabFragment extends BaseFragment {

    private static String fragmentTitle;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, null);
        ButterKnife.bind(this, view);
        tvTitle.setText(fragmentTitle);
        return view;
    }

    public static TabFragment getInstance(String title) {
        TabFragment fragment = new TabFragment();
        fragmentTitle = title;
        return fragment;
    }
}
