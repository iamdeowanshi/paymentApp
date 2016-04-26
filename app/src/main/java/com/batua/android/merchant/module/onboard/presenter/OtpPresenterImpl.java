package com.batua.android.merchant.module.onboard.presenter;

import android.accounts.NetworkErrorException;

import com.batua.android.merchant.data.api.ApiErrorParser;
import com.batua.android.merchant.data.api.ApiErrorResponse;
import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class OtpPresenterImpl extends BaseNetworkPresenter<OtpViewInteractor> implements OtpPresenter {

    @Inject BatuaMerchantService api;
    @Inject ApiErrorParser errorParser;

    public OtpPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void sendOtp(String mobileNo) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<String>> observable = api.sendOtp(mobileNo);

        subscribeForNetwork(observable, new ApiObserver<Response<String>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<String> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onOtpSent();
                    return;
                }

                ApiErrorResponse errorResponse = errorParser.parse(response.errorBody());

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException(errorResponse.errors.get(0).message));
                    return;
                }
            }
        });
    }

}
