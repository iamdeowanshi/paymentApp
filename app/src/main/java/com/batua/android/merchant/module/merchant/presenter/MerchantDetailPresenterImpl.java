package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Arnold Laishram.
 */
public class MerchantDetailPresenterImpl extends BaseNetworkPresenter<MerchantDetailViewInteractor> implements MerchantDetailPresenter{

    @Inject BatuaMerchantService api;

    public MerchantDetailPresenterImpl() {
        Injector.component().inject(this);
    }
}
