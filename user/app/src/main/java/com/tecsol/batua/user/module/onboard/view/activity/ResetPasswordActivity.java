package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.ChangePassword;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.ResetPasswordPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ResetPasswordViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity implements ResetPasswordViewInteractor{

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject PreferenceUtil preferenceUtil;
    @Inject ResetPasswordPresenter resetPasswordPresenter;

    @Bind(R.id.edt_password) EditText edtPassword;
    @Bind(R.id.input_confirm_password) EditText edtConfirmPassword;
    @Bind(R.id.input_layout_confirm_password) TextInputLayout inputLayoutConfirm;
    @Bind(R.id.reset_password_progressBar) ProgressBar resetProgress;

    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Injector.component().inject(this);
        resetPasswordPresenter.attachViewInteractor(this);

        userId = getIntent().getExtras().getInt("UserId");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(com.batua.android.user.R.id.btn_confirm)
    void onConfirmClick() {
        viewUtil.hideKeyboard(this);

        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }

        if (edtPassword.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "New Password cannot be empty");
            return;
        }

        if (edtPassword.getText().toString().length() < 6) {
            bakery.snackShort(getContentView(), "New Password must be minimum of 6 characters");
            return;
        }

        ChangePassword changePassword =  new ChangePassword();
        changePassword.setUserId(userId);

        if (isPasswordValid()) {
            changePassword.setPassword(edtPassword.getText().toString());
            resetPasswordPresenter.resetPassword(changePassword);
            return;
        }

        bakery.snackShort(getContentView(), "Password do not match");
    }

    @OnClick(R.id.input_confirm_password)
    void isConfirmPinValid(){
        if (isPasswordValid()) {
            inputLayoutConfirm.setErrorEnabled(true);
            inputLayoutConfirm.setError("Password do not match");
            return;
        }
        inputLayoutConfirm.setErrorEnabled(false);
    }

    @Override
    public void onPasswordReset(String message) {
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        resetProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        resetProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        viewUtil.hideKeyboard(this);
        resetProgress.setVisibility(View.GONE);

        if (e == null || e.getMessage() == null) {
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        bakery.snackShort(getContentView(), e.getMessage());
    }

    private boolean isPasswordValid() {
        return edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString());
    }

}
