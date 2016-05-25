package com.ryanst.app.widget;

import android.content.Context;
import android.content.Intent;

import com.ryanst.app.activity.DataBindingActivity;
import com.ryanst.app.activity.GlideActivity;
import com.ryanst.app.activity.HandlerTestActivity;
import com.ryanst.app.activity.HandlerThreadActivity;
import com.ryanst.app.activity.LoginActivity;
import com.ryanst.app.activity.NavigationDrawerActivity;
import com.ryanst.app.activity.PermissionActivity;
import com.ryanst.app.activity.PhotoCameraActivity;
import com.ryanst.app.activity.SpinnerActivity;
import com.ryanst.app.activity.TabFragmentActivity;
import com.ryanst.app.activity.TabLayoutViewPagerActivity;
import com.ryanst.app.activity.WebviewErrorActivity;

/**
 * Created by kevin on 16/5/10.
 */
public class ActivityHandler {
    public static void toActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void toNavigationDrawerActivity(Context context) {
        context.startActivity(new Intent(context, NavigationDrawerActivity.class));
    }
}
