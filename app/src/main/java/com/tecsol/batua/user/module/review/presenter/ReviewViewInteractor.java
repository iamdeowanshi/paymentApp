package com.tecsol.batua.user.module.review.presenter;

import com.tecsol.batua.user.data.model.Merchant.Review;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.module.base.NetworkViewInteractor;

/**
 * @author Aaditya Deowanshi.
 */
public interface ReviewViewInteractor extends NetworkViewInteractor {

    void onReviewSuccess(Review review);

}
