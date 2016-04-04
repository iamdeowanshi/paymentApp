package com.batua.android.merchant;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * @author Farhan Ali
 */
public class  BatuaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create module to make it ready for the injection
        Injector.createApplicationComponent(new ApplicationModule(this));

        // Plant Timber tree for Logging
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
