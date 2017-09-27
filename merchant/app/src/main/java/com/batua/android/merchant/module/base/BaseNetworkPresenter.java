package com.batua.android.merchant.module.base;

import com.batua.android.merchant.data.api.ApiObserver;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * An extension of BasePresenter with facility to subscribe and unsubscribe rx.subscription.
 * Presenters that are going to interact with api/network can extend this.
 *
 * @author Farhan Ali
 */
public abstract class BaseNetworkPresenter<T extends ViewInteractor> extends BasePresenter<T> {

    protected CompositeSubscription subscription = new CompositeSubscription();

    @Override
    public void attachViewInteractor(T viewInteractor) {
        RxSubscriber.validateSubscription(subscription);
        super.attachViewInteractor(viewInteractor);
    }

    @Override
    public void detachViewInteractor() {
        RxSubscriber.unsubscribe(subscription);
        super.detachViewInteractor();
    }

    protected void subscribeForNetwork(Observable resultObservable, ApiObserver apiObserver) {
        RxSubscriber.subscribe(subscription, resultObservable, apiObserver);
    }

}
