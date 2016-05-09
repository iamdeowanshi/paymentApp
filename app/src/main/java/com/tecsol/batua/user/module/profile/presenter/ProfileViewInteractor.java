package com.tecsol.batua.user.module.profile.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface ProfileViewInteractor extends NetworkViewInteractor {

    void onProfileUpdated(User user);

}
