package com.tecsol.batua.user.module.onboard.presenter;


import com.tecsol.batua.user.module.base.Presenter;

import java.io.File;


/**
 * @author Arnold Laishram.
 */
public interface ImageUploadPresenter extends Presenter<ImageUploadViewInteractor> {

    void uploadImage(File merchantImage);

}
