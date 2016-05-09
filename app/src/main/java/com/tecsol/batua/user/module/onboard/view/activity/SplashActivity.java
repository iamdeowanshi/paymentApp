package com.tecsol.batua.user.module.onboard.view.activity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.batua.android.user.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;

import java.io.IOException;

import javax.inject.Inject;


/**
 * @author Aaditya Deowanshi.
 */
public class SplashActivity extends BaseActivity {

    private final static int TIME_OUT = 2000;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static boolean splashLoaded = false;

    @Inject PreferenceUtil preferenceUtil;

    private User user;
    private GoogleCloudMessaging gcm;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Injector.component().inject(this);
        if (!splashLoaded) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    proceedToLogin();
                    splashLoaded = true;
                }
            }, TIME_OUT);
        } else {
            proceedToLogin();
        }

        if (!(InternetUtil.hasInternetConnection(getApplicationContext()))) {
            showNoInternetTitleDialog(this);
            return;
        }

        if (preferenceUtil.readString(preferenceUtil.DEVICE_ID, "") == null || preferenceUtil.readString(preferenceUtil.DEVICE_ID, "").isEmpty()){
            if (checkStatusGooglePlayService()) {
                Log.v("GCM PlayServices", " " + String.valueOf(checkStatusGooglePlayService()));
                getRegId_AndSave();
            }
        }
    }

    private void proceedToLogin() {
        user = (User)preferenceUtil.read(preferenceUtil.USER, User.class);
        if (user!=null && preferenceUtil.readBoolean(preferenceUtil.IS_LOGGED_IN, false)) {
            if (user.isPinSet() && user.isPinActivated()) {
                startActivity(PinLoginActivity.class, null);
                finish();
                return;
            }
            startActivity(HomeActivity.class, null);
            finish();
            return;
        }
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private boolean checkStatusGooglePlayService() {

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) {
            dialog = GooglePlayServicesUtil.getErrorDialog(status, this, PLAY_SERVICES_RESOLUTION_REQUEST);
            // Google Play Services are not available
            dialog.show();
            return false;
        }
        // Google Play Services are available
        return true;
    }

    public void getRegId_AndSave() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String id = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    id = gcm.register(getResources().getString(R.string.gcm_projectnumber));

                } catch (IOException ex) {
                    id = "Error : " + ex.getMessage();
                }
                return id;
            }

            @Override
            protected void onPostExecute(final String RegId) {
                Log.i("GCM onPostExecute", RegId);
                preferenceUtil.save(preferenceUtil.DEVICE_ID, RegId);
            }
        }.execute(null, null, null);
    }

}
