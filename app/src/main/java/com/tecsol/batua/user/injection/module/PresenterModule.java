package com.tecsol.batua.user.injection.module;

import com.tecsol.batua.user.module.dashboard.presenter.ContactUsPresenter;
import com.tecsol.batua.user.module.dashboard.presenter.ContactUsPresenterImpl;
import com.tecsol.batua.user.module.dashboard.presenter.HomePresenter;
import com.tecsol.batua.user.module.dashboard.presenter.HomePresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ChangePasswordPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ChangePasswordPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ChangePinPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ChangePinPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.PinLoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.PinLoginPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ResetPasswordPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ResetPasswordPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpPresenterImpl;
import com.tecsol.batua.user.module.payment.presenter.DiscountPresenter;
import com.tecsol.batua.user.module.payment.presenter.DiscountPresenterImpl;
import com.tecsol.batua.user.module.payment.presenter.TransactionPresenter;
import com.tecsol.batua.user.module.payment.presenter.TransactionPresenterImpl;
import com.tecsol.batua.user.module.profile.presenter.PinStatusPresenter;
import com.tecsol.batua.user.module.profile.presenter.PinStatusPresenterImpl;
import com.tecsol.batua.user.module.profile.presenter.ProfilePresenter;
import com.tecsol.batua.user.module.profile.presenter.ProfilePresenterImpl;
import com.tecsol.batua.user.module.profile.presenter.SetPinPresenter;
import com.tecsol.batua.user.module.profile.presenter.SetPinPresenterImpl;

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
    public SignUpPresenter provideSignUpPresenter() {
        return new SignUpPresenterImpl();
    }

    @Provides
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenterImpl();
    }

    @Provides
    public HomePresenter provideHomePresenter() {
        return new HomePresenterImpl();
    }

    @Provides
    public OtpPresenter provideOtpPresenter() {
        return new OtpPresenterImpl();
    }

    @Provides
    public VerifyOtpPresenter provideVerifyOtpPresenter() {
        return new VerifyOtpPresenterImpl();
    }

    @Provides
    public ProfilePresenter provideProfilePresenter() {
        return new ProfilePresenterImpl();
    }

    @Provides
    public ImageUploadPresenter provideImageUploadPresenter() {
        return new ImageUploadPresenterImpl();
    }

    @Provides
    public SetPinPresenter provideSetPinPresenter() {
        return new SetPinPresenterImpl();
    }

    @Provides
    public PinStatusPresenter providePinStatusPresenter() {
        return new PinStatusPresenterImpl();
    }

    @Provides
    public ChangePinPresenter provideChangePinPresenter() {
        return new ChangePinPresenterImpl();
    }

    @Provides
    public PinLoginPresenter providePinLoginPresenter() {
        return new PinLoginPresenterImpl();
    }

    @Provides
    public ChangePasswordPresenter provideChangePasswordPresenter() {
        return new ChangePasswordPresenterImpl();
    }

    @Provides
    public ResetPasswordPresenter provideResetPasswordPresenter() {
        return new ResetPasswordPresenterImpl();
    }

    @Provides
    public DiscountPresenter provideDiscountPresenter() {
        return new DiscountPresenterImpl();
    }

    @Provides
    public TransactionPresenter provideTransactionPresenter() {
        return new TransactionPresenterImpl();
    }

    @Provides
    public ContactUsPresenter provideContactUsPresenter() {
        return new ContactUsPresenterImpl();
    }

}
