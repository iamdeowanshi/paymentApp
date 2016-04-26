package com.batua.android.merchant.module.onboard.presenter;

import android.accounts.NetworkErrorException;

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
public class ResetPasswordPresenterImpl extends BaseNetworkPresenter<ResetPasswordViewInteractor> implements ResetPasswordPresenter {

    @Inject BatuaMerchantService api;

    public ResetPasswordPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void resetPassword(String password, int userId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<String>> observable = api.resetPassword(password, userId);

        subscribeForNetwork(observable, new ApiObserver<Response<String>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<String> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException("Error : " + response.code()));
                    return;
                }

                getViewInteractor().onPasswordReset();
            }
        });

    }
}
