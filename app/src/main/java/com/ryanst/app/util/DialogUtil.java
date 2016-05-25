package com.ryanst.app.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.ryanst.app.R;
import com.ryanst.app.RyanstApplication;

/**
 * Created by kevin on 16/5/9.
 */
public class DialogUtil {

    public static void createAlbumCameraDialog(Context context, final View.OnClickListener listener) {
        LayoutInflater inflater = LayoutInflater.from(RyanstApplication.getApplication());
        View view = inflater.inflate(R.layout.dialog_album_camera, null);
        final Dialog dialog = new Dialog(context, R.style.CustomDialogStyle);//.Builder(context).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(view);
        view.findViewById(R.id.selectAlbum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });
        view.findViewById(R.id.selectCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });
        view.findViewById(R.id.canceled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return;
    }
}