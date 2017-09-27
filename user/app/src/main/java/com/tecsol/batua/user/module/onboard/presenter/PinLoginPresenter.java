package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Arnold
 */
public interface PinLoginPresenter extends Presenter<PinLoginViewInteractor> {

    void loginByPin(Pin pin, String accessToken);

}
