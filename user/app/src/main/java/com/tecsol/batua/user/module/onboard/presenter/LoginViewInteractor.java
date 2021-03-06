package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface LoginViewInteractor extends NetworkViewInteractor {

    void onNormalLoginSuccess(User user);

    void onSocialLoginSuccess(User user);

}
