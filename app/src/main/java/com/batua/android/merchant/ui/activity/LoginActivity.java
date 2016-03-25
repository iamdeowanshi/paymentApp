package com.batua.android.merchant.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.app.base.BaseActivity;
import com.batua.android.merchant.util.Bakery;
import com.batua.android.merchant.util.ViewUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginActivity extends BaseActivity {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.edt_email) EditText edtEmail;
    @Bind(R.id.img_logo) ImageView imgLogo;
    @Bind(R.id.input_layout_email) TextInputLayout inputLayoutEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        injectDependencies();

        setListeners();
    }

    @OnClick(R.id.btn_login)
    void onLoginClick() {

	viewUtil.hideKeyboard(this);
        boolean isValid = isValidEmail(edtEmail.getText()) || isValidNumber(edtEmail.getText());

        if (isValid) {
            startActivity(HomeActivity.class,null);
            inputLayoutEmail.setErrorEnabled(false);

            return;
        }

        inputLayoutEmail.setErrorEnabled(true);
        inputLayoutEmail.setError("Invalid email or mobile number");
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

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        }

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidNumber(CharSequence target) {
        if (target == null || target.length() != 10) {
            return false;
        }

        return Patterns.PHONE.matcher(target).matches();
    }

    private void setListeners() {
        viewUtil.keyboardListener(this, new ViewUtil.KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    imgLogo.setVisibility(View.GONE);

                    return;
                }

                imgLogo.setVisibility(View.VISIBLE);
            }
        });
    }

}
