package com.batua.android.merchant.app.di;

import com.batua.android.merchant.presenter.SamplePresenter;
import com.batua.android.merchant.presenter.concrete.SamplePresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Provides all presenter class dependencies.
 *
 * @author Farhan Ali
 */
@Module(
        complete = false,
        library = true
)
public class PresenterModule {

    @Provides
    public SamplePresenter provideSamplePresenter() {
        return new SamplePresenterImpl();
    }


}
