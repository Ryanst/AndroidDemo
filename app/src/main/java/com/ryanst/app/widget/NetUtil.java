package com.ryanst.app.widget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhengjuntong on 16/5/21.
 */
public class NetUtil {

    public enum NetType {NO_NETWORK_CONNECTED, WIFI_CONNECTED, OTHER_NETWORK_CONNECTED}

    private Context mContext;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public NetUtil(Context context) {
        this.mContext = context;
        connectivityManager = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }

    public boolean isConnected() {
        return networkInfo != null && networkInfo.isConnected();
    }

    public NetType getNetType() {
        if (!isConnected()) {
            return NetType.NO_NETWORK_CONNECTED;
        }
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI_CONNECTED;
        } else {
            return NetType.OTHER_NETWORK_CONNECTED;
        }
    }
}
