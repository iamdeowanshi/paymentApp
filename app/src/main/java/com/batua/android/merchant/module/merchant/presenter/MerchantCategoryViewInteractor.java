package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.Category;
import com.batua.android.merchant.module.base.ViewInteractor;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
 */
public interface MerchantCategoryViewInteractor extends ViewInteractor {

    void showCategory(List<Category> categories);

}
