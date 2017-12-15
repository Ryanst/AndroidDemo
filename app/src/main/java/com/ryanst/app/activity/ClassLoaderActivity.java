package com.ryanst.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ISayHello;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseActivity;
import com.ryanst.app.core.MainActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import dalvik.system.DexClassLoader;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by zhengjt on 2017/12/15.
 */

public class ClassLoaderActivity extends BaseActivity {
    private static final String TAG = "ClassLoaderActivity";
    public static final int REQUEST_CODE = 666;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_button);
    }

    public void onClick(View view) {
        RxPermissions.getInstance(this).request(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE);

        // 获取到包含 class.dex 的 jar 包文件
        final File jarFile =
                new File(Environment.getExternalStorageDirectory().getPath() + File.separator
                        + "zhengjt" + File.separator + "hello_dex.jar");

        // 如果没有读权限,确定你在 AndroidManifest 中是否声明了读写权限
        Log.d(TAG, jarFile.canRead() + "");

        if (!jarFile.exists()) {
            Log.e(TAG, "hello_dex.jar not exists");
            return;
        }

        // getCodeCacheDir() 方法在 API 21 才能使用,实际测试替换成 getExternalCacheDir() 等也是可以的
        // 只要有读写权限的路径均可
        DexClassLoader dexClassLoader =
                new DexClassLoader(jarFile.getAbsolutePath(), getExternalCacheDir().getAbsolutePath(), null, getClassLoader());
        try {
            // 加载 HelloAndroid 类
            Class clazz = dexClassLoader.loadClass("com.example.HelloAndroid");
            // 强转成 ISayHello, 注意 ISayHello 的包名需要和 jar 包中的一致
            ISayHello iSayHello = (ISayHello) clazz.newInstance();
            Log.i(TAG, iSayHello.say());
            Toast.makeText(this, iSayHello.say(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
