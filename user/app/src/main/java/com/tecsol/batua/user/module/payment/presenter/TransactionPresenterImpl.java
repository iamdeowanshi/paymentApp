package com.tecsol.batua.user.module.payment.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.data.model.User.Transaction;
import com.tecsol.batua.user.data.model.User.TransactionResponse;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Arnold Laishram
 */
public class TransactionPresenterImpl extends BaseNetworkPresenter<TransactionViewInteractor> implements TransactionPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public TransactionPresenterImpl() {
        Injector.component().inject(this);
    }


    @Override
    public void makeTransaction(Transaction transaction) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<TransactionResponse>> observable = api.makePayment(transaction);

        subscribeForNetwork(observable, new ApiObserver<Response<TransactionResponse>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<TransactionResponse> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onTransactionSuccess(response.body());
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
