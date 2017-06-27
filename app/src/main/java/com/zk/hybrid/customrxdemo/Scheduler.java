package com.zk.hybrid.customrxdemo;

import java.util.concurrent.Executor;

/**
 * author: ZK.
 * date:   On 2017/6/27.
 * 任务调度器 */
public class Scheduler {

    public Executor mExecutor;

    public Scheduler(Executor executor) {
        mExecutor = executor;
    }

    public Worker createWorker() {
        return new Worker(mExecutor);
    }

    public static class Worker {
        public Executor executor;

        public Worker(Executor executor) {
            this.executor = executor;
        }


        public void schedule(Runnable runnable) {
            executor.execute(runnable);
        }
    }
}
