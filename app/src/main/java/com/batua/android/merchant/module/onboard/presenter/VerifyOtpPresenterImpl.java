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
public class VerifyOtpPresenterImpl extends BaseNetworkPresenter<VerifyOtpViewIteractor> implements VerifyOtpPresenter {

    @Inject BatuaMerchantService api;

    public VerifyOtpPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void verifyOtp(String mobile, String otp) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<Integer>> observable = api.verifyOtp(mobile, otp, "");

        subscribeForNetwork(observable, new ApiObserver<Response<Integer>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<Integer> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() == 401) {
                    getViewInteractor().onVerificationFailure();
                    return;
                }

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException("Error : " + response.code()));
                    return;
                }

                getViewInteractor().onVerificationSuccess(response.body());
            }
        });
    }
}
