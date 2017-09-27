package com.tecsol.batua.user.module.onboard.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.User.CustomResponse;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Arnold.
 */
public class ChangePinPresenterImpl extends BaseNetworkPresenter<ChangePinViewInteractor> implements ChangePinPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public ChangePinPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void changePin(Pin pin) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<CustomResponse>> observable = api.changePin(pin);

        subscribeForNetwork(observable, new ApiObserver<Response<CustomResponse>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<CustomResponse> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onChangePinSuccess();
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
