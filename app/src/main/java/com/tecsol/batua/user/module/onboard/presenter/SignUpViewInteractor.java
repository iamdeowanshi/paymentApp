package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface SignUpViewInteractor extends NetworkViewInteractor {

    void onNormalSignUpSuccess(String message);

    void onSocialSignUpSuccess(Integer userId);

}
