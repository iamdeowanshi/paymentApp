package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface LoginPresenter extends Presenter<LoginViewInteractor> {

    void normalLogin(String username, String password, String deviceId);

    void socialLogin(String email, String deviceId,String facebookId, String goodleId);

}
