package com.tecsol.batua.user.module.profile.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface PinStatusViewInteractor extends NetworkViewInteractor {

    void onPinStatusRecieved(User user);

    void onPinStatusChanged(User user);

    void onLoggedOutSuccess(String message);

}
