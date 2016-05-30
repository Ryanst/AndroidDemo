package com.ryanst.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.fragment.TabFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevin on 16/5/10.
 */
public class TabFragmentActivity extends BaseSlideActivity {

    public static final int TAB_ONE = 0;
    public static final String TAB_ONE_TAG = "tab_one";
    public static final String TAB_ONE_TITLE = "Fragment 1";

    public static final int TAB_TWO = 1;
    public static final String TAB_TWO_TAG = "tab_two";
    public static final String TAB_TWO_TITLE = "Fragment 2";

    public static final int TAB_THREE = 2;
    public static final String TAB_THREE_TAG = "tab_three";
    public static final String TAB_THREE_TITLE = "Fragment 3";

    public static final int TAB_FOUR = 3;
    public static final String TAB_FOUR_TAG = "tab_four";
    public static final String TAB_Four_TITLE = "Fragment 4";

    private FragmentManager fragmentManager;

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.rg_tab_parent)
    RadioGroup rgTabParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_tab_fragment);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        setDefaultFragment(TAB_ONE);
        setRadioGroupTabEvent();
    }

    private void setDefaultFragment(int index) {
        RadioButton radioButton = (RadioButton) rgTabParent.getChildAt(index);
        radioButton.setChecked(true);
        switchTab(index);
    }

    private void setRadioGroupTabEvent() {
        rgTabParent.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = group.indexOfChild(group.findViewById(checkedId));
                switchTab(index);
            }
        });
    }

    private void switchTab(int index) {
        hideAllFragment();
        switch (index) {
            case TAB_ONE:
                showFragment(TAB_ONE_TAG, TAB_ONE_TITLE);
                break;
            case TAB_TWO:
                showFragment(TAB_TWO_TAG, TAB_TWO_TITLE);
                break;
            case TAB_THREE:
                showFragment(TAB_THREE_TAG, TAB_THREE_TITLE);
                break;
            case TAB_FOUR:
                showFragment(TAB_FOUR_TAG, TAB_Four_TITLE);
                break;
            default:
                break;
        }
    }

    private void showFragment(String tag, String title) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = TabFragment.getInstance(title);
            fragmentTransaction.replace(R.id.fl_content, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void hideAllFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) return;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment == null) continue;
            transaction.hide(fragment);
        }
        transaction.commitAllowingStateLoss();
    }
}
