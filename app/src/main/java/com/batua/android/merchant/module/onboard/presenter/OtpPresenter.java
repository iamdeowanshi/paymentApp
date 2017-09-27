package com.batua.android.merchant.module.onboard.presenter;

import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface OtpPresenter extends Presenter<OtpViewInteractor> {

    void sendOtp(String mobileNo);

}
