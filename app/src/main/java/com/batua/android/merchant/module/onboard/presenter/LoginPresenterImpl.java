package com.batua.android.merchant.module.onboard.presenter;

import android.accounts.NetworkErrorException;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginPresenterImpl extends BaseNetworkPresenter<LoginViewInteractor> implements LoginPresenter {

    @Inject BatuaMerchantService api;

    public LoginPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void normalLogin(String email, String password) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.normalLogin(email, password);

        subscribeForNetwork(observable, new ApiObserver<Response<User>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<User> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException("Error on network: " + response.code()));
                }

                getViewInteractor().onLoginSuccessful(response.body());
            }
        });
    }

    @Override
    public void socialLogin(String email, String accessToken, String socialId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.socialLogin(email, socialId, accessToken);

        subscribeForNetwork(observable, new ApiObserver<Response<User>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<User> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallError(new NetworkErrorException("Error on network: " + response.code()));
                }

                getViewInteractor().onLoginSuccessful(response.body());
            }
        });
    }
}
