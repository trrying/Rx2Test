package com.owm.rx2test.rx;

import com.owm.rx2test.util.NetUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * http://www.jianshu.com/p/8818b98c44e2
 * Created by owm on 2017/8/23.
 */

public class R2_ThreadOperator {

    public static void thread() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> emitter) {
                System.out.println("subscribe thread:"+Thread.currentThread().getName());
                emitter.onNext("post");
            }
        });

        Observer observer = new Observer<String>() {
            public void onSubscribe(Disposable disposable) {

            }

            public void onNext(String Value) {

            }

            public void onError(Throwable throwable) {

            }

            public void onComplete() {

            }
        };

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String value) {
                System.out.println("accept value:"+value+" thread:"+Thread.currentThread().getName());
            }
        };

        /**
         * subscribeOn 设置Observable的运行线程，只有第一个有效
         * observeOn   设置Observer  的运行线程，只有其上面最后一个有效
         */
//        observable.subscribe(consumer);
        observable
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }

    public static void threadNet() {
        Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("subscribe");
                emitter.onNext(NetUtils.connection("https://www.baidu.com"));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("accept  value:"+s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("onError");
                        throwable.printStackTrace();
                    }
                });
    }

}



















