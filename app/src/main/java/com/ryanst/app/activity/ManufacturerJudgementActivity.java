package com.ryanst.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;

/**
 * Created by kevin on 16/4/27.
 */
public class ManufacturerJudgementActivity extends BaseSlideActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manufacturer_judge);

        String manufacturer = Build.MANUFACTURER.toLowerCase();
        if (!TextUtils.isEmpty(manufacturer)) {
            if (manufacturer.contains("xiaomi")) {
                toXiaomiPermissionSetting();
            } else if (manufacturer.contains("meizu")) {
                toMeizuPermissionSetting();
            } else if (manufacturer.contains("huawei")) {
                toHuaweiPermissionSetting();
            } else if (manufacturer.contains("samsang")) {
                toSamsangPermissionSetting();
            } else if (manufacturer.contains("htc")) {
                toHtcPermissionSetting();
            } else if (manufacturer.contains("sony")) {
                toSonyPermissionSetting();
            }
        }
    }

    public static boolean isMIUI(Context context) {
        boolean result = false;
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        i.setClassName("com.android.settings",
                "com.miui.securitycenter.permission.AppPermissionsEditor");
        if (isIntentAvailable(context, i)) {
            result = true;
        }
        return result;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        //TODO
//        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
//        return list.size() > 0;
        return false;
    }


    private void toXiaomiPermissionSetting() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        i.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
        i.putExtra("extra_package_uid", info.applicationInfo.uid);
        try {
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "只有MIUI才可以设置哦", Toast.LENGTH_SHORT).show();
        }
    }

    private void toMeizuPermissionSetting() {
    }

    private void toSonyPermissionSetting() {
    }

    private void toHtcPermissionSetting() {
    }

    private void toSamsangPermissionSetting() {
    }

    private void toHuaweiPermissionSetting() {
    }

}
