package com.batua.android.merchant.injection.module;


import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenter;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.CityPresenter;
import com.batua.android.merchant.module.merchant.presenter.CityPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.MerchantCategoryPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantMerchantCategoryPresenterImpl;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Provides all presenter class dependencies.
 *
 * @author Farhan Ali
 */
@Module
public class PresenterModule {

    @Provides
    public MerchantPresenter provideMerchantPresenter() {
        return new MerchantPresenterImpl();
    }

    @Provides
    public MerchantListPresenter provideMerchantListPresenter() {
        return new MerchantListPresenterImpl();
    }

    @Provides
    public MerchantCategoryPresenter provideCategoryPresenter() {
        return new MerchantMerchantCategoryPresenterImpl();
    }

    @Provides
    public CityPresenter provideCityProvide() {
        return new CityPresenterImpl();
    }

}
