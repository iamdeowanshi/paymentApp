package com.tecsol.batua.user.module.review.presenter;

import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface ReviewPresenter extends Presenter<ReviewViewInteractor> {

    void getReviews(int userId, int merchantId);

}
