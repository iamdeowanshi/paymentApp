package com.tecsol.batua.user.injection.component;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.injection.module.ApiModule;
import com.tecsol.batua.user.injection.module.ApplicationModule;
import com.tecsol.batua.user.injection.module.PresenterModule;
import com.tecsol.batua.user.injection.module.UtilModule;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.ConnectivityUtil;
import com.tecsol.batua.user.module.common.util.ImageUtil;
import com.tecsol.batua.user.module.common.util.PermissionUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.common.util.social.SocialAuth;
import com.tecsol.batua.user.module.dashboard.presenter.ContactUsPresenterImpl;
import com.tecsol.batua.user.module.dashboard.presenter.HomePresenterImpl;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;
import com.tecsol.batua.user.module.dashboard.view.fragment.NavigationFragment;
import com.tecsol.batua.user.module.onboard.presenter.ChangePasswordPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ChangePinPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.PinLoginPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ResetPasswordPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpPresenterImpl;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePasswordActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ChangePinActivity;
import com.tecsol.batua.user.module.onboard.view.activity.MobileNumberActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;
import com.tecsol.batua.user.module.onboard.view.activity.OtpActivity;
import com.tecsol.batua.user.module.onboard.view.activity.PinLoginActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ResetPasswordActivity;
import com.tecsol.batua.user.module.onboard.view.activity.ResetPinActivity;
import com.tecsol.batua.user.module.onboard.view.activity.SetPinActivity;
import com.tecsol.batua.user.module.onboard.view.activity.SplashActivity;
import com.tecsol.batua.user.module.onboard.view.fragment.LoginFragment;
import com.tecsol.batua.user.module.onboard.view.fragment.SignUpFragment;
import com.tecsol.batua.user.module.payment.presenter.DiscountPresenterImpl;
import com.tecsol.batua.user.module.payment.presenter.TransactionPresenterImpl;
import com.tecsol.batua.user.module.payment.view.activity.PaymentSuccessActivity;
import com.tecsol.batua.user.module.payment.view.activity.PrePaymentConfirmationActivity;
import com.tecsol.batua.user.module.profile.presenter.PinStatusPresenterImpl;
import com.tecsol.batua.user.module.profile.presenter.ProfilePresenterImpl;
import com.tecsol.batua.user.module.profile.presenter.SetPinPresenterImpl;
import com.tecsol.batua.user.module.profile.view.activity.EditProfileActivity;
import com.tecsol.batua.user.module.profile.view.activity.ProfileActivity;
import com.tecsol.batua.user.module.review.presenter.ReviewPresenterImpl;
import com.tecsol.batua.user.module.support.activity.ContactUsActivity;

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
    void inject(ApiErrorParser apiErrorParser);

    // inject methods for view classes
    void inject(PrePaymentConfirmationActivity prePaymentConfirmationActivity);
    void inject(PaymentSuccessActivity paymentSuccessActivity);
    void inject(OtpActivity otpActivity);
    void inject(ResetPasswordActivity resetPasswordActivity);
    void inject(OnBoardActivity OnBoardActivity);
    void inject(HomeActivity homeActivity);
    void inject(LoginFragment loginFragment);
    void inject(SignUpFragment signUpFragment);
    void inject(NavigationFragment navigationFragment);
    void inject(ProfileActivity profileActivity);
    void inject(MobileNumberActivity mobileNumberActivity);
    void inject(EditProfileActivity editProfileActivity);
    void inject(SplashActivity splashActivity);
    void inject(SetPinActivity setPinActivity);
    void inject(ChangePinActivity changePinActivity);
    void inject(PinLoginActivity pinLoginActivity);
    void inject(ChangePasswordActivity changePasswordActivity);
    void inject(ResetPinActivity resetPinActivity);
    void inject(ContactUsActivity contactUsActivity);

    // inject methods for presenter classes
    void inject(SignUpPresenterImpl loginPresenter);
    void inject(LoginPresenterImpl loginPresenter);
    void inject(HomePresenterImpl homePresenter);
    void inject(ReviewPresenterImpl reviewPresenter);
    void inject(OtpPresenterImpl otpPresenter);
    void inject(VerifyOtpPresenterImpl verifyOtpPresenter);
    void inject(ProfilePresenterImpl profilePresenter);
    void inject(ImageUploadPresenterImpl imageUploadPresenter);
    void inject(SetPinPresenterImpl setPinPresenter);
    void inject(PinStatusPresenterImpl pinStatusPresenter);
    void inject(ChangePinPresenterImpl changePinPresenter);
    void inject(PinLoginPresenterImpl pinLoginPresenter);
    void inject(ChangePasswordPresenterImpl changePasswordPresenter);
    void inject(ResetPasswordPresenterImpl resetPasswordPresenter);
    void inject(DiscountPresenterImpl discountPresenter);
    void inject(ContactUsPresenterImpl contactUsPresenter);
    void inject(TransactionPresenterImpl transactionPresenter);

    // inject methods for util classes
    void inject(PreferenceUtil preferenceUtil);
    void inject(Bakery bakery);
    void inject(ConnectivityUtil connectivityUtil);
    void inject(PermissionUtil permissionUtil);
    void inject(ImageUtil imageUtil);
    void inject(ViewUtil viewUtil);
    void inject(SocialAuth socialAuth);

}
