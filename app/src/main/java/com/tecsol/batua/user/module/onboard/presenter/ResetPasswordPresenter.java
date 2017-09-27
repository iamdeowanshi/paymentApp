package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.ChangePassword;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Arnold.
 */
public interface ResetPasswordPresenter extends Presenter<ResetPasswordViewInteractor> {

    void resetPassword(ChangePassword changePassword);

}
