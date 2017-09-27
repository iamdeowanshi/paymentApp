package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.data.model.Merchant.Category;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class MerchantCategoryPresenterImpl extends BaseNetworkPresenter<MerchantCategoryViewInteractor> implements MerchantCategoryPresenter {

    @Inject BatuaMerchantService api;

    public MerchantCategoryPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void getCategory() {
        Observable<Response<List<Category>>> observable = api.getCategories("");

        subscribeForNetwork(observable, new ApiObserver<Response<List<Category>>>() {
            @Override
            public void onError(Throwable e) {
                Timber.d(e.toString());
            }

            @Override
            public void onResponse(Response<List<Category>> response) {
                if (response.code() != 200) {
                    Timber.d("error " + response.code());
                    return;
                }

                getViewInteractor().showCategory(response.body());
            }
        });
    }
}
