package com.batua.android.merchant.app.di;

import android.content.Context;
import android.view.LayoutInflater;

import com.batua.android.merchant.app.BatuaApplication;
import com.batua.android.merchant.ui.activity.AddMerchantActivity;
import com.batua.android.merchant.ui.activity.EditProfileActivity;
import com.batua.android.merchant.ui.activity.ForgotPasswordActivity;
import com.batua.android.merchant.ui.activity.GalleryImagesActivity;
import com.batua.android.merchant.ui.activity.LoginActivity;
import com.batua.android.merchant.ui.activity.OtpActivity;
import com.batua.android.merchant.ui.activity.ResetPasswordActivity;
import com.batua.android.merchant.ui.adapter.GalleryViewPagerAdapter;
import com.batua.android.merchant.ui.fragment.MerchantBankInfoFragment;
import com.batua.android.merchant.ui.fragment.MerchantBasicInfoFragment;
import com.batua.android.merchant.ui.fragment.MerchantLocationInfoFragment;
import com.batua.android.merchant.util.Bakery;
import com.batua.android.merchant.util.ConnectivityUtil;
import com.batua.android.merchant.util.DisplayUtil;
import com.batua.android.merchant.util.ImageUtil;
import com.batua.android.merchant.util.PermissionUtil;
import com.batua.android.merchant.util.PreferenceUtil;
import com.batua.android.merchant.util.ViewUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Include all other modules, provide Context dependency. All activity, fragment, presenter and
 * any classes that are going to use dependency injection should be registered here.
 *
 * @author Farhan Ali
 */
@Module(
        includes = {
                PresenterModule.class,
                ApiModule.class,
                UtilModule.class,
                FragmentModule.class
        },
        injects = {
                BatuaApplication.class,

                // View specific classes - activities, adapters etc
                LoginActivity.class,
                ForgotPasswordActivity.class,
                OtpActivity.class,
                ResetPasswordActivity.class,
                GalleryViewPagerAdapter.class,
                GalleryImagesActivity.class,
                AddMerchantActivity.class,
                EditProfileActivity.class,

                //Fragment
                MerchantBasicInfoFragment.class,
                MerchantLocationInfoFragment.class,
                MerchantBankInfoFragment.class,

                // Presenter implementations

                // Utilities
                PreferenceUtil.class,
                PermissionUtil.class,
                Bakery.class,
                ConnectivityUtil.class,
                DisplayUtil.class,
                ImageUtil.class,
                ViewUtil.class,
        }
)
public class RootModule {

    private Context context;

    public RootModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return context;
    }


    @Provides
    @Singleton
    public LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(context);
    }

}
