package com.batua.android.merchant.module.profile.presenter;

import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface ProfilePresenter extends Presenter<ProfileViewInteractor> {

    void getProfile(int id);

    void updateProfile(User user);

}
