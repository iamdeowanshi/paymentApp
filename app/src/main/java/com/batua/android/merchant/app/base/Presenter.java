package com.batua.android.merchant.app.base;

/**
 * @author Farhan Ali
 */
public interface Presenter<T extends ViewInteractor> {

    void setViewInteractor(T viewInteractor);

    void resume();

    void pause();

}
