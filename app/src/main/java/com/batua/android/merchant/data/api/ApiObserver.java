package com.batua.android.merchant.data.api;

import rx.Observer;

/**
 * Wrapper for rx.Observer with default implementation for onCompleted & onError.
 *
 * @author Farhan Ali
 */
public abstract class ApiObserver<T> implements Observer<T> {

    /**
     * Publish result to observer.
     *
     * @param response
     */
    public abstract void onResponse(T response);

    @Override
    public void onCompleted() {
        // Default implementation, can be override accordingly.
    }

    @Override
    public void onNext(T result) {
        onResponse(result);
    }

}
