package com.ryanst.app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseFragment;
import com.ryanst.app.databinding.FragmentSimpleCourseListBinding;
import com.ryanst.app.widget.ScrollRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjuntong on 10/10/16.
 */
public class SimpleCourseListFragment extends BaseFragment {

    private FragmentSimpleCourseListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_simple_course_list, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.rlSimpleCourseList.setLayoutManager(linearLayoutManager);
//        binding.rlSimpleCourseList.setFocusableInTouchMode(false);
//        binding.rlSimpleCourseList.setFocusable(false);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("test" + i);
        }
        ScrollRecyclerAdapter adapter = new ScrollRecyclerAdapter(getActivity(), list, R.layout.item_list_view);
        binding.rlSimpleCourseList.setAdapter(adapter);
    }

    public static SimpleCourseListFragment newInstance() {
        SimpleCourseListFragment fragment = new SimpleCourseListFragment();
        return fragment;
    }
}
