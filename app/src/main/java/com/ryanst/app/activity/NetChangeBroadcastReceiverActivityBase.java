package com.ryanst.app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.util.NetUtil;

/**
 * Created by zhengjuntong on 16/5/21.
 */
public class NetChangeBroadcastReceiverActivityBase extends BaseSlideActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
    }

    public void onClick(View view) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetUtil.NetType networkType = new NetUtil(context).getNetType();
            switch (networkType) {
                case NO_NETWORK_CONNECTED:
                    toast("NO_NETWORK_CONNECTED");
                    break;
                case WIFI_CONNECTED:
                    toast("WIFI_CONNECTED");
                    break;
                case OTHER_NETWORK_CONNECTED:
                    toast("OTHER_NETWORK_CONNECTED");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
