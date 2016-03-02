package com.batua.android.ui.activity;

import android.os.Bundle;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.util.Bakery;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginActivity extends BaseActivity {

    @Inject Bakery bakery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        injectDependencies();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {
        startActivity(HomeActivity.class,null);
    }

    @OnClick(R.id.btn_gplus)
    void onGPlusLogin() {
        startActivity(HomeActivity.class, null);
    }

    @OnClick(R.id.txt_forgot_password)
    void onForgotPasswordClick() {
        startActivity(ForgotPasswordActivity.class, null);
        finish();
    }
}
