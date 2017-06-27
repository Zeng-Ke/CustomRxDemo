package com.zk.hybrid.customrxdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Observerable.create(new Observerable.onSubcribe<Integer>() {
            @Override
            public void call(Subscriber subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Integer var) {
                Log.d("===================", var.toString());
            }
        });

        //-------------------map------------------------------------------
        Observerable.create(new Observerable.onSubcribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).map1(new Observerable.Transformer<Integer, String>() {
            @Override
            public String call(Integer from) {
                return "map :  " + from;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String var) {
                Log.d("=======map1============", var.toString());
            }
        });


        //-------------------改进后map------------------------------------------
        Observerable.create(new Observerable.onSubcribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).map2(new Observerable.Transformer<Integer, String>() {
            @Override
            public String call(Integer from) {
                return "map :  " + from;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String var) {
                Log.d("========map2===========", var.toString());
            }
        });


        //================subscribeOn=================================
        Observerable.create(new Observerable.onSubcribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d("====subscribeOn Thread=", Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Integer var) {

                    }
                });


        //================observerOn=================================
        Observerable.create(new Observerable.onSubcribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.d("====subscribeOn Thread=", Thread.currentThread().getName());
            }
        }).observerOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Integer var) {
                        Log.d("====observerOn Thread=", Thread.currentThread().getName());
                    }
                });

    }


}
