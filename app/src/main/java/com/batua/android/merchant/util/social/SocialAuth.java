package com.batua.android.merchant.util.social;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.batua.android.merchant.app.di.Injector;
import com.batua.android.merchant.util.Bakery;
import com.batua.android.merchant.util.PermissionUtil;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author Aaditya Deowanshi
 *
 *         Socail Authentication class.
 */

public class SocialAuth implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback  {

    public enum SocialType {
        GOOGLE,
        FACEBOOK
    }

    private static final int RC_SIGN_IN = 0;
    private static final int REQ_SIGN_IN_REQUIRED = 55664;
    private final static int ACCOUNT_REQUEST_CODE = 0;

    @Inject PermissionUtil permissionUtil;
    @Inject Bakery bakery;

    private boolean isResolving = false;
    private boolean shouldResolve = false;
    private GoogleApiClient googleApiClient;

    private static String[] ACCOUNT_PERMISSION = {Manifest.permission.GET_ACCOUNTS};

    private Activity activity;
    private SocialType socialType;
    private SocialAuthCallback callback;

    public SocialAuth(Activity activity) {
        this.activity = activity;
        initGoogleClient();
        Injector.instance().inject(this);
    }

    public View getContentView() {
        return activity.findViewById(android.R.id.content);
    }

    /**
     * Checking for Account Permission, applicable from Android 6 and above.
     */
    private void checkAccount() {
        if (ActivityCompat.checkSelfPermission(activity, ACCOUNT_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED) {
            requestAccountPermission();

            return;
        }

        googleLogin();
    }

    /**
     * Requesting for runtime permission, applicable from Android 6 and above.
     */
    private void requestAccountPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, ACCOUNT_PERMISSION[0])) {
            bakery.snack(getContentView(), "Contact permission are required for Login", Snackbar.LENGTH_INDEFINITE, "Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, ACCOUNT_PERMISSION, ACCOUNT_REQUEST_CODE);
                }
            });

            return;
        }

        ActivityCompat.requestPermissions(activity, ACCOUNT_PERMISSION, ACCOUNT_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionUtil.verifyPermissions(grantResults)) {
            googleLogin();

            return;
        }

        bakery.snackShort(getContentView(), "Permissions were not granted");
    }

    // Google Api callbacks
    @Override
    public void onConnected(Bundle bundle) {
        shouldResolve = false;
        try {
            new RetrieveTokenTask(Plus.PeopleApi.getCurrentPerson(googleApiClient).toString(), Plus.AccountApi.getAccountName(googleApiClient))
                    .execute(Plus.AccountApi.getAccountName(googleApiClient));
        } catch (NullPointerException e) {
            callback.onError(e);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (isResolving && shouldResolve) return;

        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, RC_SIGN_IN);
                isResolving = true;
            } catch (IntentSender.SendIntentException e) {
                isResolving = false;
                callback.onError(e);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                shouldResolve = false;
            }

            isResolving = false;
            googleApiClient.connect();
        }

        if (requestCode == REQ_SIGN_IN_REQUIRED && resultCode == Activity.RESULT_OK) {
            // Retrieving access token after sign in.
            new RetrieveTokenTask(Plus.PeopleApi.getCurrentPerson(googleApiClient).toString(), Plus.AccountApi.getAccountName(googleApiClient))
                    .execute(Plus.AccountApi.getAccountName(googleApiClient));
        }
    }

    /**
     * Initializing Google.
     */
    private void initGoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }

    public void setCallback(SocialAuthCallback callback) {
        this.callback = callback;
    }

    /**
     * Login to Facebook or google.
     *
     * @param type
     */
    public void login(SocialType type) {
        this.socialType = type;
        switch (socialType) {
            case GOOGLE:
                //Checking for Account permissions on runtime.
                checkAccount();
                break;
            case FACEBOOK:
                break;
        }
    }

    private void googleLogin() {
        shouldResolve = true;
        googleApiClient.connect();
    }

    /**
     * Disconnect from facebook or google.
     */
    public void disconnect() {
        if (socialType == SocialType.GOOGLE) {
            googleApiClient.disconnect();
            Timber.d("google logout");
        }
    }

    /**
     * Retrieving access token from google.
     */
    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {

        String jsonData;
        String email;

        public RetrieveTokenTask(String jsonData, String email) {
            this.jsonData = jsonData;
            this.email = email;
        }

        @Override
        protected String doInBackground(String... params) {
            String accountName = params[0];
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(activity, accountName, scopes);
            } catch (IOException e) {
                callback.onError(e);
            } catch (UserRecoverableAuthException e) {
                activity.startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                callback.onError(e);
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null) {
                AuthResult result = new AuthResult(jsonData, SocialType.GOOGLE);
                result.getAuthUser().setAccessToken(token);
                result.getAuthUser().setEmail(email);
                callback.onSuccess(result);
            }
        }
    }

}
