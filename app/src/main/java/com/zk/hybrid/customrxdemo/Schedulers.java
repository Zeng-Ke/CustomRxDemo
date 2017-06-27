package com.zk.hybrid.customrxdemo;

import java.util.concurrent.Executors;

/**
 * author: ZK.
 * date:   On 2017/6/27.
 * description:-- Scheduler的工厂方法
 */
public class Schedulers {

    private static final Scheduler ioScheduler = new Scheduler(Executors.newSingleThreadExecutor());


    public static Scheduler io() {
        return ioScheduler;
    }


}
