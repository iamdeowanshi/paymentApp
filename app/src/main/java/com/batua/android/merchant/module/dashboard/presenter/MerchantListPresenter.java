package com.batua.android.merchant.module.dashboard.presenter;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;
import com.batua.android.merchant.module.base.Presenter;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantListPresenter extends Presenter<MerchantListViewInteractor> {

    void getMerchant(String header);

}
