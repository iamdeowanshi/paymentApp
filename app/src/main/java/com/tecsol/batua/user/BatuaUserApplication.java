package com.tecsol.batua.user;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.injection.module.ApplicationModule;
import com.tecsol.batua.user.module.base.BaseActivity;

import timber.log.Timber;

/**
 * @author Farhan Ali
 */
public class BatuaUserApplication extends Application {

    private BaseActivity currentActivity = null;

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

    public BaseActivity getCurrentActivity(){
        return currentActivity;
    }
    public void setCurrentActivity(BaseActivity mCurrentActivity){
        this.currentActivity = mCurrentActivity;
    }

}
