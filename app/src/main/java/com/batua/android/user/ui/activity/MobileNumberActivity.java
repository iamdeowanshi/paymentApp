package com.batua.android.user.ui.activity;

import android.os.Bundle;


import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;

import butterknife.OnClick;

public class MobileNumberActivity extends BaseActivity {

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
