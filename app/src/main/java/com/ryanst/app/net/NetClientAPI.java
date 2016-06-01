package com.ryanst.app.net;

import android.widget.Toast;

import com.ryanst.app.core.RyanstApp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.rlib.util.AndroidNetworkUtil;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kevin on 16/5/7.
 */
public class NetClientAPI {
    public static final int OUT_TIME = 60;

    public static class NetConfig {
        public static final String HOST = false ? "http://test.mobile.haibian.com" : "http://mobile.haibian.com";
    }

    private static RestInterface restService;

    static {
        // 设置拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!AndroidNetworkUtil.isNetworkAvailable(RyanstApp.getApplication())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!AndroidNetworkUtil.isNetworkAvailable(RyanstApp.getApplication())) {
                    int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，
                            // 会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置缓存路径
        File httpCacheDirectory = new File("", "responses");
        //设置缓存 20M
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .readTimeout(OUT_TIME, TimeUnit.SECONDS)//set out time
                .connectTimeout(OUT_TIME, TimeUnit.SECONDS)//set out time
                .cache(cache)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetConfig.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestInterface.class);
    }

    private static boolean checkNetwork() {
        if (AndroidNetworkUtil.isNetworkAvailable(RyanstApp.getApplication())) {
            return true;
        } else {

            new Toast(RyanstApp.getApplication()).show();

            Toast.makeText(RyanstApp.getApplication(), "没网络", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
