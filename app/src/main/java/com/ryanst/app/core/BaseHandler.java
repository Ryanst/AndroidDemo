package com.ryanst.app.core;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by kevin on 16/6/20.
 */
public abstract class BaseHandler<T extends Context> extends Handler {
    protected WeakReference<T> mReference;

    public BaseHandler(T t) {
        mReference = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mReference == null || mReference.get() == null) {
            return;
        }
        handleMyMessage(msg);
    }

    public abstract void handleMyMessage(Message msg);
}
