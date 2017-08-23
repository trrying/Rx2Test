package com.owm.rx2test.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * http://www.jianshu.com/p/464fa025229e
 * Created by owm on 2017/8/23.
 */

public class R1_BaseOperate {

    public static void observable() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            public void subscribe(ObservableEmitter<String> emitter) {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        });

        Observer<String> observer = new Observer<String>() {
            public void onSubscribe(Disposable disposable) {
                System.out.println("onSubscribe");
            }

            public void onNext(String value) {
                System.out.println("onNext value:"+value);
            }

            public void onError(Throwable throwable) {
                System.out.println("onError msg:"+throwable.getMessage());
                throwable.printStackTrace();
            }

            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        observable.subscribe(observer);
    }

}
