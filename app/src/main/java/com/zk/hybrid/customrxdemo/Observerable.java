package com.zk.hybrid.customrxdemo;

/**
 * author: ZK.
 * date:   On 2017/6/27.
 * 被观察者
 */
public class Observerable<T> {

    private onSubcribe<T> mOnSubcribe;


    public Observerable(onSubcribe<T> onSubcribe) {
        mOnSubcribe = onSubcribe;
    }

    public Observerable<T> create(onSubcribe<T> onSubcribe) {
        return new Observerable<>(onSubcribe);
    }

    /**
     * 为什么是被观察者去订阅观察者呢？因为这是为了保证流式API调用风格
     *
     * @param subscriber
     */
    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onStart();
        mOnSubcribe.call(subscriber);
    }


    public interface onSubcribe<T> {
        void call(Subscriber<? super T> subscriber);
    }
}
