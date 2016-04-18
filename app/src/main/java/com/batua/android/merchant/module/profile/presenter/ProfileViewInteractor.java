package com.batua.android.merchant.module.profile.presenter;

import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.module.base.NetworkViewInteractor;
import com.batua.android.merchant.module.base.ViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface ProfileViewInteractor extends NetworkViewInteractor {

    void showProfile(User user);
}
