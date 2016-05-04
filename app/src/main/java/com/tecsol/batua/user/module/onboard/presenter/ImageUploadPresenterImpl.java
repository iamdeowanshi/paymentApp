package com.tecsol.batua.user.module.onboard.presenter;

import android.util.Log;

import com.tecsol.batua.user.data.api.ApiObserver;
import com.tecsol.batua.user.data.api.BatuaUserService;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseNetworkPresenter;

import java.io.File;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import timber.log.Timber;

/**
 * @author Arnold Laishram.
 */
public class ImageUploadPresenterImpl extends BaseNetworkPresenter<ImageUploadViewInteractor> implements ImageUploadPresenter {

    @Inject BatuaUserService api;

    public ImageUploadPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void uploadImage(File file) {
        // create multipart
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // upload
        getViewInteractor().showProfileUploadingProgress();

        Observable<Response<String>> observable = api.uploadPhoto("",body);

        // on Response
        subscribeForNetwork(observable, new ApiObserver<Response<String>>() {
            @Override
            public void onError(Throwable e) {
                getViewInteractor().hideProfileUploadingProgress();
            }

            @Override
            public void onResponse(Response<String> response) {

                if (response.code() != 200) {
                    Timber.d("error " + response.code());
                    return;
                }
                getViewInteractor().hideProfileUploadingProgress();
                getViewInteractor().onProfileImageUploadSuccess(response.body());

            }
        });

    }

}
