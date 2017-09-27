package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Arnold.
 */
public interface ResetPasswordViewInteractor extends NetworkViewInteractor {

    void onPasswordReset(String message);

}
