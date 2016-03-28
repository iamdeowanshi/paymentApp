package com.batua.android.user.app.di;

import com.batua.android.user.util.Bakery;
import com.batua.android.user.util.ConnectivityUtil;
import com.batua.android.user.util.PreferenceUtil;
import com.batua.android.user.util.ViewUtil;
import com.batua.android.user.util.social.SocialAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides all util class dependencies.
 *
 * @author Farhan Ali
 */
@Module(
        complete = false,
        library = true
)
public class UtilModule {

    @Provides
    @Singleton
    public PreferenceUtil providePreferenceUtil() {
        return new PreferenceUtil();
    }

    @Provides
    @Singleton
    public ConnectivityUtil provideConnectivityUtil() {
        return new ConnectivityUtil();
    }

    @Provides
    @Singleton
    public Bakery provideBakery() {
        return new Bakery();
    }

    @Provides
    @Singleton
    public ViewUtil provideViewUtil() {
        return new ViewUtil();
    }

}
