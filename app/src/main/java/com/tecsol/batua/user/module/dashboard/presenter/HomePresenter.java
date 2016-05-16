package com.tecsol.batua.user.module.dashboard.presenter;

import com.tecsol.batua.user.module.base.Presenter;

/**
 * @author Aaditya Deowanshi.
 */
public interface HomePresenter extends Presenter<HomeViewInteractor> {

    void getMerchants(int id, int merchantId, double latitute, double longitude);

}
