package com.ryanst.app.util;

import android.content.Context;
import android.os.Build;

/**
 * Created by kevin on 16/6/16.
 */
public class ColorUtil {
    public static int getColor(Context context,int colorResId){
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            result  = context.getResources().getColor(colorResId,null);
        }else {
            result  = context.getResources().getColor(colorResId);
        }
        return result;
    }
}
