package com.batua.android.ui.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;

/**
 * @author Aaditya Deowanshi.
 */
public class SplashActivity extends BaseActivity {

    private static final int timeOut = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        proceedToLogin();
    }

    public void proceedToLogin() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class, null);
                finish();
            }
        }, timeOut);
    }

}
