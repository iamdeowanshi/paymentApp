package com.batua.android.merchant.injection;

import com.batua.android.merchant.injection.component.ApplicationComponent;
import com.batua.android.merchant.injection.component.DaggerApplicationComponent;
import com.batua.android.merchant.injection.module.ApiModule;
import com.batua.android.merchant.injection.module.ApplicationModule;
import com.batua.android.merchant.injection.module.PresenterModule;
import com.batua.android.merchant.injection.module.UtilModule;

/**
 * A singleton class, which is responsible for holding dagger ObjectGraph, adding modules to it
 * & injecting dependencies.
 *
 * @author Farhan Ali
 */
public class Injector {

    // Singleton instance
    private static Injector instance;

    // components
    private ApplicationComponent applicationComponent;

    // Private constructor to make it singleton
    private Injector() {
    }

    /**
     * Get the singleton Injector instance
     *
     * @return Injector
     */
    private static Injector instance() {
        if (instance == null) {
            instance = new Injector();
        }
        
        return  instance;
    }

    /**
     * Creates application component which used of injection later.
     *
     * @param applicationModule
     */
    public static void createApplicationComponent(ApplicationModule applicationModule) {
        if (instance().applicationComponent == null) {
            instance().applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(applicationModule)
                    .apiModule(new ApiModule())
                    .presenterModule(new PresenterModule())
                    .utilModule(new UtilModule())
                    .build();
        }
    }

    /**
     * Returns the component for injection.
     *
     * @return
     */
    public static ApplicationComponent component() {
        return instance().applicationComponent;
    }

}
