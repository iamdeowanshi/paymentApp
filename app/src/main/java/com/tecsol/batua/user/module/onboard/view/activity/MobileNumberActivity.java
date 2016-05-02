package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;


import com.tecsol.batua.user.module.base.BaseActivity;

import butterknife.OnClick;

public class MobileNumberActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_forgot_password);
    }

    @OnClick(com.batua.android.user.R.id.btn_send_otp)
    void onSendClick() {
        startActivity(OtpActivity.class, null);
    }

}
