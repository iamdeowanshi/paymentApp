package com.tecsol.batua.user.injection.module;

import com.tecsol.batua.user.module.dashboard.presenter.HomePresenter;
import com.tecsol.batua.user.module.dashboard.presenter.HomePresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ImageUploadPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.SavePinPresenter;
import com.tecsol.batua.user.module.onboard.presenter.SavePinPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpPresenterImpl;
import com.tecsol.batua.user.module.profile.presenter.ProfilePresenter;
import com.tecsol.batua.user.module.profile.presenter.ProfilePresenterImpl;

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
    public SavePinPresenter provideSavePresenter() {
        return new SavePinPresenterImpl();
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

}
