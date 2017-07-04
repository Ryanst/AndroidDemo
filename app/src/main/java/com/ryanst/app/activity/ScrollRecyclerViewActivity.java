package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.databinding.ActivityScrollRecyclerBinding;
import com.ryanst.app.fragment.SimpleCourseListFragment;
import com.ryanst.app.widget.ScrollRecyclerAdapter;
import com.ryanst.app.widget.SeaFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjuntong on 6/6/17.
 */

public class ScrollRecyclerViewActivity extends BaseActivity {


    private ActivityScrollRecyclerBinding binding;
    private ViewPager vpCourseDetail;
    private SeaFragmentPagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;
    private TabLayout tabCourseDetail;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scroll_recycler);
        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        initViewPager();
        initFragment();
        initRecycle();
    }

    private void initFragment() {
        fragmentManager.beginTransaction().replace(R.id.fl_recycler, SimpleCourseListFragment.newInstance(), "TAG").commitAllowingStateLoss();
    }

    private void initRecycle() {
        binding.rvList.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvList.setLayoutManager(linearLayoutManager);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add("test" + i);
        }
        ScrollRecyclerAdapter adapter = new ScrollRecyclerAdapter(this, list, R.layout.item_list_view);
        binding.rvList.setAdapter(adapter);
    }

    private void initViewPager() {
        initPagerFragments();
        vpCourseDetail = binding.vpCourseDetail;
        tabCourseDetail = binding.tabCourseDetail;
        pagerAdapter = new SeaFragmentPagerAdapter(fragmentManager, fragments);
        vpCourseDetail.setAdapter(pagerAdapter);
        vpCourseDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabCourseDetail));
        tabCourseDetail.setTabMode(TabLayout.MODE_FIXED);
        tabCourseDetail.setupWithViewPager(vpCourseDetail);
        vpCourseDetail.setOffscreenPageLimit(2);
        initTabsLayout();
    }

    private void initPagerFragments() {
        fragments.clear();

        SimpleCourseListFragment courseListFragment = SimpleCourseListFragment.newInstance();
        fragments.add(courseListFragment);

        SimpleCourseListFragment courseListFragment2 = SimpleCourseListFragment.newInstance();
        fragments.add(courseListFragment2);

        SimpleCourseListFragment courseListFragment3 = SimpleCourseListFragment.newInstance();
        fragments.add(courseListFragment3);
    }

    private void initTabsLayout() {
        String tabTitle1 = "1";
        View tabView1 = LayoutInflater.from(this).inflate(R.layout.layout_course_detail_tab_view, null);
        TextView tvTitle1 = (TextView) tabView1.findViewById(R.id.tv_title);
        tvTitle1.setText(tabTitle1);
        tabCourseDetail.getTabAt(0).setCustomView(tabView1);

        String tabTitle2 = "2";
        View tabView2 = LayoutInflater.from(this).inflate(R.layout.layout_course_detail_tab_view, null);
        TextView tvTitle2 = (TextView) tabView2.findViewById(R.id.tv_title);
        tvTitle2.setText(tabTitle2);
        tabCourseDetail.getTabAt(1).setCustomView(tabView2);

        String tabTitle3 = "3";
        View tabView3 = LayoutInflater.from(this).inflate(R.layout.layout_course_detail_tab_view, null);
        TextView tvTitle3 = (TextView) tabView3.findViewById(R.id.tv_title);
        tvTitle3.setText(tabTitle3);
        tabCourseDetail.getTabAt(2).setCustomView(tabView3);
    }
}
