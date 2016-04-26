package com.batua.android.merchant.module.onboard.presenter;

import com.batua.android.merchant.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface VerifyOtpViewIteractor extends NetworkViewInteractor {

    void onVerificationSuccess(Integer id);

    void onVerificationFailure();

}
