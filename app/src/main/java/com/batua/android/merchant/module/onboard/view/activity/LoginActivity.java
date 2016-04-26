package com.batua.android.merchant.module.onboard.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.InternetUtil;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.common.util.social.AuthResult;
import com.batua.android.merchant.module.common.util.social.SocialAuth;
import com.batua.android.merchant.module.common.util.social.SocialAuthCallback;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginActivity extends BaseActivity implements SocialAuthCallback, ActivityCompat.OnRequestPermissionsResultCallback{

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(com.batua.android.merchant.R.id.edt_email) EditText edtEmail;
    @Bind(com.batua.android.merchant.R.id.img_logo) ImageView imgLogo;
    @Bind(com.batua.android.merchant.R.id.input_layout_email) TextInputLayout inputLayoutEmail;

    private SocialAuth socialAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_login);
        Injector.component().inject(this);


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
        socialAuth.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(AuthResult result) {
        socialAuth.disconnect();
        //hideProgress();
        startActivity(HomeActivity.class, null);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        viewUtil.hideKeyboard(this);
        boolean isValid = isValidEmail(edtEmail.getText()) || isValidNumber(edtEmail.getText());

        if (!InternetUtil.hasInternetConnection(this)){
            showNoInternetTitleDialog(this);

            return;
        }

        if (isValid) {
            startActivity(HomeActivity.class, null);
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

        startActivity(HomeActivity.class, null);
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
        progressDialog.setMessage("Logging in");
        progressDialog.show();
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }
}
