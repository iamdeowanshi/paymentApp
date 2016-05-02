package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface SignUpPresenter extends Presenter<SignUpViewInteractor> {

    void normalSignUp(User user);

    void socialSignUp(String email, String name, String googleId);

}
