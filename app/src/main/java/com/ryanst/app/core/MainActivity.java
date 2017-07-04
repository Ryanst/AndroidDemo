package com.ryanst.app.core;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ryanst.app.R;
import com.ryanst.app.activity.AnotationActivity;
import com.ryanst.app.activity.BallActivity;
import com.ryanst.app.activity.CustomViewActivity;
import com.ryanst.app.activity.DataBindingActivity;
import com.ryanst.app.activity.GlideActivity;
import com.ryanst.app.activity.HandlerTestActivity;
import com.ryanst.app.activity.HandlerThreadActivity;
import com.ryanst.app.activity.LeakTestActivity;
import com.ryanst.app.activity.LoginActivity;
import com.ryanst.app.activity.MenuTestActivity;
import com.ryanst.app.activity.NavBarTestActivity;
import com.ryanst.app.activity.NavigationDrawerActivity;
import com.ryanst.app.activity.NetChangeBroadcastReceiverActivity;
import com.ryanst.app.activity.PermissionActivity;
import com.ryanst.app.activity.PhotoCameraActivity;
import com.ryanst.app.activity.RxJavaTestActivity;
import com.ryanst.app.activity.ScheduleTaskActivity;
import com.ryanst.app.activity.ScrollViewListViewActivity;
import com.ryanst.app.activity.ScrollerLayoutActivity;
import com.ryanst.app.activity.ServiceTestActivity;
import com.ryanst.app.activity.SimpleRecyclerActivity;
import com.ryanst.app.activity.SpinnerActivity;
import com.ryanst.app.activity.TabFragmentActivity;
import com.ryanst.app.activity.TabLayoutViewPagerActivity;
import com.ryanst.app.activity.TagViewContainerActivity;
import com.ryanst.app.activity.TestCrashActivity;
import com.ryanst.app.activity.TestMatActivity;
import com.ryanst.app.activity.TextViewLetterSpacingActivity;
import com.ryanst.app.activity.ToolBarTestActivity;
import com.ryanst.app.activity.TouchEventTestActivity;
import com.ryanst.app.activity.WebviewErrorActivity;
import com.ryanst.app.activity.WheelViewActivity;
import com.ryanst.app.activity.WidgetBallActivity;
import com.ryanst.app.activity.launchMode.LaunchModeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseSlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init("Juntong").logLevel(LogLevel.FULL);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                startActivity(new Intent(this, TestCrashActivity.class));
                break;
            case R.id.btn_test1:
                startActivity(new Intent(this, SimpleRecyclerActivity.class));
                break;
            case R.id.btn_container:
                startActivity(new Intent(this, TagViewContainerActivity.class));
                break;
            case R.id.btn_mat:
                startActivity(new Intent(this, TestMatActivity.class));
                break;
            case R.id.btn_test_scroller_layout:
                startActivity(new Intent(this, ScrollerLayoutActivity.class));
                break;
            case R.id.btn_test_launch_mode:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
            case R.id.btn_test_widget_ball:
                startActivity(new Intent(this, WidgetBallActivity.class));
                break;
            case R.id.btn_test_ball:
                startActivity(new Intent(this, BallActivity.class));
                break;
            case R.id.btn_test_anotation:
                startActivity(new Intent(this, AnotationActivity.class));
                break;
            case R.id.btn_touch_event_test:
                startActivity(new Intent(this, TouchEventTestActivity.class));
                break;
            case R.id.btn_to_menu_test:
                startActivity(new Intent(this, MenuTestActivity.class));
                break;
            case R.id.btn_to_scroll_test:
                startActivity(new Intent(this, ScrollViewListViewActivity.class));
                break;
            case R.id.btn_to_service_test:
                startActivity(new Intent(this, ServiceTestActivity.class));
                break;
            case R.id.btn_to_leak_test:
                startActivity(new Intent(this, LeakTestActivity.class));
                break;
            case R.id.btn_to_navbar_test:
                startActivity(new Intent(this, NavBarTestActivity.class));
                break;
            case R.id.btn_to_toolbar_test:
                startActivity(new Intent(this, ToolBarTestActivity.class));
                break;
            case R.id.btn_to_rxjava_test:
                startActivity(new Intent(this, RxJavaTestActivity.class));
                break;
            case R.id.btn_to_wheel_view:
                startActivity(new Intent(this, WheelViewActivity.class));
                break;
            case R.id.btn_to_custom_view:
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.btn_textview_spacing:
                startActivity(new Intent(this, TextViewLetterSpacingActivity.class));
                break;
            case R.id.btn_schedule_task:
                startActivity(new Intent(this, ScheduleTaskActivity.class));
                break;
            case R.id.btn_net_change:
                startActivity(new Intent(this, NetChangeBroadcastReceiverActivity.class));
                break;
            case R.id.btn_navigation_drawer:
                startActivity(new Intent(this, NavigationDrawerActivity.class));
                break;
            case R.id.btn_tab_fragment:
                startActivity(new Intent(this, TabFragmentActivity.class));
                break;
            case R.id.login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.glide:
                startActivity(new Intent(this, GlideActivity.class));
                break;
            case R.id.permission:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case R.id.webview_error:
                startActivity(new Intent(this, WebviewErrorActivity.class));
                break;
            case R.id.test_handler:
                startActivity(new Intent(this, HandlerTestActivity.class));
                break;
            case R.id.test_data_binding:
                startActivity(new Intent(this, DataBindingActivity.class));
                break;
            case R.id.test_handlerthread:
                startActivity(new Intent(this, HandlerThreadActivity.class));
                break;
            case R.id.test_tablayout_viewpager:
                startActivity(new Intent(this, TabLayoutViewPagerActivity.class));
                break;
            case R.id.btn_photo_test:
                startActivity(new Intent(this, PhotoCameraActivity.class));
                break;
            case R.id.test_map_to_list:
                TestMapToList();
                break;
            case R.id.btn_spinner_test:
                startActivity(new Intent(this, SpinnerActivity.class));
                break;
        }
    }

    private void TestMapToList() {
        Map<String, MyObject> map = new HashMap<>();
        MyObject object1 = new MyObject(1);
        MyObject object2 = new MyObject(2);
        map.put("11", object1);
        map.put("22", object2);

        List<MyObject> list = new ArrayList<MyObject>(map.values());

        for (int i = 0; i < list.size(); i++) {
            Log.e("TEST", list.get(i).getNum() + "");
        }
    }

    public class MyObject {
        private int num;

        public MyObject(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

}
