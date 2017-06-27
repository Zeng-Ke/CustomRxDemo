package com.zk.hybrid.customrxdemo;

/**
 * author: ZK.
 * date:   On 2017/6/27.
 * 观察者
 */
public interface Observer<T> {
    void onComplete();

    void onError(Throwable throwable);

    void onNext(T var);
}
