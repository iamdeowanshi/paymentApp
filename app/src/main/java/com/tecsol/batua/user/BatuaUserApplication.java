package com.tecsol.batua.user;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.injection.module.ApplicationModule;

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
