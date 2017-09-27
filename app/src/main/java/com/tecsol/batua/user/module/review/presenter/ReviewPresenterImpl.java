package com.tecsol.batua.user.module.review.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.Merchant.Review;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class ReviewPresenterImpl extends BaseNetworkPresenter<ReviewViewInteractor> implements ReviewPresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public ReviewPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getReviews(int userId, int merchantId) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<Review>> observable = api.getReview(userId, merchantId);

        subscribeForNetwork(observable, new ApiObserver<Response<Review>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<Review> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.isSuccessful()) {
                    getViewInteractor().onReviewSuccess(response.body());
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
