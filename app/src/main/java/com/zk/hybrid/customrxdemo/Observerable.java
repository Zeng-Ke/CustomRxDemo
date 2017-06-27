package com.zk.hybrid.customrxdemo;

import android.widget.ImageView;

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

    public static <T> Observerable<T> create(onSubcribe<T> onSubcribe) {
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

    /**
     * 转换字符
     * 把上层的数据源和下层观察者之间桥接了一个新的Observerable。桥接的Observerable内部实例化新的OnSubcribe和subscriber。
     *
     * @param transformer
     * @param <R>
     * @return
     */
    public <R> Observerable<R> map1(final Transformer<? super T, ? extends R> transformer) {
        return create(new onSubcribe<R>() {
            @Override
            public void call(final Subscriber<? super R> subscriber) {
                Observerable.this.subscribe(new Subscriber<T>() {
                    @Override
                    public void onComplete() {
                        subscriber.onComplete();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        subscriber.onError(throwable);
                    }

                    @Override
                    public void onNext(T var) {
                        subscriber.onNext(transformer.call(var));
                    }
                });
            }
        });
    }


    //=========================================================================================================

    /**
     * 优化后的map
     *
     * @param transformer
     * @param <R>
     * @return
     */
    public <R> Observerable<R> map2(Transformer<? super T, ? extends R> transformer) {
        return create(new MapSubscribe<T, R>(this, transformer));
    }


    public class MapSubscribe<T, R> implements Observerable.onSubcribe {
        final Observerable<T> source;
        final Observerable.Transformer<? super T, ? extends R> tranFromer;

        public MapSubscribe(Observerable<T> source, Transformer<? super T, ? extends R> tranFromer) {
            this.source = source;
            this.tranFromer = tranFromer;
        }

        @Override
        public void call(Subscriber subscriber) {
            source.subscribe(new MapSubScriber<R, T>(subscriber, tranFromer));
        }
    }


    public class MapSubScriber<T, R> extends Subscriber<R> {
        final Subscriber<? super T> mTSubscriber;
        final Observerable.Transformer<? super R, ? extends T> mTransformer;

        public MapSubScriber(Subscriber<? super T> tSubscriber, Transformer<? super R, ? extends T> transformer) {
            mTSubscriber = tSubscriber;
            mTransformer = transformer;
        }


        @Override

        public void onComplete() {
            mTSubscriber.onComplete();
        }

        @Override
        public void onError(Throwable throwable) {
            mTSubscriber.onError(throwable);
        }

        @Override
        public void onNext(R var) {
            mTSubscriber.onNext(mTransformer.call(var));
        }


    }


    //=========================================================================================================


    public Observerable<T> subscribeOn(final Scheduler scheduler) {
        return Observerable.create(new onSubcribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                subscriber.onStart();
                scheduler.createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        Observerable.this.mOnSubcribe.call(subscriber);
                    }
                });
            }
        });
    }


    public interface onSubcribe<T> {
        void call(Subscriber<? super T> subscriber);
    }

    public interface Transformer<T, R> {
        R call(T from);
    }
}
