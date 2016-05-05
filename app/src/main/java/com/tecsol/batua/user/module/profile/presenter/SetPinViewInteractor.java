package com.tecsol.batua.user.module.profile.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Arnold.
 */
public interface SetPinViewInteractor extends NetworkViewInteractor {

    void onPinSet(String message);

}
