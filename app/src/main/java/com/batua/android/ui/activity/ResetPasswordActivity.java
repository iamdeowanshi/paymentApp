package com.batua.android.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.util.Bakery;

import javax.inject.Inject;

import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @Inject Bakery bakery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        injectDependencies();
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmClick() {
        startActivity(LoginActivity.class,null);
    }
}
