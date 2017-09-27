package com.batua.android.merchant.module.onboard.presenter;

import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface VerifyOtpPresenter extends Presenter<VerifyOtpViewIteractor> {

    void verifyOtp(String mobile, String otp);

}
