package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.City;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class CityPresenterImpl extends BaseNetworkPresenter<CityViewInteractor> implements CityPresenter {

    @Inject BatuaMerchantService api;

    public CityPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getCities() {
        Observable<Response<List<City>>> observable = api.getCities("");

        subscribeForNetwork(observable, new ApiObserver<Response<List<City>>>() {
            @Override
            public void onError(Throwable e) {
                Timber.d(e.toString());
            }

            @Override
            public void onResponse(Response<List<City>> response) {
                if (response.code() != 200) {
                    Timber.d("error " + response.code());
                    return;
                }

                getViewInteractor().showCities(response.body());
            }
        });

    }
}
