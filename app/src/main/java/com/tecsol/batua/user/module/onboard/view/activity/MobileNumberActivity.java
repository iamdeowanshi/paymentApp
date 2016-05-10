package com.tecsol.batua.user.module.onboard.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.batua.android.user.R;
import com.tecsol.batua.user.Config;
import com.tecsol.batua.user.data.model.User.Otp;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.callback.PermissionCallback;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.OtpViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MobileNumberActivity extends BaseActivity implements OtpViewInteractor{

    final String[] READ_SMS_PERMISSION = {Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};

    @Inject OtpPresenter otpPresenter;
    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_number) EditText edtNumber;
    @Bind(R.id.progressBar2) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Injector.component().inject(this);
        otpPresenter.attachViewInteractor(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_send_otp)
    void onSendClick() {

        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }

        viewUtil.hideKeyboard(this);
        Otp otp = new Otp();
        try{
            otp.setPhone(Long.valueOf(edtNumber.getText().toString()));
        }catch (NumberFormatException e) {
            viewUtil.hideKeyboard(this);
            bakery.snackShort(getContentView(), "Please enter a valid mobile Number");
            return;
        }

        if (Config.OTP_REQUEST_ACTIVITY == Config.FORGOT_PASSWORD_ACTIVITY ||
                Config.OTP_REQUEST_ACTIVITY == Config.FORGOT_PIN_ACTIVITY) {
            otpPresenter.sendForgotPasswordOrPinOtp(otp);
            return;
        }

        if (Config.OTP_REQUEST_ACTIVITY == Config.PHONE_VERIFICATION_AFTER_SIGNUP_ACTIVITY) {
            otp.setType("send");
            otp.setUserId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
            otpPresenter.sendSignUpOtp(otp);
            return;
        }

        if (Config.OTP_REQUEST_ACTIVITY == Config.PHONE_VERIFICATION_AFTER_LOGIN_ACTIVITY) {
            otp.setType("send");
            otp.setUserId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getUserId());
            otpPresenter.sendSignUpOtp(otp);
        }
    }

    @Override
    public void onOtpSent() {
        Bundle bundle = new Bundle();
        Long phone = 0l;
        try{
            phone = Long.valueOf(edtNumber.getText().toString());
        }catch (NumberFormatException e) {
            viewUtil.hideKeyboard(this);
            bakery.snackShort(getContentView(), "Invalid Number");
            return;
        }
        if (phone!=0l) {
            bundle.putLong("Mobile", phone);
            verifyOtp(bundle);
        }
    }

    private void verifyOtp(final Bundle bundle) {
        requestPermission(READ_SMS_PERMISSION, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String[] grantedPermissions) {
                startActivity(OtpActivity.class, bundle);
                finish();
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                bakery.snackShort(getContentView(), "Reading SMS permission is required to continue");
            }

            @Override
            public void onPermissionBlocked(String[] blockedPermissions) {
                bakery.snackShort(getContentView(), "Reading SMS permission is required to continue");
            }
        });
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
        if (e == null || e.getMessage() == null) {
            return;
        }

        if (e.getMessage().equals("Already a social user")) {
            showAlreadySocialUserDialog();
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        viewUtil.hideKeyboard(this);
        progressBar.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }

    private void showAlreadySocialUserDialog(){

            AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);

            alertbuilder.setTitle("Alert")
                    .setMessage("This number is associated with a social login. Please login with your Facebook or Google to reset the password.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(OnBoardActivity.class, null);
                            dialog.dismiss();
                            finish();
                        }
                    });
            AlertDialog alert = alertbuilder.create();
            alert.show();
    }
}
