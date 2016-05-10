package com.ryanst.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.ryanst.app.core.BaseFragment;
import com.ryanst.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/6.
 */
public class PagerFragment extends BaseFragment {

    public static final String COLORS_RES = "colorsRes";
    private static final int LAZY_LOAD = 1;
    public static final int DELAY_MILLIS = 1000;
    public static final String LAZY_LOAD_COLORS = "lazy_load_colors";
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.view3)
    View view3;

    private static int[] colors;

    private boolean isViewCreated = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LAZY_LOAD:
                    toast("lazyLoad success");
                    int lazyColors[] = msg.getData().getIntArray(LAZY_LOAD_COLORS);
                    setViewBackgroundColor(lazyColors);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, null);
        ButterKnife.bind(this, view);
        setColorRes();
        isViewCreated = true;
        return view;
    }

    private void setColorRes() {
        Bundle bundle = getArguments();
        colors = bundle.getIntArray(COLORS_RES);
        setViewBackgroundColor(colors);
    }

    public static PagerFragment createPagerFragment(int... bgColor) {
        Bundle bundle = new Bundle();
        bundle.putIntArray(COLORS_RES, bgColor);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setViewBackgroundColor(int bgColor[]) {
        int len = bgColor.length;
        if (len >= 1) {
            view1.setBackgroundColor(bgColor[0]);
            if (len >= 2) {
                view2.setBackgroundColor(bgColor[1]);
                if (len >= 3) {
                    view3.setBackgroundColor(bgColor[2]);
                }
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d(isVisibleToUser ? "visiable" : "invisiable");
        if (isVisibleToUser && isViewCreated) {
            lazyLoad();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeMessages(LAZY_LOAD);
    }

    private void lazyLoad() {
        //模拟加载操作
        int lazyColors[] = new int[]{
                getResources().getColor(R.color.Aqua)
        };
        Bundle bundle = new Bundle();
        bundle.putIntArray(LAZY_LOAD_COLORS, lazyColors);
        Message message = new Message();
        message.setData(bundle);
        message.what = LAZY_LOAD;
        handler.sendMessageDelayed(message, DELAY_MILLIS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            lazyLoad();
        }
    }
}
