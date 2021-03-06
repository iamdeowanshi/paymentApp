package com.tecsol.batua.user.module.common.util.social;

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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PermissionUtil;

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

public class SocialAuth implements GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    public enum SocialType {
        GOOGLE_LOGIN,
        GOOGLE_SIGNUP,
        FACEBOOK_LOGIN,
        FACEBOOK_SIGNUP,
    }

    private static final int RC_SIGN_IN = 0;
    private static final int RC_SIGN_UP = 1;
    private static int REQUEST_CODE;
    private static final int REQ_SIGN_IN_REQUIRED = 55664;
    private static final int REQ_LOG_IN_REQUIRED = 55665;
    private final static int ACCOUNT_REQUEST_CODE = 2;

    @Inject PermissionUtil permissionUtil;
    @Inject Bakery bakery;

    private boolean isResolving = false;
    private boolean shouldResolve = false;
    private GoogleSignInOptions gso;
    private ProgressDialog progressDialog;
    private GoogleApiClient googleApiClient;
    private CallbackManager fbCallbackManager;

    private static String[] ACCOUNT_PERMISSION = {Manifest.permission.GET_ACCOUNTS};

    private Activity activity;
    private SocialType socialType;
    private SocialAuthCallback callback;

    public SocialAuth(Activity activity, SocialType type) {
        this.activity = activity;
        this.socialType = type;
        initGoogleClient();
        initFacebookClient();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionUtil.verifyPermissions(grantResults)) {
            googleLogin();

            return;
        }

        bakery.snackShort(getContentView(), "Permissions were not granted");
    }

/*    private void googleLogin() {
        shouldResolve = true;
        googleApiClient.connect();
    }*/

/*    // Google Api callbacks
    @Override
    public void onConnected(Bundle bundle) {
        shouldResolve = false;
        try {
            if (googleApiClient == null) {
                initGoogleClient();
            }
            new RetrieveTokenTask(Plus.PeopleApi.getCurrentPerson(googleApiClient).toString(), Plus.AccountApi.getAccountName(googleApiClient))
                    .execute(Plus.AccountApi.getAccountName(googleApiClient));
        } catch (NullPointerException e) {
            callback.onError(e);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}*/

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (isResolving && shouldResolve) return;

        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(activity, REQUEST_CODE);
                isResolving = true;
            } catch (IntentSender.SendIntentException e) {
                isResolving = false;
                callback.onError(e);
                onDestroy();
            }
        }
    }

    public void onDestroy() {
        if (googleApiClient!=null) {
            googleApiClient.stopAutoManage((FragmentActivity) this.activity);
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        this.activity = activity;
        if (requestCode == REQUEST_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        if (fbCallbackManager.onActivityResult(requestCode, resultCode, data)) return;
    }

/*    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                shouldResolve = false;
            }

            isResolving = false;
            googleApiClient.connect();
        }

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (googleApiClient == null) {
                initGoogleClient();
            }

            // Retrieving access token after sign in.
            if (!googleApiClient.isConnected()) {
                googleApiClient.connect();
                return;
            }
            new RetrieveTokenTask(Plus.PeopleApi.getCurrentPerson(googleApiClient).toString(), Plus.AccountApi.getAccountName(googleApiClient))
                    .execute(Plus.AccountApi.getAccountName(googleApiClient));
        }

        if (fbCallbackManager.onActivityResult(requestCode, resultCode, data)) return;
    }*/

    private void handleSignInResult(GoogleSignInResult result) {
        hideProgress();
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            AuthResult authResult = new AuthResult(acct, socialType);
            callback.onSocialConnectionSuccess(authResult);
            onDestroy();
        }
        onDestroy();
    }

    /**
     * Initializing Google.
     */
    private void initGoogleClient() {

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
        activity.startActivityForResult(signInIntent, REQUEST_CODE);
    }

    /**
     * Initializing Google.
     */
   /* private void initGoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }*/

    /**
     * Initializing Facebook.
     */
    private void initFacebookClient() {
        FacebookSdk.sdkInitialize(activity);
        fbCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location, picture");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String token = loginResult.getAccessToken().getToken();
                        AuthResult result = new AuthResult(object.toString(), SocialAuth.this.socialType);
                        result.getAuthUser().setAccessToken(token);
                        callback.onSocialConnectionSuccess(result);
                    }
                });

                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                callback.onCancel();
            }

            @Override
            public void onError(FacebookException exception) {
                callback.onError(exception);
            }
        });
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
            case GOOGLE_LOGIN:
                REQUEST_CODE = RC_SIGN_IN;
                checkAccount();
                break;
            case GOOGLE_SIGNUP:
                REQUEST_CODE = RC_SIGN_UP;
                checkAccount();
                break;
            case FACEBOOK_LOGIN:
                REQUEST_CODE = REQ_LOG_IN_REQUIRED;
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends", "email"));
                break;
            case FACEBOOK_SIGNUP:
                REQUEST_CODE = REQ_SIGN_IN_REQUIRED;
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "user_friends", "email"));
                break;
        }
    }

    private void googleLogin() {
        shouldResolve = true;
        //googleApiClient.connect();
        signIn();
        showProgress();
    }

/*    *//**
     * Disconnect from facebook or google.
     *//*
    public void disconnect() {
        if (socialType == SocialType.GOOGLE) {
            shouldResolve = false;
            isResolving = false;
            googleApiClient.disconnect();
            Timber.d("google logout");
        }
    }*/

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Timber.d("Logged out");
                    }
                });
    }

    /**
     * Disconnect from facebook or google.
     */
    //TODO: facebook logout only & for google call signout method
    public void disconnect() {
        if (socialType == (SocialType.GOOGLE_LOGIN)) {
            signOut();
        } else if (socialType == SocialType.FACEBOOK_LOGIN) {
            LoginManager.getInstance().logOut();
            Timber.d("facebook logout");
        }if (socialType == (SocialType.GOOGLE_SIGNUP)) {
            signOut();
        } else if (socialType == SocialType.FACEBOOK_SIGNUP) {
            LoginManager.getInstance().logOut();
            Timber.d("facebook logout");
        }
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


   /* *//**
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
                callback.onError(e);
            } catch (UserRecoverableAuthException e) {
                activity.startActivityForResult(e.getIntent(), REQUEST_CODE);
            } catch (GoogleAuthException e) {
                callback.onError(e);
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null) {
                AuthResult result = new AuthResult(jsonData, socialType);
                result.getAuthUser().setAccessToken(token);
                result.getAuthUser().setEmail(email);
                callback.onSocialConnectionSuccess(result);
            }
        }
    }
*/
}
