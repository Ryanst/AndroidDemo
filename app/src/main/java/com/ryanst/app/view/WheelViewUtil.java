package com.ryanst.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.core.RyanstApp;

import java.util.List;

/**
 * Created by gongyu on 16/6/15.
 */
public class WheelViewUtil {

    public static String result;

    public static void createWheelViewDialog(Context context, final View.OnClickListener listener, List<String> list, int offset, String defaultValue) {
        LayoutInflater inflater = LayoutInflater.from(RyanstApp.getApplication());
        View rootView = inflater.inflate(R.layout.dialog_change_grade, null);
        WheelView wheelView = (WheelView) rootView.findViewById(R.id.wheelView);
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(defaultValue)) {
                index = i;
                break;
            }
        }
        wheelView.setSeletion(index);
        wheelView.setOffset(offset);
        wheelView.setItems(list);

        final Dialog dialog = new Dialog(context, R.style.CustomDialogStyle);
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(rootView);

        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                WheelViewUtil.result = item;
            }
        });

        rootView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });
        rootView.findViewById(R.id.tv_positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });
    }


}
