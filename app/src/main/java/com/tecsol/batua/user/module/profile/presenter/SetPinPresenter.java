package com.tecsol.batua.user.module.profile.presenter;

import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Arnold.
 */
public interface SetPinPresenter extends Presenter<SetPinViewInteractor> {

    void setPin(Pin pin);

}
