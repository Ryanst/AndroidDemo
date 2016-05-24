package com.ryanst.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.ryanst.app.activity.NetChangeBroadcastReceiverActivity;
import com.ryanst.app.activity.ScheduleTaskActivity;
import com.ryanst.app.activity.TextViewLetterSpacingActivity;
import com.ryanst.app.widget.ActivityHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;


public class MainActivity extends AppCompatActivity {

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
                ActivityHandler.toNavigationDrawerActivity(MainActivity.this);
                break;
            case R.id.btn_tab_fragment:
                ActivityHandler.toTabFragmentActivity(MainActivity.this);
                break;
            case R.id.login:
                ActivityHandler.toLoginActivity(MainActivity.this);
                break;
            case R.id.glide:
                ActivityHandler.toGlideActivity(MainActivity.this);
                break;
            case R.id.permission:
                ActivityHandler.toPermissionActivity(MainActivity.this);
                break;
            case R.id.webview_error:
                ActivityHandler.toWebviewErrorActivity(MainActivity.this);
                break;
            case R.id.test_handler:
                ActivityHandler.toHandlerActivity(MainActivity.this);
                break;
            case R.id.test_data_binding:
                ActivityHandler.toDataBindingActivity(MainActivity.this);
                break;
            case R.id.test_handlerthread:
                ActivityHandler.toHandlerThreadActivity(MainActivity.this);
                break;
            case R.id.test_tablayout_viewpager:
                ActivityHandler.toTabLayoutViewPagerActivity(MainActivity.this);
                break;
            case R.id.btn_photo_test:
                ActivityHandler.toPhotoCameraActivity(MainActivity.this);
                break;
            case R.id.test_map_to_list:
                TestMapToList();
                break;
            case R.id.btn_spinner_test:
                ActivityHandler.toSpinnerActivity(MainActivity.this);
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
