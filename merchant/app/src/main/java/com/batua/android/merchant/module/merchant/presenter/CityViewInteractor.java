package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.City;
import com.batua.android.merchant.module.base.ViewInteractor;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public interface CityViewInteractor extends ViewInteractor {

    void showCities(List<City> cities);
}
