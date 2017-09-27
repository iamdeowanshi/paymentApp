package com.tecsol.batua.user.module.payment.presenter;

import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Arnold Laishram
 */
public interface DiscountPresenter extends Presenter<DiscountViewInteractor> {

    void validatePromocode(Discount discount);

    void OfferExist(Integer merchantId);

}
