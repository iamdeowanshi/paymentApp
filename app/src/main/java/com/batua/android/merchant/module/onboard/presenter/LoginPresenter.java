package com.batua.android.merchant.module.onboard.presenter;

import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface LoginPresenter extends Presenter<LoginViewInteractor> {

    void normalLogin(String email, String password);

    void socialLogin(String email, String socialId);
}
