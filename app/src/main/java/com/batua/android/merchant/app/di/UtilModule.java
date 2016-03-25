package com.batua.android.merchant.app.di;

import com.batua.android.merchant.util.Bakery;
import com.batua.android.merchant.util.ConnectivityUtil;
import com.batua.android.merchant.util.DisplayUtil;
import com.batua.android.merchant.util.ImageUtil;
import com.batua.android.merchant.util.PermissionUtil;
import com.batua.android.merchant.util.PreferenceUtil;
import com.batua.android.merchant.util.ViewUtil;

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

    @Provides
    @Singleton
    public ImageUtil provideImageUtil() {
        return new ImageUtil();
    }

    @Provides
    @Singleton
    public PermissionUtil providePermissionUtil() {
        return new PermissionUtil();
    }

    @Provides
    @Singleton
    public ViewUtil provideViewUtil() {
        return new ViewUtil();
    }

}
