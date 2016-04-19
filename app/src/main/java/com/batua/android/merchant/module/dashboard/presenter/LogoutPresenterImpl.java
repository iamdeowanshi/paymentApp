package com.batua.android.merchant.module.dashboard.presenter;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class LogoutPresenterImpl extends BaseNetworkPresenter<LogoutViewInteractor> implements LogoutPresenter {

    @Inject
    BatuaMerchantService api;

    public LogoutPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void logout(String deviceId, int id) {
        Observable<Response<String>> observable = api.logout(deviceId, id);

        subscribeForNetwork(observable, new ApiObserver<Response<String>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onResponse(Response response) {
                if (response.code() != 200) {

                }

                getViewInteractor().onLogout();
            }
        });
    }
}
