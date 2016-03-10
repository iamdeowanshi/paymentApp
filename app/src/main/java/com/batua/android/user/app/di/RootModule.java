package com.batua.android.user.app.di;

import android.content.Context;

import com.batua.android.user.app.BatuaUserApplication;
import com.batua.android.user.presenter.concrete.SamplePresenterImpl;
import com.batua.android.user.ui.activity.SampleActivity;
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
                SampleActivity.class,

                // Presenter implementations
                SamplePresenterImpl.class,

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
