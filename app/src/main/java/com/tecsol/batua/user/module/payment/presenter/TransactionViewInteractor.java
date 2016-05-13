package com.tecsol.batua.user.module.payment.presenter;

import com.tecsol.batua.user.data.model.Merchant.Discount;
import com.tecsol.batua.user.data.model.User.Transaction;
import com.tecsol.batua.user.data.model.User.TransactionResponse;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Arnold Laishram
 */
public interface TransactionViewInteractor extends NetworkViewInteractor {

    void onTransactionSuccess(TransactionResponse transactionResponse);

}
