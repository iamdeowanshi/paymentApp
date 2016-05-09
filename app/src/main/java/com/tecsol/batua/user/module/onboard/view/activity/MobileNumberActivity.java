package com.tecsol.batua.user.module.onboard.view.activity;

import android.Manifest;
import android.os.Bundle;
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
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.OtpViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MobileNumberActivity extends BaseActivity implements OtpViewInteractor{

    final String[] SEND_SMS_PERMISSION = {Manifest.permission.SEND_SMS};
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

    @OnClick(R.id.btn_send_otp)
    void onSendClick() {
        viewUtil.hideKeyboard(this);
        Otp otp = new Otp();
        try{
            otp.setPhone(Long.valueOf(edtNumber.getText().toString()));
        }catch (NumberFormatException e) {
            viewUtil.hideKeyboard(this);
            bakery.snackShort(getContentView(), "Please enter a valid mobile Number");
            return;
        }

        sendOtp(otp);
    }

    private void sendOtp(final Otp otp) {
        requestPermission(SEND_SMS_PERMISSION, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String[] grantedPermissions) {
                if (Config.OTP_REQUEST_ACTIVITY == Config.FORGOT_PASSWORD_ACTIVITY ||
                        Config.OTP_REQUEST_ACTIVITY == Config.FORGOT_PIN_ACTIVITY ||
                        Config.OTP_REQUEST_ACTIVITY == Config.PHONE_VERIFICATION_AFTER_LOGIN_ACTIVITY) {
                    otpPresenter.sendForgotPasswordOrPinOtp(otp);
                    return;
                }

                if (Config.OTP_REQUEST_ACTIVITY == Config.PHONE_VERIFICATION_AFTER_SIGNUP_ACTIVITY) {
                    otp.setType("send");
                    otp.setUserId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
                    otpPresenter.sendSignUpOtp(otp);
                    return;
                }
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                bakery.snackShort(getContentView(), "Sending SMS permission is required to continue");
            }

            @Override
            public void onPermissionBlocked(String[] blockedPermissions) {
                bakery.snackShort(getContentView(), "Sending SMS permission is required to continue");
            }
        });
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
        viewUtil.hideKeyboard(this);
        progressBar.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }
}
