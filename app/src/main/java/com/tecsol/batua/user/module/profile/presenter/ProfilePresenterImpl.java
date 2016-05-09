package com.tecsol.batua.user.module.profile.presenter;

import android.accounts.NetworkErrorException;

import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.ApiErrorResponse;
import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.data.model.User.CustomResponse;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginViewInteractor;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Aaditya Deowanshi.
 */
public class ProfilePresenterImpl extends BaseNetworkPresenter<ProfileViewInteractor> implements ProfilePresenter {

    @Inject BatuaUserService api;
    @Inject ApiErrorParser errorParser;

    public ProfilePresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void updateProfile(User user) {
        getViewInteractor().onNetworkCallProgress();

        Observable<Response<User>> observable = api.updateProfile(user);

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
                    getViewInteractor().onProfileUpdated(response.body());
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
