package com.batua.android.merchant.module.profile.presenter;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class ProfilePresenterImpl extends BaseNetworkPresenter<ProfileViewInteractor> implements ProfilePresenter {

    @Inject BatuaMerchantService api;

    public ProfilePresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getProfile(int id) {
        getViewInteractor().onNetworkCallProgress();
        Observable<Response<User>> observable = api.getProfile(id, "");

        subscribeForNetwork(observable, new ApiObserver<Response<User>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallProgress();
            }

            @Override
            public void onResponse(Response<User> response) {
                getViewInteractor().onNetworkCallCompleted();
                if (response.code() != 200) {
                    Timber.d("error " + response.code());
                    return;
                }

                getViewInteractor().showProfile(response.body());
            }
        });
    }

    @Override
    public void updateProfile(User user) {
        getViewInteractor().onNetworkCallProgress();
        Observable<Response<User>> observable = api.updateProfile(3, user, "");

        subscribeForNetwork(observable, new ApiObserver<Response<User>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallProgress();
            }

            @Override
            public void onResponse(Response<User> response) {
                getViewInteractor().onNetworkCallCompleted();

                if (response.code() == 400) {
                    getViewInteractor().incorrectPassword();
                    return;
                }

                if (response.code() != 200) {
                    Timber.d("error " + response.code());
                    return;
                }

                getViewInteractor().showProfile(response.body());
            }
        });
    }
}
