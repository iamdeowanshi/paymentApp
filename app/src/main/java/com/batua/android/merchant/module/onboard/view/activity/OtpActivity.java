package com.batua.android.merchant.module.onboard.view.activity;

import android.os.Bundle;

import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;

import javax.inject.Inject;

import butterknife.OnClick;

public class OtpActivity extends BaseActivity {

    @Inject Bakery bakery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_otp);
        Injector.component().inject(this);
    }

    @OnClick(com.batua.android.merchant.R.id.txt_resend_otp)
    void onResendClick() {
        bakery.snackShort(getContentView(), "Otp sent");
    }

    @OnClick(com.batua.android.merchant.R.id.btn_submit)
    void onSubmitClick() {
        startActivity(ResetPasswordActivity.class, null);
        finish();
    }

}
