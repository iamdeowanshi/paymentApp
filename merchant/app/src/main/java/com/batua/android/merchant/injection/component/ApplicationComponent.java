package com.batua.android.merchant.injection.component;

import com.batua.android.merchant.BatuaApplication;
import com.batua.android.merchant.data.api.ApiErrorParser;
import com.batua.android.merchant.injection.module.ApiModule;
import com.batua.android.merchant.injection.module.ApplicationModule;
import com.batua.android.merchant.injection.module.PresenterModule;
import com.batua.android.merchant.injection.module.UtilModule;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ConnectivityUtil;
import com.batua.android.merchant.module.common.util.DisplayUtil;
import com.batua.android.merchant.module.common.util.ImageUtil;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.common.util.social.SocialAuth;
import com.batua.android.merchant.module.dashboard.presenter.LogoutPresenter;
import com.batua.android.merchant.module.dashboard.presenter.LogoutPresenterImpl;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenterImpl;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;
import com.batua.android.merchant.module.dashboard.view.fragment.NavigationFragment;
import com.batua.android.merchant.module.merchant.presenter.CityPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.ImageUploadPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenterImpl;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.GalleryImagesActivity;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;
import com.batua.android.merchant.module.merchant.view.adapter.GalleryViewPagerAdapter;
import com.batua.android.merchant.module.merchant.view.fragment.MerchantBankInfoFragment;
import com.batua.android.merchant.module.merchant.view.fragment.MerchantBasicInfoFragment;
import com.batua.android.merchant.module.merchant.view.fragment.MerchantLocationInfoFragment;
import com.batua.android.merchant.module.onboard.presenter.LoginPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.OtpPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.ResetPasswordPresenterImpl;
import com.batua.android.merchant.module.onboard.presenter.VerifyOtpPresenterImpl;
import com.batua.android.merchant.module.onboard.view.activity.ForgotPasswordActivity;
import com.batua.android.merchant.module.onboard.view.activity.LoginActivity;
import com.batua.android.merchant.module.onboard.view.activity.OtpActivity;
import com.batua.android.merchant.module.onboard.view.activity.ResetPasswordActivity;
import com.batua.android.merchant.module.onboard.view.activity.SplashActivity;
import com.batua.android.merchant.module.profile.presenter.ProfilePresenterImpl;
import com.batua.android.merchant.module.profile.view.activity.EditProfileActivity;
import com.batua.android.merchant.module.profile.view.activity.ProfileActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Farhan Ali
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class,
        PresenterModule.class,
        UtilModule.class,
})
public interface ApplicationComponent {
    void inject(BatuaApplication batuaApplication);
    void inject(ApiErrorParser apiErrorParser);

    // inject methods for view classes
    void inject(LoginActivity loginActivity);
    void inject(EditProfileActivity editProfileActivity);
    void inject(ForgotPasswordActivity forgotPasswordActivity);
    void inject(OtpActivity otpActivity);
    void inject(ResetPasswordActivity resetPasswordActivity);
    void inject(GalleryViewPagerAdapter galleryViewPagerAdapter);
    void inject(GalleryImagesActivity galleryImagesActivity);
    void inject(AddMerchantActivity addMerchantActivity);
    void inject(EditMerchantActivity editMerchantActivity);
    void inject(HomeActivity homeActivity);
    void inject(ProfileActivity profileActivity);
    void inject(SplashActivity splashActivity);

    void inject(MerchantBasicInfoFragment merchantBasicInfoFragment);
    void inject(MerchantBankInfoFragment merchantBankInfoFragment);
    void inject(MerchantDetailsActivity merchantDetailsActivity);
    void inject(MerchantLocationInfoFragment merchantLocationInfoFragment);
    void inject(NavigationFragment navigationFragment);

    // inject methods for presenter classes
    void inject(MerchantPresenterImpl merchantPresenter);
    void inject(MerchantListPresenterImpl merchantListPresenter);
    void inject(CityPresenterImpl cityPresenter);
    void inject(MerchantCategoryPresenterImpl categoryPresenter);
    void inject(ImageUploadPresenterImpl imageUploadPresenter);
    void inject(ProfilePresenterImpl profilePresenter);
    void inject(LoginPresenterImpl loginPresenter);
    void inject(LogoutPresenterImpl logoutPresenter);
    void inject(OtpPresenterImpl otpPresenter);
    void inject(VerifyOtpPresenterImpl verifyOtpPresenter);
    void inject(ResetPasswordPresenterImpl resetPasswordPresenter);


    // inject methods for util classes
    void inject(PreferenceUtil preferenceUtil);
    void inject(Bakery bakery);
    void inject(ConnectivityUtil connectivityUtil);
    void inject(PermissionUtil permissionUtil);
    void inject(DisplayUtil displayUtil);
    void inject(ImageUtil imageUtil);
    void inject(ViewUtil viewUtil);
    void inject(SocialAuth socialAuth);

}
