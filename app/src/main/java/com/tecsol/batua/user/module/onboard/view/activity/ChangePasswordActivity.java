package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.ChangePassword;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.ChangePasswordPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ChangePasswordViewInteractor;
import com.tecsol.batua.user.module.onboard.presenter.ChangePinPresenter;
import com.tecsol.batua.user.module.profile.view.activity.ProfileActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class ChangePasswordActivity extends BaseActivity implements ChangePasswordViewInteractor{

    @Inject Bakery bakery;
    @Inject ChangePasswordPresenter changePasswordPresenter;
    @Inject PreferenceUtil preferenceUtil;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_merchant_current_password) EditText edtCurrentPassword;
    @Bind(R.id.edt_merchant_new_password) EditText edtNewPassword;
    @Bind(R.id.edt_merchant_confirm_password) EditText edtConfirmPassword;
    @Bind(R.id.change_password_progressBar) ProgressBar changePasswordProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Injector.component().inject(this);
        changePasswordPresenter.attachViewInteractor(this);

        setToolBar();
    }

    @OnClick(R.id.btn_update_password)
    void updatePassword() {
        viewUtil.hideKeyboard(this);
        if (!edtCurrentPassword.getText().toString().isEmpty()) {
            if (edtNewPassword.getText().toString().isEmpty()) {
                bakery.snackShort(getContentView(), "New Password cannot be empty");
                return;
            }

            if (!edtNewPassword.getText().toString().isEmpty() && edtConfirmPassword.getText().toString().isEmpty()) {
                bakery.snackShort(getContentView(), "Confirm Password cannot be empty");
                return;
            }
        }

        if (isPasswordValid()) {
            ChangePassword changePassword = new ChangePassword();
            changePassword.setCurrentPassword(edtCurrentPassword.getText().toString());
            changePassword.setNewPassword(edtNewPassword.getText().toString());
            changePassword.setUserId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getId());

            changePasswordPresenter.changePassword(changePassword);

            return;
        }

        bakery.snackShort(getContentView(), "Password doesn't match");
    }

    private boolean isPasswordValid() {
        return edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString());
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onChangePasswordSuccess() {
        startActivity(ProfileActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        changePasswordProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        changePasswordProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        changePasswordProgress.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }

}
