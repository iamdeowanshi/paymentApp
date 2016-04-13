package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.data.model.Merchant.Gallery;
import com.batua.android.merchant.module.base.ViewInteractor;

/**
 * @author Arnold Laishram.
 */
public interface ImageUploadViewInteractor extends ViewInteractor {

    void showUploadingProgress();

    void hideUploadingProgress();

    void onMerchantImageUploadSuccess(String merchantImage);

    void onProfileImageUploadSuccess(String merchantImage);

}
