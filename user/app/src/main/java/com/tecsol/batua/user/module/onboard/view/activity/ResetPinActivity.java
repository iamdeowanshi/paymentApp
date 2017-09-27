package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.InternetUtil;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.profile.presenter.SetPinPresenter;
import com.tecsol.batua.user.module.profile.presenter.SetPinViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class ResetPinActivity extends BaseActivity implements SetPinViewInteractor{

    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject SetPinPresenter setPinPresenter;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_new_pin) EditText edtNewPin;
    @Bind(R.id.edt_confirm_pin) EditText edtConfirmPin;
    @Bind(R.id.reset_pin_progressBar) ProgressBar resetPinProgress;
    @Bind(R.id.input_layout_confirm_password) TextInputLayout inputLayoutConfirm;

    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);
        Injector.component().inject(this);
        setPinPresenter.attachViewInteractor(this);

        userId = getIntent().getExtras().getInt("UserId");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmClick() {

        if (!InternetUtil.hasInternetConnection(this)) {
            showNoInternetTitleDialog(this);
            return;
        }

        viewUtil.hideKeyboard(this);

        if (edtNewPin.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "New PIN cannot be empty");
            return;
        }

        if (edtNewPin.getText().toString().length()!=4){
            bakery.snackShort(getContentView(), "New PIN must be 4 digit");
            return;
        }

        Pin pin = new Pin();
        pin.setUserId(userId);

        if (isPinValid()) {
            pin.setPin(Integer.parseInt(edtNewPin.getText().toString()));
            setPinPresenter.setPin(pin);
            return;
        }

        bakery.snackShort(getContentView(), "PIN do not match");
    }

    @OnClick(R.id.edt_confirm_pin)
    void isConfirmPinValid(){
        if (isPinValid()) {
            inputLayoutConfirm.setErrorEnabled(true);
            inputLayoutConfirm.setError("PIN do not match");
            return;
        }
        inputLayoutConfirm.setErrorEnabled(false);
    }

    @Override
    public void onPinSet(String message) {
        viewUtil.hideKeyboard(this);
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        resetPinProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        resetPinProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        viewUtil.hideKeyboard(this);
        resetPinProgress.setVisibility(View.GONE);

        if (e == null || e.getMessage() == null) {
            return;
        }

        if (e.getMessage().startsWith("failed to connect")) {
            bakery.snackShort(getContentView(), "Server error");
            return;
        }

        bakery.snackShort(getContentView(), e.getMessage());
    }

    private boolean isPinValid() {
        return edtNewPin.getText().toString().equals(edtConfirmPin.getText().toString());
    }

}
