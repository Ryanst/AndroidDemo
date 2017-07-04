package com.ryanst.app.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by kevin on 16/6/7.
 */
public class SeaFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> list;

    public SeaFragmentPagerAdapter(FragmentManager fm, List<T> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list == null || list.size() == 0 ? null : list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}
