package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.NetworkViewInteractor;
import com.batua.android.merchant.module.base.ViewInteractor;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantViewInteractor extends ViewInteractor{

    void merchantAdded(Merchant response);

    void merchantUpdated(Merchant response);

}
