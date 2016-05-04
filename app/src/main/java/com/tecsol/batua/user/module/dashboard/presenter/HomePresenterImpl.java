package com.tecsol.batua.user.module.dashboard.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginViewInteractor;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class HomePresenterImpl extends BaseNetworkPresenter<HomeViewInteractor> implements HomePresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public HomePresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getMerchants(int id, int merchantId, double latitude, double longitude) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<List<Merchant>>> observable = api.getMerchants(id, merchantId, latitude, longitude);

        subscribeForNetwork(observable, new ApiObserver<Response<List<Merchant>>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<List<Merchant>> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onSuccess(response.body());
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
