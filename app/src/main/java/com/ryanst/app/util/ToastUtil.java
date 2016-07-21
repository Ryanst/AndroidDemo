package com.ryanst.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kevin on 16/3/22.
 */
public class ToastUtil {
    private static Toast toast = null;

    private static void showMessage(final Context context, final String message, final int length) {
        try {
            if (toast == null) {
                toast = Toast.makeText(context, message, length);
            } else {
                toast.setText(message);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
            toast = null;
        }
    }

    public static void showToastShort(Context context, int resId) {
        showMessage(context, context.getString(resId), Toast.LENGTH_SHORT);

    }

    public static void showToastLong(Context context, int resId) {
        showMessage(context, context.getString(resId), Toast.LENGTH_LONG);

    }

    public static void showToastShort(Context context, String resId) {
        showMessage(context, resId, Toast.LENGTH_SHORT);

    }

    public static void showToastLong(Context context, String resId) {
        showMessage(context, resId, Toast.LENGTH_LONG);
    }

}
