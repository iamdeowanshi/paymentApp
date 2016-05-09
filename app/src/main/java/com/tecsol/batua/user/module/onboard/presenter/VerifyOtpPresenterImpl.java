package com.tecsol.batua.user.module.onboard.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.User.Otp;
import com.tecsol.batua.user.data.model.User.CustomResponse;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class VerifyOtpPresenterImpl extends BaseNetworkPresenter<VerifyOtpViewIteractor> implements VerifyOtpPresenter {

    @Inject BatuaUserService api;

    public VerifyOtpPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void verifySignUpOtp(Otp otp) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<CustomResponse>> observable = api.verifySignUpOtp(otp);

        subscribeForNetwork(observable, new ApiObserver<Response<CustomResponse>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<CustomResponse> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() == 401) {
                    getViewInteractor().onVerificationFailure();
                    return;
                }

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException("Error : " + response.code()));
                    return;
                }
                getViewInteractor().onSignUpOtpVerificationSuccess();
            }
        });
    }

    @Override
    public void verifyForgotPinPasswordOtp(Otp otp) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<CustomResponse>> observable = api.verifyForgotPinPasswordOtp(otp);

        subscribeForNetwork(observable, new ApiObserver<Response<CustomResponse>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<CustomResponse> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() == 401) {
                    getViewInteractor().onVerificationFailure();
                    return;
                }

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException("Error : " + response.code()));
                    return;
                }
                getViewInteractor().onForgotPasswordPinVerificationSuccess(response.body().getUserId());
            }
        });
    }
}
