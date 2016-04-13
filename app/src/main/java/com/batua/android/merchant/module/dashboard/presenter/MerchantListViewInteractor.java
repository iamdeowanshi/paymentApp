package com.batua.android.merchant.module.dashboard.presenter;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.ViewInteractor;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantListViewInteractor extends ViewInteractor {

    void showProgress();

    void hideProgress();

    void merchantList(List<Merchant> merchants);

}
