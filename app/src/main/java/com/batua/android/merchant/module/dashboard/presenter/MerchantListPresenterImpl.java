package com.batua.android.merchant.module.dashboard.presenter;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;
import com.batua.android.merchant.module.common.util.PreferenceUtil;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class MerchantListPresenterImpl extends BaseNetworkPresenter<MerchantListViewInteractor> implements MerchantListPresenter {

    @Inject BatuaMerchantService api;
    @Inject PreferenceUtil preferenceUtil;

    public MerchantListPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getMerchant(String header) {
        getViewInteractor().showProgress();

        User user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);

        Observable<Response<List<Merchant>>> observable = api.getMerchants(user.getId(), user.getAccessToken());

        subscribeForNetwork(observable, new ApiObserver<Response<List<Merchant>>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().hideProgress();
                Timber.d(e.toString());
            }

            @Override
            public void onResponse(Response<List<Merchant>> response) {
                if (response.code() != 200) {
                    getViewInteractor().hideProgress();
                    return;
                }

                getViewInteractor().hideProgress();
                getViewInteractor().merchantList(response.body());
            }
        });
    }
}
