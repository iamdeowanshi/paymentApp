package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.NetworkViewInteractor;
import com.batua.android.merchant.module.base.ViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantViewInteractor extends NetworkViewInteractor{

    void showMerchant(Merchant response);

    void showError();

}
