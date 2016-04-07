package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantPresenter extends Presenter<MerchantViewInteractor> {

    void addMerchant(MerchantRequest request);

    void updateMerchant(MerchantRequest request);

}
