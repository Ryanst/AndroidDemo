package com.ryanst.app.widget;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by zhengjuntong on 7/14/16.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File cacheLocation = new File(Constant.IMAGE_PATH);
                cacheLocation.mkdirs();
                return DiskLruCacheWrapper.get(cacheLocation, Constant.DISK_CACHE);

            }
        });
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
