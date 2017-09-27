package com.batua.android.merchant.module.onboard.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.batua.android.merchant.R;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.onboard.presenter.ResetPasswordPresenter;
import com.batua.android.merchant.module.onboard.presenter.ResetPasswordViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordViewInteractor {

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject ResetPasswordPresenter resetPasswordPresenter;

    @Bind(R.id.input_password) EditText edtPassword;
    @Bind(R.id.input_confirm_password) EditText edtConfirmPassword;
    @Bind(R.id.input_layout_confirm_password) TextInputLayout inputLayoutConfirm;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Injector.component().inject(this);
        resetPasswordPresenter.attachViewInteractor(this);

        userId = getIntent().getIntExtra("UserId", 0);
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmClick() {
        viewUtil.hideKeyboard(this);
        resetPasswordPresenter.resetPassword(edtPassword.getText().toString(), userId);
    }

    @OnTextChanged(R.id.input_confirm_password)
    void onConfirmPasswordChange(CharSequence text) {
        if ( isPasswordValid(text.toString())) {
            inputLayoutConfirm.setErrorEnabled(false);
            return;
        }

        inputLayoutConfirm.setErrorEnabled(true);
        inputLayoutConfirm.setError("Password do not match");
    }

    private boolean isPasswordValid(String password) {
       return password.equals(edtConfirmPassword.getText().toString());
    }

    @Override
    public void onPasswordReset() {
        startActivityClearTop(LoginActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {

    }

    @Override
    public void onNetworkCallCompleted() {

    }

    @Override
    public void onNetworkCallError(Throwable e) {
        bakery.snackShort(getContentView(), "Network Error !");
    }
}
