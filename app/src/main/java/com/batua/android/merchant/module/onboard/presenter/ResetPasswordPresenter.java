package com.batua.android.merchant.module.onboard.presenter;

import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface ResetPasswordPresenter extends Presenter<ResetPasswordViewInteractor> {

    void resetPassword(String password, int userId);

}
