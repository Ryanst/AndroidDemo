package com.ryanst.app.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.ryanst.app.R;

import java.util.List;

/**
 * Created by gongyu on 16/6/15.
 */
public class WheelViewDialog extends Dialog {

    public WheelViewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private int textSize = -1;
        private int textPadding = -1;
        private int textColor = -1;
        private int offset = -1;
        private int selectTextColor = -1;
        private int flingSpeed = -1;
        private int defaultIndex = -1;
        private WheelView.OnWheelViewListener wheelViewListener;
        private Context context;
        private View rootView;
        private WheelView wheelView;
        private List<String> list;

        public Builder(Context context, View rootView, WheelView wheelView, List<String> list) {
            this.context = context;
            this.rootView = rootView;
            this.wheelView = wheelView;
            this.list = list;
        }

        public Builder setWheelViewListener(WheelView.OnWheelViewListener wheelViewListener) {
            this.wheelViewListener = wheelViewListener;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setTextPadding(int textPadding) {
            this.textPadding = textPadding;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public Builder setSelectTextColor(int selectTextColor) {
            this.selectTextColor = selectTextColor;
            return this;
        }

        public Builder setFlingSpeed(int flingSpeed) {
            this.flingSpeed = flingSpeed;
            return this;
        }

        public Builder setDefaultIndex(int defaultIndex) {
            this.defaultIndex = defaultIndex;
            return this;
        }

        public WheelViewDialog create() {
            if (list == null) {
                return null;
            }

            WheelViewDialog wheelViewDialog = new WheelViewDialog(context, R.style.CustomDialogStyle);

            wheelView.setItems(list);

            if (textSize != -1) {
                wheelView.setTextSize(textSize);
            }
            if (textPadding != -1) {
                wheelView.setTextPadding(textPadding);
            }
            if (textColor != -1) {
                wheelView.setTextColor(textColor);
            }
            if (selectTextColor != -1) {
                wheelView.setSelectTextColor(selectTextColor);
            }
            if (offset != -1) {
                wheelView.setOffset(offset);
            }
            if (flingSpeed != -1) {
                wheelView.setFlingSpeed(flingSpeed);
            }
            if (defaultIndex != -1) {
                wheelView.setDefaultIndex(defaultIndex);
            }
            if (wheelViewListener != null) {
                wheelView.setOnWheelViewListener(wheelViewListener);
            }

            wheelView.refreshView();

            wheelViewDialog.getWindow().setGravity(Gravity.BOTTOM);
            wheelViewDialog.getWindow().setContentView(rootView);
            wheelViewDialog.show();

            return wheelViewDialog;
        }

    }
}
