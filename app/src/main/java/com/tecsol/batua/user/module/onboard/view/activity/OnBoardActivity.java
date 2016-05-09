package com.tecsol.batua.user.module.onboard.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tecsol.batua.user.Config;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.callback.PermissionCallback;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.common.util.social.AuthResult;
import com.tecsol.batua.user.module.common.util.social.SocialAuth;
import com.tecsol.batua.user.module.common.util.social.SocialAuthCallback;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;
import com.tecsol.batua.user.module.onboard.presenter.LoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.LoginViewInteractor;
import com.tecsol.batua.user.module.onboard.presenter.SignUpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.SignUpViewInteractor;
import com.tecsol.batua.user.module.onboard.view.fragment.LoginFragmentPagerAdpater;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnPageChange;

public class OnBoardActivity extends BaseActivity implements SocialAuthCallback, SignUpViewInteractor, LoginViewInteractor {

    private static int ONBOARD_PAGE_NUM = 0;

    @Inject ViewUtil viewUtil;
    @Inject SignUpPresenter signUpPresenter;
    @Inject LoginPresenter loginPresenter;
    @Inject PreferenceUtil preferenceUtil;
    @Inject Bakery bakery;

    @Bind(R.id.home_tab_layout) TabLayout onBoardTabLayout;
    @Bind(R.id.home_viewpager) ViewPager onBoardViewpager;
    @Bind(R.id.img_logo) ImageView imgLogo;
    @Bind(R.id.onboard_progressBar) ProgressBar progressBar;

    private SocialAuth socialAuth;
    private User signUpUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Injector.component().inject(this);

        signUpPresenter.attachViewInteractor(this);
        loginPresenter.attachViewInteractor(this);

