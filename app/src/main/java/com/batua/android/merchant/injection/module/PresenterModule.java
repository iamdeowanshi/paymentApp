package com.batua.android.merchant.injection.module;


import com.batua.android.merchant.module.dashboard.presenter.LogoutPresenter;
import com.batua.android.merchant.module.dashboard.presenter.LogoutPresenterImpl;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenter;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.CityPresenter;
import com.batua.android.merchant.module.merchant.presenter.CityPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadPresenter;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.LoginPresenter;
import com.batua.android.merchant.module.onboard.presenter.LoginPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.OtpPresenter;
import com.batua.android.merchant.module.onboard.presenter.OtpPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.ResetPasswordPresenter;
import com.batua.android.merchant.module.onboard.presenter.ResetPasswordPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.VerifyOtpPresenter;
import com.batua.android.merchant.module.onboard.presenter.VerifyOtpPresenterImpl;
import com.batua.android.merchant.module.profile.presenter.ProfilePresenter;
import com.batua.android.merchant.module.profile.presenter.ProfilePresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Provides all presenter class dependencies.
 *
 * @author Farhan Ali
 */
@Module
public class PresenterModule {

    @Provides
    public MerchantPresenter provideMerchantPresenter() {
        return new MerchantPresenterImpl();
    }

    @Provides
    public MerchantListPresenter provideMerchantListPresenter() {
        return new MerchantListPresenterImpl();
    }

    @Provides
    public MerchantCategoryPresenter provideCategoryPresenter() {
        return new MerchantCategoryPresenterImpl();
    }

    @Provides
    public ImageUploadPresenter provideImageUploadPresenter() {
        return new ImageUploadPresenterImpl();
    }

    @Provides
    public CityPresenter provideCityProvide() {
        return new CityPresenterImpl();
    }

    @Provides
    public ProfilePresenter provideProfilePresenter() {
        return new ProfilePresenterImpl();
    }

    @Provides
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenterImpl();
    }

    @Provides
    public LogoutPresenter provideLogoutPresenter() {
        return new LogoutPresenterImpl();
    }

    @Provides
    public VerifyOtpPresenter provideVerifyOtpPresenter() {
        return new VerifyOtpPresenterImpl();
    }

    @Provides
    public ResetPasswordPresenter provideResetPasswordPresenter() {
        return new ResetPasswordPresenterImpl();
    }

    @Provides
    public OtpPresenter provideOtpPresenter() {
        return new OtpPresenterImpl();
    }

}
