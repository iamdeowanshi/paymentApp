package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.ChangePassword;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Arnold
 */
public interface ChangePasswordPresenter extends Presenter<ChangePasswordViewInteractor> {

    void changePassword(ChangePassword changePassword);

}
