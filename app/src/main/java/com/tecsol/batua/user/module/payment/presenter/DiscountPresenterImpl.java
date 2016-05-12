package com.tecsol.batua.user.module.payment.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Arnold Laishram
 */
public class DiscountPresenterImpl extends BaseNetworkPresenter<DiscountViewInteractor> implements DiscountPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public DiscountPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void validatePromocode(Discount discount) {

        getViewInteractor().onNetworkCallProgress();

        Observable<Response<List<Discount>>> observable = api.validatePromocode(discount);

        subscribeForNetwork(observable, new ApiObserver<Response<List<Discount>>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<List<Discount>> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onValidPromocode(response.body().get(0));
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
    public void OfferExist(Integer merchantId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<List<Discount>>> observable = api.offerExist(merchantId);

        subscribeForNetwork(observable, new ApiObserver<Response<List<Discount>>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<List<Discount>> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onOfferExist(response.body().get(0));
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
