package com.batua.android.merchant.module.onboard.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.batua.android.merchant.module.base.BaseActivity;


/**
 * @author Aaditya Deowanshi.
 */
public class SplashActivity extends BaseActivity {

    private static final int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_splash);
        proceedToLogin();
    }

    public void proceedToLogin() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class, null);
                finish();
            }
        }, TIME_OUT);
    }

}
