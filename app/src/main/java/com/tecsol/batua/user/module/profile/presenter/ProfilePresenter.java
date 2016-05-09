package com.tecsol.batua.user.module.profile.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface ProfilePresenter extends Presenter<ProfileViewInteractor> {

    void updateProfile(User user);

}
