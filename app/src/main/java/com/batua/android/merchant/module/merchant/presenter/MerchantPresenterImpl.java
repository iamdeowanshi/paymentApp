package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class MerchantPresenterImpl extends BaseNetworkPresenter<MerchantViewInteractor> implements MerchantPresenter{

    @Inject BatuaMerchantService api;

    public MerchantPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void addMerchant(MerchantRequest request) {
        getViewInteractor().onNetworkCallProgress();
        Observable<Response<Merchant>> observable = api.addMerchant(request, "");

        subscribeForNetwork(observable, new ApiObserver<Response<Merchant>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
                Timber.d(e.toString());
            }

            @Override
            public void onResponse(Response<Merchant> response) {
                if (response.code() != 201) {
                    getViewInteractor().onNetworkCallCompleted();
                    getViewInteractor().showError();
                    Timber.d("error " + response.code());
                    return;
                }
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().showMerchant(response.body());
            }
        });
    }

    @Override
    public void updateMerchant(MerchantRequest request) {
        getViewInteractor().onNetworkCallProgress();
        Observable<Response<Merchant>> observable = api.updateMerchant(request, "");

        subscribeForNetwork(observable, new ApiObserver<Response<Merchant>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().onNetworkCallError(e);
            }

            @Override
            public void onResponse(Response<Merchant> response) {
                if (response.code() != 200) {
                    getViewInteractor().onNetworkCallCompleted();
                    getViewInteractor().showError();
                    Timber.d("error " + response.code());
                    return;
                }

                getViewInteractor().onNetworkCallCompleted();
                getViewInteractor().showMerchant(response.body());
            }
        });
    }

}
