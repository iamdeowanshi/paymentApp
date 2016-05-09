package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.ChangePassword;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.module.base.Presenter;
import com.tecsol.batua.user.module.profile.presenter.SetPinViewInteractor;

/**
 * @author Arnold.
 */
public interface ResetPasswordPresenter extends Presenter<ResetPasswordViewInteractor> {

    void resetPassword(ChangePassword changePassword);

}
