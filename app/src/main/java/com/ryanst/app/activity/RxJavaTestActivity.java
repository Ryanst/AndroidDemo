package com.ryanst.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jakewharton.rxbinding.view.RxView;
import com.ryanst.app.R;
import com.ryanst.app.core.BaseSlideActivity;
import com.ryanst.app.databinding.ActivitySingleButtonBinding;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhengjuntong on 16/6/21.
 */
public class RxJavaTestActivity extends BaseSlideActivity {

    public static final String TAG = "RxJavaTestActivity";
    ActivitySingleButtonBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_button);
        initView();
    }

    private void initView() {
        RxView.clicks(binding.btnTestOrder)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        testOrder();
                    }
                });

        RxView.clicks(binding.btnTestThread)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        testThread();
                    }
                });
    }

    public void testThread() {
//        Observable.just(1, 2, 3, 4)
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer number) {
//                        log("number:" + number);
//                    }
//                });

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("OnSubscribe start\t" + Thread.currentThread().getId());
                subscriber.onStart();//thread 2
                log("OnSubscribe onNext1\t" + Thread.currentThread().getId());
                subscriber.onNext(1);//thread 2
                log("OnSubscribe onNext2\t" + Thread.currentThread().getId());
                subscriber.onCompleted();//thread 2
                log("OnSubscribe onCompleted\t" + Thread.currentThread().getId());//thread 1
            }
        })
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        log("map1\t" + Thread.currentThread().getId());
                        return integer + 10;//thread 2
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        log("map2\t" + Thread.currentThread().getId());
                        return String.valueOf(integer);//thread 2
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        log("doOnSubscribe\t" + Thread.currentThread().getId());//thread 3由下一个subscribeOn决定
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log("doOnNext\t" + Thread.currentThread().getId());//thread 3由下一个subscribeOn决定
                    }
                })
                .subscribeOn(Schedulers.newThread())
                // 新线程，由 observeOn() 指定
                .observeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String str) {
                        log("map3\t" + Thread.currentThread().getId());
                        return str + "plus1 ";//thread 4
                    }
                })
                // IO 线程，由 observeOn() 指定
                .observeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String str) {
                        log("map4\t" + Thread.currentThread().getId());
                        return str + "plus2 ";//thread 5
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        log("Subscriber.onStart\t" + Thread.currentThread().getId());//thread 1
                    }

                    @Override
                    public void onCompleted() {
                        log("Subscriber.onCompleted\t" + Thread.currentThread().getId());//thread 1
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        log("Subscriber.onNext\t" + Thread.currentThread().getId());//thread 1
                    }
                });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("\n--------------------------------");
    }

    private void testOrder() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onCompleted();
            }
        })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        if ("1".equals(s)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                })
                .flatMap(new Func1<Integer, Observable<Double>>() {
                    @Override
                    public Observable<Double> call(Integer integer) {
                        if (integer == 1) {
                            return Observable.just(1.00);
                        } else {
                            return Observable.just(2.00);
                        }
                    }
                })
                .subscribe(new Subscriber<Double>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Double aDouble) {
                        Log.d(TAG, "onNext: " + aDouble);
                    }
                });
    }
}
