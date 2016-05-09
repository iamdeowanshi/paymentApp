package com.tecsol.batua.user.module.profile.presenter;

import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface PinStatusPresenter extends Presenter<PinStatusViewInteractor> {

    void getPinStatus(int userId);

    void updatePinStatus(Pin pin);

    void logOut(Pin pin);

}
