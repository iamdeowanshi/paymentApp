package com.batua.android.merchant.ui.activity;

import android.os.Bundle;

import com.batua.android.merchant.R;
import com.batua.android.merchant.app.base.BaseActivity;

import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    @OnClick(R.id.btn_send_otp)
    void onSendClick() {
        startActivity(OtpActivity.class, null);
    }
}
