package com.batua.android.merchant.app.base;

/**
 * @author Farhan Ali
 */
public interface NetworkViewInteractor extends ViewInteractor {

    void showProgress();

    void hideProgress();

    void onNetworkError(Throwable e);

}
