package com.tecsol.batua.user.module.base;

/**
 * @author Farhan Ali
 */
public interface NetworkViewInteractor extends ViewInteractor {

    void onNetworkCallProgress();

    void onNetworkCallCompleted();

    void onNetworkCallError(Throwable e);

}
