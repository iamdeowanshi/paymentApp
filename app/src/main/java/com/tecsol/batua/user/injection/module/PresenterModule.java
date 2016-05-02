package com.tecsol.batua.user.injection.module;

import com.tecsol.batua.user.module.dashboard.presenter.HomePresenter;
import com.tecsol.batua.user.module.dashboard.presenter.HomePresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenterImpl;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenterImpl;

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

}
