package com.tecsol.batua.user.injection.module;

import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.ConnectivityUtil;
import com.tecsol.batua.user.module.common.util.ImageUtil;
import com.tecsol.batua.user.module.common.util.PermissionUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;

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
