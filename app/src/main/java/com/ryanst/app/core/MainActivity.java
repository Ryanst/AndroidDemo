package com.ryanst.app.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ryanst.app.R;
import com.ryanst.app.activity.CustomViewActivityBase;
import com.ryanst.app.activity.DataBindingActivityBase;
import com.ryanst.app.activity.GlideActivityBase;
import com.ryanst.app.activity.HandlerTestActivityBase;
import com.ryanst.app.activity.HandlerThreadActivityBase;
import com.ryanst.app.activity.LoginActivityBase;
import com.ryanst.app.activity.NavigationDrawerActivityBase;
import com.ryanst.app.activity.NetChangeBroadcastReceiverActivityBase;
import com.ryanst.app.activity.PermissionActivityBase;
import com.ryanst.app.activity.PhotoCameraActivityBase;
import com.ryanst.app.activity.ScheduleTaskActivityBase;
import com.ryanst.app.activity.SpinnerActivityBase;
import com.ryanst.app.activity.TabFragmentActivityBase;
import com.ryanst.app.activity.TabLayoutViewPagerActivityBase;
import com.ryanst.app.activity.TextViewLetterSpacingActivityBase;
import com.ryanst.app.activity.WebviewErrorActivityBase;
import com.ryanst.app.core.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;


public class MainActivity extends BaseActivity {

    public static final String RYANST_LOG = "Juntong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init(RYANST_LOG).logLevel(LogLevel.FULL);
    }

    @DebugLog
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_to_custom_view:
                startActivity(new Intent(this, CustomViewActivityBase.class));
                break;
            case R.id.btn_textview_spacing:
                startActivity(new Intent(this, TextViewLetterSpacingActivityBase.class));
                break;
            case R.id.btn_schedule_task:
                startActivity(new Intent(this, ScheduleTaskActivityBase.class));
                break;
            case R.id.btn_net_change:
                startActivity(new Intent(this, NetChangeBroadcastReceiverActivityBase.class));
                break;
            case R.id.btn_navigation_drawer:
                startActivity(new Intent(this, NavigationDrawerActivityBase.class));
                break;
            case R.id.btn_tab_fragment:
                startActivity(new Intent(this, TabFragmentActivityBase.class));
                break;
            case R.id.login:
                startActivity(new Intent(this, LoginActivityBase.class));
                break;
            case R.id.glide:
                startActivity(new Intent(this, GlideActivityBase.class));
                break;
            case R.id.permission:
                startActivity(new Intent(this, PermissionActivityBase.class));
                break;
            case R.id.webview_error:
                startActivity(new Intent(this, WebviewErrorActivityBase.class));
                break;
            case R.id.test_handler:
                startActivity(new Intent(this, HandlerTestActivityBase.class));
                break;
            case R.id.test_data_binding:
                startActivity(new Intent(this, DataBindingActivityBase.class));
                break;
            case R.id.test_handlerthread:
                startActivity(new Intent(this, HandlerThreadActivityBase.class));
                break;
            case R.id.test_tablayout_viewpager:
                startActivity(new Intent(this, TabLayoutViewPagerActivityBase.class));
                break;
            case R.id.btn_photo_test:
                startActivity(new Intent(this, PhotoCameraActivityBase.class));
                break;
            case R.id.test_map_to_list:
                TestMapToList();
                break;
            case R.id.btn_spinner_test:
                startActivity(new Intent(this, SpinnerActivityBase.class));
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
