package com.batua.android.merchant.module.onboard.presenter;

import android.accounts.NetworkErrorException;

import com.batua.android.merchant.data.api.ApiErrorParser;
import com.batua.android.merchant.data.api.ApiErrorResponse;
import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;
import com.batua.android.merchant.module.common.util.PreferenceUtil;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginPresenterImpl extends BaseNetworkPresenter<LoginViewInteractor> implements LoginPresenter {

    @Inject BatuaMerchantService api;
    @Inject PreferenceUtil preferenceUtil;
    @Inject ApiErrorParser errorParser;

    private String deviceId;

    public LoginPresenterImpl() {
        Injector.component().inject(this);
        deviceId = preferenceUtil.readString(preferenceUtil.DEVICE_ID, "");
    }

    @Override
    public void normalLogin(String email, String password) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.normalLogin(email, password, deviceId);

        subscribeForNetwork(observable, new ApiObserver<Response<User>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<User> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onLoginSuccessful(response.body());
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

    @Override
    public void socialLogin(String email, String socialId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.socialLogin(email, socialId, deviceId);

        subscribeForNetwork(observable, new ApiObserver<Response<User>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<User> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onLoginSuccessful(response.body());
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
