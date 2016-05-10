package com.ryanst.app.widget;

import android.content.Context;
import android.content.Intent;

import com.ryanst.app.MainActivity;
import com.ryanst.app.activity.DataBindingActivity;
import com.ryanst.app.activity.GlideActivity;
import com.ryanst.app.activity.HandlerActivity;
import com.ryanst.app.activity.HandlerThreadActivity;
import com.ryanst.app.activity.LoginActivity;
import com.ryanst.app.activity.PermissionActivity;
import com.ryanst.app.activity.PhotoCameraActivity;
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

    public static void toPhotoCameraActivity(Context context) {
        context.startActivity(new Intent(context, PhotoCameraActivity.class));
    }

    public static void toTabLayoutViewPagerActivity(Context context) {
        context.startActivity(new Intent(context, TabLayoutViewPagerActivity.class));
    }

    public static void toHandlerThreadActivity(Context context) {
        context.startActivity(new Intent(context, HandlerThreadActivity.class));
    }

    public static void toDataBindingActivity(Context context) {
        context.startActivity(new Intent(context, DataBindingActivity.class));
    }

    public static void toHandlerActivity(Context context) {
        context.startActivity(new Intent(context, HandlerActivity.class));
    }

    public static void toWebviewErrorActivity(Context context) {
        context.startActivity(new Intent(context, WebviewErrorActivity.class));
    }

    public static void toPermissionActivity(Context context) {
        context.startActivity(new Intent(context, PermissionActivity.class));
    }

    public static void toGlideActivity(Context context) {
        context.startActivity(new Intent(context, GlideActivity.class));
    }

    public static void toLoginActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public static void toTabFragmentActivity(Context context) {
        context.startActivity(new Intent(context, TabFragmentActivity.class));
    }
}
