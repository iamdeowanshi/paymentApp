package com.batua.android.merchant.module.onboard.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.common.util.InternetUtil;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.common.util.social.AuthResult;
import com.batua.android.merchant.module.common.util.social.SocialAuth;
import com.batua.android.merchant.module.common.util.social.SocialAuthCallback;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;
import com.batua.android.merchant.module.onboard.presenter.LoginPresenter;
import com.batua.android.merchant.module.onboard.presenter.LoginViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import timber.log.Timber;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginActivity extends BaseActivity implements SocialAuthCallback, ActivityCompat.OnRequestPermissionsResultCallback, LoginViewInteractor {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject LoginPresenter loginPresenter;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.img_logo) ImageView imgLogo;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_password) EditText edtPassword;
    @Bind(R.id.btn_login) Button btnLogin;

    private SocialAuth socialAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Injector.component().inject(this);
        loginPresenter.attachViewInteractor(this);

        socialAuth = new SocialAuth(this);
        socialAuth.setCallback(this);
        setListeners();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        socialAuth.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        socialAuth.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(AuthResult result) {
        socialAuth.disconnect();
        loginPresenter.socialLogin(result.getAuthUser().getEmail(), result.getAuthUser().getSocialId());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {
        bakery.toastShort(throwable.getMessage());
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        viewUtil.hideKeyboard(this);

        if (!InternetUtil.hasInternetConnection(this)){
            showNoInternetTitleDialog(this);

            return;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "Please enter your password");
            return;
        }

        boolean isUserIdValid = isValidEmail(edtEmail.getText()) || isValidNumber(edtEmail.getText());

        if (isUserIdValid) {
            loginPresenter.normalLogin(edtEmail.getText().toString(), edtPassword.getText().toString());
            inputLayoutEmail.setErrorEnabled(false);
            return;
        }

        inputLayoutEmail.setErrorEnabled(true);
        inputLayoutEmail.setError("Invalid email or mobile number");
    }

    @OnClick(R.id.btn_gplus)
    void onGPlusLogin() {
        viewUtil.hideKeyboard(this);
        /*socialAuth.login(SocialAuth.SocialType.GOOGLE);*/
        //showProgress();
        if (!InternetUtil.hasInternetConnection(this)){
            showNoInternetTitleDialog(this);

            return;
        }


        socialAuth.login(SocialAuth.SocialType.GOOGLE);
        finish();
    }

    @OnClick(R.id.txt_forgot_password)
    void onForgotPasswordClick() {
        startActivity(ForgotPasswordActivity.class, null);
        finish();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidNumber(CharSequence target) {
        if (target == null || target.length() != 10) {
            return false;
        }

        return Patterns.PHONE.matcher(target).matches();
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

    private void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Signing in");
        progressDialog.show();
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void onLoginSuccessful(User user) {
        preferenceUtil.save(preferenceUtil.USER, user);
        startActivityClearTop(HomeActivity.class, null);
    }

    @Override
    public void onLoginFailed(String message) {
        bakery.snackShort(getContentView(), message);
    }

    @Override
    public void onNetworkCallProgress() {
        showProgress();
    }

    @Override
    public void onNetworkCallCompleted() {
        hideProgress();
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        bakery.snackShort(getContentView(), "Network Error !");
        Timber.d(e.toString());
    }
}
