package com.batua.android.app.di;

import com.batua.android.util.Bakery;
import com.batua.android.util.ConnectivityUtil;
import com.batua.android.util.DisplayUtil;
import com.batua.android.util.PreferenceUtil;

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
    public DisplayUtil provideDisplayUtil() {
        return new DisplayUtil();
    }

}
