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

    }
}
