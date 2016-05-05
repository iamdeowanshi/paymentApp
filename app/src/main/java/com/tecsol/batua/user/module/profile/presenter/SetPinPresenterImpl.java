package com.tecsol.batua.user.module.profile.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Arnold.
 */
public class SetPinPresenterImpl extends BaseNetworkPresenter<SetPinViewInteractor> implements SetPinPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public SetPinPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void setPin(Pin pin) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<String>> observable = api.setPin(pin);

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
                    getViewInteractor().onPinSet(response.body());
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
