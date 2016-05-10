package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.fragment.PagerFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/6.
 */
public class TabLayoutViewPagerActivity extends BaseActivity {

    public static final int TAB_NUM = 5;

    @BindView(R.id.tl_test)
    TabLayout tlTest;
    @BindView(R.id.vp_test)
    ViewPager vpTest;

    private MyFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_viewpager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        FragmentManager fr = getSupportFragmentManager();
        adapter = new MyFragmentPagerAdapter(fr);
        initTab();
        vpTest.setAdapter(adapter);
        vpTest.setCurrentItem(0);
        tlTest.setupWithViewPager(vpTest);

    }

    private void initTab() {
        for (int i = 0; i < TAB_NUM; i++) {
            int color = getResources().getColor(R.color.Pink);
            adapter.addTab(PagerFragment.createPagerFragment(color), "标题" + i);
//            View view = createTabView(i);
//            tlTest.getTabAt(i).setCustomView(view);
        }
    }

    //可以手动添加tabLayout,也可以用系统自带的,用系统自带的可以用系统的tabLayout属性
    private View createTabView(int i) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.view_tab, null);
        TextView textView = (TextView) tabView.findViewById(R.id.tv_tab_title);
        textView.setText("标题" + i);
        return textView;
    }


    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> titles = new ArrayList<>();

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addTab(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
