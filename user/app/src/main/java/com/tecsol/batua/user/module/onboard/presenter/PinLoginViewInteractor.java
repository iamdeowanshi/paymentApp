package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Arnold
 */
public interface PinLoginViewInteractor extends NetworkViewInteractor {

    void onPinLoginSuccess(User user);

}
