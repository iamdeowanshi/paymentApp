package com.batua.android.merchant.module.onboard.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ViewUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(com.batua.android.merchant.R.id.input_password) EditText edtPassword;
    @Bind(com.batua.android.merchant.R.id.input_confirm_password) EditText edtConfirmPassword;
    @Bind(com.batua.android.merchant.R.id.input_layout_confirm_password) TextInputLayout inputLayoutConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_reset_password);
        Injector.component().inject(this);
    }

    @OnClick(com.batua.android.merchant.R.id.btn_confirm)
    void onConfirmClick() {
        if ( isPasswordValid()) {
            viewUtil.hideKeyboard(this);
            bakery.snackShort(getContentView(), "Your Password has been reset.");
            inputLayoutConfirm.setErrorEnabled(false);

            startActivity(LoginActivity.class, null);
            finish();

            return;
        }

        inputLayoutConfirm.setErrorEnabled(true);
        inputLayoutConfirm.setError("Password do not match");
    }

    private boolean isPasswordValid() {
       return edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString());
    }

}
