package com.tecsol.batua.user.module.payment.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.Merchant.PromoCode;
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
    public void validatePromocode(PromoCode promoCode) {

        getViewInteractor().onNetworkCallProgress();

        Observable<Response<List<PromoCode>>> observable = api.validatePromocode(promoCode);

        subscribeForNetwork(observable, new ApiObserver<Response<List<PromoCode>>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<List<PromoCode>> response) {
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
}
