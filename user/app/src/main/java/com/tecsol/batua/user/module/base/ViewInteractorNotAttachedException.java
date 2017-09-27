package com.tecsol.batua.user.module.base;

public class ViewInteractorNotAttachedException extends RuntimeException {

    public ViewInteractorNotAttachedException() {
        super("Please call Presenter.attachViewViewInteractor(viewInteractor) before" +
                " requesting data to the Presenter");
    }

}