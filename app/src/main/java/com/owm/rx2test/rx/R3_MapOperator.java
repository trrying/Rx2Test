package com.owm.rx2test.rx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * http://www.jianshu.com/p/128e662906af
 *
 * 1、使用操作符，用ObserveOn(Schedulers)来切换显示，subscribeOn只能用来调度Observable
 *
 * Created by owm on 2017/8/23.
 */

public class R3_MapOperator {

    public static void map() {

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> emitter) {
                System.out.println("subscribe");
                System.out.println("threadName : "+Thread.currentThread().getName());
                emitter.onNext("subscribe post");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    public String apply(String value) {
                        System.out.println("map Function apply  value : " + value);
                        System.out.println("threadName : "+Thread.currentThread().getName());
                        return value+" map post";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    public void accept(String value) {
                        System.out.println("consumer accept value : "+value);
                        System.out.println("threadName : "+Thread.currentThread().getName());
                    }
                });
    }

    public static void flatMap() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(10086);
            }
        })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    public ObservableSource<String> apply(Integer value) {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("post flatMap value : "+value);
                        }
                        return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    public void accept(String value) {

                    }
                });

    }

}





























