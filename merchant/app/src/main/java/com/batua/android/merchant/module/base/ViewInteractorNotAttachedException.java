package com.batua.android.merchant.module.base;

public class ViewInteractorNotAttachedException extends RuntimeException {

    public ViewInteractorNotAttachedException() {
        super("Please call Presenter.attachViewViewInteractor(viewInteractor) before" +
                " requesting data to the Presenter");
    }

}