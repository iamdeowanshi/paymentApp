package com.batua.android.merchant.module.onboard.view.activity;

import android.os.Bundle;

import com.batua.android.merchant.module.base.BaseActivity;

import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_forgot_password);
    }

    @OnClick(com.batua.android.merchant.R.id.btn_send_otp)
    void onSendClick() {
        startActivity(OtpActivity.class, null);
    }
}
