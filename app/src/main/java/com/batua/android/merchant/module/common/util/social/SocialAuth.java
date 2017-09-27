package com.batua.android.merchant.module.common.util.social;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.batua.android.merchant.R;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

import java.io.IOException;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author Aaditya Deowanshi
 *
 *         Socail Authentication class.
 */

public class SocialAuth implements GoogleApiClient.OnConnectionFailedListener {

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
    private GoogleSignInOptions gso;
    private ProgressDialog progressDialog;

    private static String[] ACCOUNT_PERMISSION = {Manifest.permission.GET_ACCOUNTS};

    private Activity activity;
    private SocialType socialType;
    private SocialAuthCallback callback;

    public SocialAuth(Activity activity) {
        this.activity = activity;
        initGoogleClient();
        Injector.component().inject(this);
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionUtil.verifyPermissions(grantResults)) {
            googleLogin();

            return;
        }

        bakery.snackShort(getContentView(), "Permissions were not granted");
    }

/*    // Google Api callbacks
    @Override
    public void onConnected(Bundle bundle) {
        shouldResolve = false;
        try {
            signOut();
            *//*new RetrieveTokenTask(Plus.PeopleApi.getCurrentPerson(googleApiClient).toString(), Plus.AccountApi.getAccountName(googleApiClient))
                    .execute(Plus.AccountApi.getAccountName(googleApiClient));*//*
        } catch (NullPointerException e) {
            callback.onError(e);
            hideProgress();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }*/

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
                hideProgress();
            }
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        this.activity = activity;
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
/*        if (requestCode == RC_SIGN_IN) {
            if (resultCode != Activity.RESULT_OK) {
                shouldResolve = false;
            }

            isResolving = false;
            hideProgress();
        }

        if (requestCode == REQ_SIGN_IN_REQUIRED && resultCode == Activity.RESULT_OK) {
            // Retrieving access token after sign in.
            signOut();
            new RetrieveTokenTask(Plus.PeopleApi.getCurrentPerson(googleApiClient).toString(), Plus.AccountApi.getAccountName(googleApiClient))
                    .execute(Plus.AccountApi.getAccountName(googleApiClient));
        }*/
    }

    private void handleSignInResult(GoogleSignInResult result) {
        hideProgress();
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            AuthResult authResult = new AuthResult(acct);
            callback.onSuccess(authResult);
            signOut();
        }
    }

    /**
     * Initializing Google.
     */
    private void initGoogleClient() {
/*        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();*/

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage((FragmentActivity) activity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
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
        //googleApiClient.connect();
        signIn();
        showProgress();
    }

    /**
     * Disconnect from facebook or google.
     */
    public void disconnect() {
        if (socialType == SocialType.GOOGLE) {
            shouldResolve = false;
            isResolving = false;
            googleApiClient.disconnect();
            Timber.d("google logout");
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Timber.d("Logged out");
                    }
                });
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Logging in");
        progressDialog.show();
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

 /*   *//**
     * Retrieving access token from google.
     *//*
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
                hideProgress();
                callback.onError(e);
            } catch (UserRecoverableAuthException e) {
                activity.startActivityForResult(e.getIntent(), REQ_SIGN_IN_REQUIRED);
            } catch (GoogleAuthException e) {
                hideProgress();
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
                hideProgress();
            }
        }
    }*/

}
