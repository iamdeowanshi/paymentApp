package com.tecsol.batua.user.module.onboard.presenter;

import com.tecsol.batua.user.module.base.ViewInteractor;

/**
 * @author Arnold Laishram.
 */
public interface ImageUploadViewInteractor extends ViewInteractor {

    void showProfileUploadingProgress();

    void hideProfileUploadingProgress();

    void onProfileImageUploadSuccess(String merchantImage);

}