        setListeners();
        loadFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListeners();
        loadFragments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        socialAuth.onActivityResult(requestCode, resultCode, data);
    }

    @OnPageChange(R.id.home_viewpager)
    public void onPageSelected(int position){
        ONBOARD_PAGE_NUM = position;
    }

    // overidden methods of SocialAuth
    @Override
    public void onSocialConnectionSuccess(AuthResult result) {

        Log.d("result-email", result.getAuthUser().getEmail());
        String email = result.getAuthUser().getEmail();
        String name = result.getAuthUser().getFirstName() + result.getAuthUser().getLastName();
        String socialId = result.getAuthUser().getSocialId();
        String deviceId = preferenceUtil.readString(preferenceUtil.DEVICE_ID, "");

        socialAuth.disconnect();
        switch (result.getAuthType()) {
            case FACEBOOK_SIGNUP:
                signUpPresenter.socialFacebookSignUp(email, name, socialId);
                break;
            case GOOGLE_SIGNUP:
                signUpPresenter.socialGoogleSignUp(email, name, socialId);
                break;
            case FACEBOOK_LOGIN:
                loginPresenter.socialFacebookLogin(email, deviceId, socialId);
                break;
            case GOOGLE_LOGIN:
                loginPresenter.socialGoogleLogin(email, deviceId, socialId);
                break;
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    // overidden methods of LoginViewInteractor
    @Override
    public void onNormalLoginSuccess(User user) {
        requestPermissionAndLogin(user);
    }

    @Override
    public void onSocialLoginSuccess(User user) {
        requestPermissionAndLogin(user);
    }

    // overidden methods of SignUpViewInteractor
    @Override
    public void onNormalSignUpSuccess(String message) {
        Config.OTP_REQUEST_ACTIVITY = Config.PHONE_VERIFICATION_AFTER_SIGNUP_ACTIVITY;
        Bundle bundle =  new Bundle();
        bundle.putLong("Mobile", signUpUser.getPhone());
        startActivity(OtpActivity.class, bundle);
        finish();
    }

    @Override
    public void onSocialSignUpSuccess(Integer userId) {
        Config.OTP_REQUEST_ACTIVITY = Config.PHONE_VERIFICATION_AFTER_SIGNUP_ACTIVITY;
        User user = new User();
        user.setId(userId);
        preferenceUtil.save(preferenceUtil.USER, user);
        startActivity(MobileNumberActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        viewUtil.hideKeyboard(this);
        progressBar.setVisibility(View.GONE);

        if (e ==  null) {
            return;
        }

        if (e.getMessage() ==  null) {
            return;
        }

        if (e.getMessage().equals("Mobile number not verified")) {
            Config.OTP_REQUEST_ACTIVITY = Config.PHONE_VERIFICATION_AFTER_LOGIN_ACTIVITY;
            startActivity(MobileNumberActivity.class, null);
            finish();
            return;
        }
        bakery.snackShort(getContentView(), e.getMessage());
    }

    private void setListeners() {
        viewUtil.keyboardListener(this, new ViewUtil.KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    imgLogo.setVisibility(View.GONE);

                    return;
                }

                imgLogo.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadFragments() {
        onBoardViewpager.setAdapter(new LoginFragmentPagerAdpater(getSupportFragmentManager(), this));
        onBoardTabLayout.post(new Runnable() {
            @Override
            public void run() {
                onBoardTabLayout.setupWithViewPager(onBoardViewpager);
            }
        });
        onBoardViewpager.setCurrentItem(ONBOARD_PAGE_NUM);
    }

    public void socialSignUp(SocialAuth.SocialType socialType) {

        viewUtil.hideKeyboard(this);
        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);

            return;
        }

        if (socialType == SocialAuth.SocialType.FACEBOOK_SIGNUP) {
            socialAuth = new SocialAuth(this, SocialAuth.SocialType.FACEBOOK_SIGNUP);
            socialAuth.setCallback(this);
            socialAuth.login(SocialAuth.SocialType.FACEBOOK_SIGNUP);
            return;
        }
        socialAuth = new SocialAuth(this, SocialAuth.SocialType.GOOGLE_SIGNUP);
        socialAuth.setCallback(this);
        socialAuth.login(SocialAuth.SocialType.GOOGLE_SIGNUP);
        return;

    }

    public void socialLogin(SocialAuth.SocialType socialType) {

        viewUtil.hideKeyboard(this);
        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);

            return;
        }

        if (socialType == SocialAuth.SocialType.FACEBOOK_LOGIN) {
            socialAuth = new SocialAuth(this, SocialAuth.SocialType.FACEBOOK_LOGIN);
            socialAuth.setCallback(this);
            socialAuth.login(SocialAuth.SocialType.FACEBOOK_LOGIN);
            return;
        }
        socialAuth = new SocialAuth(this, SocialAuth.SocialType.GOOGLE_LOGIN);
        socialAuth.setCallback(this);
        socialAuth.login(SocialAuth.SocialType.GOOGLE_LOGIN);

    }

    public void normalSignUp(User user) {
        signUpUser = user;
        signUpPresenter.normalSignUp(user);
    }

    public void normalLogin(String username, String password, String deviceId) {
        loginPresenter.normalLogin(username, password, deviceId);
    }

    private void requestPermissionAndLogin(final User user) {
        String[] internetPermissions = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        requestPermission(internetPermissions, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String[] grantedPermissions) {
                // permissions are granted
                preferenceUtil.save(preferenceUtil.IS_LOGGED_IN, true);
                preferenceUtil.save(preferenceUtil.USER, user);
                if (user.isPinActivated() && user.isPinSet()) {
                    startActivity(PinLoginActivity.class, null);
                    finish();
                    return;
                }
                startActivity(HomeActivity.class, null);
                finish();
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                // permissions are denied
                bakery.snackShort(getContentView(), "Location permission is required to login");
            }

            @Override
            public void onPermissionBlocked(String[] blockedPermissions) {
                // permissions are denied and user opted not to ask again
                bakery.snackShort(getContentView(), "Location permission is required to login");
            }
        });
    }

}
