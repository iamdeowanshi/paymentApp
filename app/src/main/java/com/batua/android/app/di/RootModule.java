package com.batua.android.app.di;

import android.content.Context;

import com.batua.android.app.BatuaApplication;
import com.batua.android.ui.activity.ForgotPasswordActivity;
import com.batua.android.ui.activity.LoginActivity;
import com.batua.android.ui.activity.OtpActivity;
import com.batua.android.ui.activity.ResetPasswordActivity;
import com.batua.android.util.Bakery;
import com.batua.android.util.ConnectivityUtil;
import com.batua.android.util.PreferenceUtil;

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
                BatuaApplication.class,

                // View specific classes - activities, fragments, adapters etc
                LoginActivity.class,
                ForgotPasswordActivity.class,
                OtpActivity.class,
                ResetPasswordActivity.class,

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
