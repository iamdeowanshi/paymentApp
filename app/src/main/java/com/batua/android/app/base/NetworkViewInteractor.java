package com.batua.android.app.base;

/**
 * @author Farhan Ali
 */
public interface NetworkViewInteractor extends ViewInteractor {

    void showProgress();

    void hideProgress();

    void onNetworkError(Throwable e);

}
