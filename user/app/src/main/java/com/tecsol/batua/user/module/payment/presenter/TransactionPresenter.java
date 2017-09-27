package com.tecsol.batua.user.module.payment.presenter;

import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.data.model.User.Transaction;
import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Arnold Laishram
 */
public interface TransactionPresenter extends Presenter<TransactionViewInteractor> {

    void makeTransaction(Transaction transaction);

}
