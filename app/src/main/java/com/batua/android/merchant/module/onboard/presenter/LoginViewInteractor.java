package com.batua.android.merchant.module.onboard.presenter;

import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.module.base.NetworkViewInteractor;
import com.batua.android.merchant.module.base.ViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface LoginViewInteractor extends NetworkViewInteractor {

    void onLoginSuccessful(User user);

    void onLoginFailed(String message);

}
