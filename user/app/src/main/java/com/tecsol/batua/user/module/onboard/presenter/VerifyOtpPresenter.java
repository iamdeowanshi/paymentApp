package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.Otp;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface VerifyOtpPresenter extends Presenter<VerifyOtpViewIteractor> {

    void verifySignUpOtp(Otp otp);

    void verifyForgotPinPasswordOtp(Otp otp);

}
