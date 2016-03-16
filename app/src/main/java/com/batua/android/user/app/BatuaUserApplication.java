package com.batua.android.user.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.batua.android.user.app.di.Injector;
import com.batua.android.user.app.di.RootModule;

import timber.log.Timber;

/**
 * @author Farhan Ali
 */
public class BatuaUserApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        // Create module to make it ready for the injection
        Injector.instance().createModule(new RootModule(this));

        // Plant Timber tree for Logging
        Timber.plant(new Timber.DebugTree());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
