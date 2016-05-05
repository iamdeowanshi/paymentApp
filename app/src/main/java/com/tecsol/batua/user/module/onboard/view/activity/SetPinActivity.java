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
import com.tecsol.batua.user.module.profile.presenter.SetPinPresenter;
import com.tecsol.batua.user.module.profile.presenter.SetPinViewInteractor;
import com.tecsol.batua.user.module.profile.view.activity.ProfileActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class SetPinActivity extends BaseActivity implements SetPinViewInteractor{

    @Inject SetPinPresenter setPinPresenter;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.edt_first_pin) EditText edtFirstPin;
    @Bind(R.id.edt_second_pin) EditText edtSecondPin;
    @Bind(R.id.edt_third_pin) EditText edtThirdPin;
    @Bind(R.id.edt_fourth_pin) EditText edtFourthPin;
    @Bind(R.id.set_pin_progressBar) ProgressBar setPinProgress;

    private String firstPin;
    private String secondPin;
    private String thirdPin;
    private String fourthPin;
    private int pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        Injector.component().inject(this);
        setPinPresenter.attachViewInteractor(this);

        setToolBar();
    }

    @OnClick(R.id.btn_save)
    void onSave(){
        viewUtil.hideKeyboard(this);
        Pin pin = new Pin();
        pin.setUserId(((User) preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
        if (getPin() !=0){
            pin.setPin(getPin());
            setPinPresenter.setPin(pin);
            return;
        }

        bakery.snackShort(getContentView(), "Please enter a valid PIN");
    }

    @Override
    public void onPinSet(String message) {
        startActivity(ProfileActivity.class, null);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        setPinProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        setPinProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        viewUtil.hideKeyboard(this);
        setPinProgress.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
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

    public int getPin() {
        if(isValidPin()){
            try {
                pin = Integer.parseInt((firstPin + secondPin + thirdPin + fourthPin));
            } catch (NumberFormatException e){
                return 0;
            }
            return pin;
        }

        return 0;
    }

    private boolean isValidPin() {
        firstPin = edtFirstPin.getText().toString();
        secondPin = edtSecondPin.getText().toString();
        thirdPin = edtThirdPin.getText().toString();
        fourthPin = edtFourthPin.getText().toString();

        if (firstPin.isEmpty() || secondPin.isEmpty() || thirdPin.isEmpty() || fourthPin.isEmpty()) {
            return false;
        }
        return true;
    }
}
