package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface VerifyOtpViewIteractor extends NetworkViewInteractor {

    void onVerificationSuccess();

    void onVerificationFailure();

}
