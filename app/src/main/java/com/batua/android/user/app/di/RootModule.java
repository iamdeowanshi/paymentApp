package com.batua.android.user.app.di;

import android.content.Context;

import com.batua.android.user.app.BatuaUserApplication;
import com.batua.android.user.ui.activity.LoginActivity;
import com.batua.android.user.ui.activity.MobileNumberActivity;
import com.batua.android.user.ui.activity.OtpActivity;
import com.batua.android.user.ui.activity.ResetPasswordActivity;
import com.batua.android.user.ui.fragment.LoginFragment;
import com.batua.android.user.ui.fragment.SignUpFragment;
import com.batua.android.user.util.Bakery;
import com.batua.android.user.util.ConnectivityUtil;
import com.batua.android.user.util.PreferenceUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Include all other modules, provide Context dependency. All activity, fragment, presenter and
 * any classes that are going to use dependency injection should be registered here.
 *
 * @author Farhan Ali
 */
@Module(
        includes = {
                PresenterModule.class,
                ApiModule.class
        },
        injects = {
                BatuaUserApplication.class,

                // View specific classes - activities, fragments, adapters etc
                MobileNumberActivity.class,
                LoginFragment.class,
                SignUpFragment.class,
                ResetPasswordActivity.class,
                OtpActivity.class,
                LoginActivity.class,
                // Presenter implementations

                // Utilities
                PreferenceUtil.class,
                Bakery.class,
                ConnectivityUtil.class,
        }
)
public class RootModule {

    private Context context;

    public RootModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return context;
    }

}
