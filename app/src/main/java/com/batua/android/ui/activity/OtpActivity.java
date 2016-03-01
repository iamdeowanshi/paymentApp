package com.batua.android.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.util.Bakery;

import javax.inject.Inject;

import butterknife.OnClick;

public class OtpActivity extends BaseActivity {

    @Inject Bakery bakery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        injectDependencies();
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
