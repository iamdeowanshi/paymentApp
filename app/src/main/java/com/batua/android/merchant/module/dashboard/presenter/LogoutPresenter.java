package com.batua.android.merchant.module.dashboard.presenter;

import com.batua.android.merchant.module.base.Presenter;
import com.batua.android.merchant.module.base.ViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface LogoutPresenter extends Presenter<LogoutViewInteractor> {

    void logout(String deviceId, int id);

}
