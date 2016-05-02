package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;

import com.batua.android.user.R;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;

import javax.inject.Inject;

import butterknife.OnClick;

public class OtpActivity extends BaseActivity {

    @Inject
    Bakery bakery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Injector.component().inject(this);
    }

    @OnClick(R.id.txt_resend_otp)
    void onResendClick() {
        bakery.snackShort(getContentView(), "Otp sent");
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        startActivity(ResetPasswordActivity.class, null);
        finish();
    }

}
