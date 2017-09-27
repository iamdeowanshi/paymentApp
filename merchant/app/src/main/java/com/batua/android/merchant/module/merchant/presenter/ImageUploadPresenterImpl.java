package com.batua.android.merchant.module.merchant.presenter;

import android.util.Log;

import com.batua.android.merchant.data.api.ApiObserver;
import com.batua.android.merchant.data.api.BatuaMerchantService;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseNetworkPresenter;

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

    private static final int PROFILE_FLAG = 1;

    @Inject BatuaMerchantService api;

    public ImageUploadPresenterImpl() {
        Injector.component().inject(this);
    }

    @Override
    public void uploadImage(File file, final int flag) {
        // create multipart
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // upload
        if (flag == PROFILE_FLAG) {
            getViewInteractor().showProfileUploadingProgress();
        }else {
            getViewInteractor().showUploadingProgress();
        }
        Observable<Response<String>> observable = api.uploadPhoto("",body);

        // on Response
        subscribeForNetwork(observable, new ApiObserver<Response<String>>() {
            @Override
            public void onError(Throwable e) {
                if (flag == PROFILE_FLAG) {
                    getViewInteractor().hideProfileUploadingProgress();
                }else {
                    getViewInteractor().hideUploadingProgress();
                }
                Log.e(".......", e.toString());
            }

            @Override
            public void onResponse(Response<String> response) {

                if (response.code() != 200) {
                    Timber.d("error " + response.code());
                    return;
                }

                if (flag == PROFILE_FLAG) {
                    getViewInteractor().hideProfileUploadingProgress();
                    getViewInteractor().onProfileImageUploadSuccess(response.body());
                    return;
                }else {
                    getViewInteractor().hideUploadingProgress();
                }

                getViewInteractor().onMerchantImageUploadSuccess(response.body());
            }
        });

    }

}
