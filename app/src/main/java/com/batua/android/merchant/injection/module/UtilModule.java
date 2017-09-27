package com.batua.android.merchant.injection.module;

import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ConnectivityUtil;
import com.batua.android.merchant.module.common.util.DisplayUtil;
import com.batua.android.merchant.module.common.util.ImageUtil;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.common.util.ViewUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides all util class dependencies.
 *
 * @author Farhan Ali
 */
@Module
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
