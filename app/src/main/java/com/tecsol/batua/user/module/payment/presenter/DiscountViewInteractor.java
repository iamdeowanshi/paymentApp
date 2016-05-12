package com.tecsol.batua.user.module.payment.presenter;

import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Arnold Laishram
 */
public interface DiscountViewInteractor extends NetworkViewInteractor {

    void onValidPromocode(Discount discount);

    void onOfferExist(Discount discount);

}
