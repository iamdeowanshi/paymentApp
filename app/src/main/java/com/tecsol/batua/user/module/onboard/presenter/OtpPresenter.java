package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.data.model.User.Otp;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface OtpPresenter extends Presenter<OtpViewInteractor> {

    void sendSignUpOtp(Otp otp);

}
