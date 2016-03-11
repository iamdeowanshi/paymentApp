package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.util.Bakery;
import com.batua.android.user.util.ViewUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @Inject Bakery bakery;

    @Bind(R.id.input_password) EditText edtPassword;
    @Bind(R.id.input_confirm_password) EditText edtConfirmPassword;
    @Bind(R.id.input_layout_confirm_password) TextInputLayout inputLayoutConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        injectDependencies();
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmClick() {
        if ( isPasswordValid()) {
            ViewUtil.hideKeyboard(getContentView());
            bakery.snackShort(getContentView(), "Your Password has been reset.");
            inputLayoutConfirm.setErrorEnabled(false);

            return;
        }

        inputLayoutConfirm.setErrorEnabled(true);
        inputLayoutConfirm.setError("Password do not match");
    }

    private boolean isPasswordValid() {
       return edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString());
    }

}
