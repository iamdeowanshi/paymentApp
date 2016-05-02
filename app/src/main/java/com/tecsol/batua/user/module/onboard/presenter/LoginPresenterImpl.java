package com.tecsol.batua.user.module.onboard.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginPresenterImpl extends BaseNetworkPresenter<LoginViewInteractor> implements LoginPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public LoginPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void normalLogin(String email, String password, String deviceId) {
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
                    getViewInteractor().onNormalLoginSuccess(response.body());
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
    public void socialLogin(String email, String deviceid, String facebookId, String googleId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.socialGoogleLogin(email, deviceid, facebookId, googleId);

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
                    getViewInteractor().onSocialLoginSuccess(response.body());
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
