package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.ChangePinPresenter;
import com.tecsol.batua.user.module.onboard.presenter.ChangePinViewInteractor;
import com.tecsol.batua.user.module.profile.view.activity.ProfileActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class ChangePinActivity extends BaseActivity implements ChangePinViewInteractor {

    @Inject Bakery bakery;
    @Inject ChangePinPresenter changePinPresenter;
    @Inject PreferenceUtil preferenceUtil;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_merchant_current_pin) EditText edtCurrentPin;
    @Bind(R.id.edt_merchant_new_pin) EditText edtNewPin;
    @Bind(R.id.edt_merchant_confirm_pin) EditText edtConfirmPin;
    @Bind(R.id.change_pin_progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        Injector.component().inject(this);
        changePinPresenter.attachViewInteractor(this);

        setToolBar();
    }

    @OnClick(R.id.btn_update_pin)
    void updatePin(){
        viewUtil.hideKeyboard(this);

        if (edtCurrentPin.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "Current PIN cannot be empty");
            return;
        }

        if (edtNewPin.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "New PIN cannot be empty");
            return;
        }

        if (edtNewPin.getText().toString().length()!=4){
            bakery.snackShort(getContentView(), "New PIN must be 4 digit");
            return;
        }

        if (!edtNewPin.getText().toString().isEmpty() && edtConfirmPin.getText().toString().isEmpty()) {
            bakery.snackShort(getContentView(), "Confirm PIN cannot be empty");
            return;
        }

        if (isPinValid()) {
            Pin pin = new Pin();
            pin.setCurrentPin(Integer.parseInt(edtCurrentPin.getText().toString()));
            pin.setNewPin(Integer.parseInt(edtNewPin.getText().toString()));
            pin.setUserId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
            pin.setIsPinActivated(true);
            changePinPresenter.changePin(pin);

            return;
        }

        bakery.snackShort(getContentView(), "PIN doesn't match");
    }

    private boolean isPinValid() {
        return edtNewPin.getText().toString().equals(edtConfirmPin.getText().toString());
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
    public void onChangePinSuccess() {
        startActivity(ProfileActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        viewUtil.hideKeyboard(this);
        progressBar.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }
}
