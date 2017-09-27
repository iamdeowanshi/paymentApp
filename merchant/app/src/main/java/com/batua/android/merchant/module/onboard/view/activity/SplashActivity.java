package com.batua.android.merchant.module.onboard.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;

import javax.inject.Inject;


/**
 * @author Aaditya Deowanshi.
 */
public class SplashActivity extends BaseActivity {

    @Inject PreferenceUtil preferenceUtil;

    private static final int TIME_OUT = 2000;
    private String androidId;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_splash);
        Injector.component().inject(this);
        proceedToLogin();

        androidId = preferenceUtil.readString(preferenceUtil.DEVICE_ID, null);

        if (androidId == null) {
            androidId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
            preferenceUtil.save(preferenceUtil.DEVICE_ID, androidId);
        }
    }

    public void proceedToLogin() {
        user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Class<? extends Activity> activityToLaunch = (user == null)
                        ? LoginActivity.class
                        : HomeActivity.class;

                startActivity(activityToLaunch, null);
                finish();
            }
        }, TIME_OUT);
    }

}
