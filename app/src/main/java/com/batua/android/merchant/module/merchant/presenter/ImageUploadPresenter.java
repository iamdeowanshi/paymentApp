package com.batua.android.merchant.module.merchant.presenter;

import com.batua.android.merchant.module.base.Presenter;

import java.io.File;


/**
 * @author Arnold Laishram.
 */
public interface ImageUploadPresenter extends Presenter<ImageUploadViewInteractor> {

    void uploadImage(File merchantImage, int flag);

}
