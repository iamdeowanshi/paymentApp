package com.tecsol.batua.user.module.dashboard.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.User.ContactUs;
import com.tecsol.batua.user.data.model.User.CustomResponse;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class ContactUsPresenterImpl extends BaseNetworkPresenter<ContactUsViewInteractor> implements ContactUsPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public ContactUsPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void contactBatua(ContactUs contactUs) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<CustomResponse>> observable = api.contactBatua(contactUs);

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
                    getViewInteractor().onEmailSent(response.body().getMessage());
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
