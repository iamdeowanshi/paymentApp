package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface SavePinPresenter extends Presenter<SavePinViewInteractor> {

    void savePin(int userId, String pin);

}
