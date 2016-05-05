package com.tecsol.batua.user.module.profile.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class PinStatusPresenterImpl extends BaseNetworkPresenter<PinStatusViewInteractor> implements PinStatusPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public PinStatusPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getPinStatus(int userId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.getPinStatus(userId);

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
                    getViewInteractor().onPinStatusRecieved(response.body());
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
    public void updatePinStatus(Pin pin) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.updatePinStatus(pin);

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
                    getViewInteractor().onPinStatusChanged(response.body());
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
