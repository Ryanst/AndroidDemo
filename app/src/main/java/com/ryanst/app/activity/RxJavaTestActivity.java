package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.databinding.ActivitySingleButtonBinding;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhengjuntong on 16/6/21.
 */
public class RxJavaTestActivity extends BaseSlideActivity {

    ActivitySingleButtonBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_button);
        initView();
    }

    private void initView() {
        RxView.clicks(binding.btnSimple)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxonClick();
                    }
                });
    }

    public void RxonClick() {
//        Observable.just(1, 2, 3, 4)
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer number) {
//                        log("number:" + number);
//                    }
//                });

        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
//                .observeOn(Schedulers.newThread())
//                .subscribeOn(Schedulers.newThread())
//                .doOnSubscribe(new Action0() { //doOnSubscribe 中 call 执行的线程取决于它后面第一个subscribeOn的线程
//                    @Override
//                    public void call() {
//                        logger(Thread.currentThread().getId() + ": doOnSubscribe");
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .doOnNext(new Action1<Integer>() { //doOnSubscribe 中 call 执行的线程取决于它上面的observeOn的线程
//                    @Override
//                    public void call(Integer integer) {
//                        logger(Thread.currentThread().getId() + ": doOnNext");
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                }) // 新线程，由 observeOn() 指定
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String str) {
                        return str + "plus1 ";
                    }
                }) // IO 线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String str) {
                        return str + "plus2 ";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(RxJavaTestActivity.this, "subscribe", Toast.LENGTH_SHORT).show();
                        logger("subscribe");
                    }
                });  // Android 主线程，由 observeOn() 指定
    }
}
