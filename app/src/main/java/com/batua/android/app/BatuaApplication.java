package com.batua.android.app;

import android.app.Application;

import com.batua.android.app.di.Injector;
import com.batua.android.app.di.RootModule;

import timber.log.Timber;

/**
 * @author Farhan Ali
 */
public class BatuaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Create module to make it ready for the injection
        Injector.instance().createModule(new RootModule(this));

        // Plant Timber tree for Logging
        Timber.plant(new Timber.DebugTree());
    }

}
