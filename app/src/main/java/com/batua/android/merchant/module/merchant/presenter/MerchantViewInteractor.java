package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.ViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantViewInteractor extends ViewInteractor{

    void showMerchant(Merchant response);

}
