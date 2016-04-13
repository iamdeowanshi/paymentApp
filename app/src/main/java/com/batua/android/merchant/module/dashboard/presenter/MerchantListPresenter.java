package com.batua.android.merchant.module.dashboard.presenter;

import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantListPresenter extends Presenter<MerchantListViewInteractor> {

    void getMerchant(String header);

}
