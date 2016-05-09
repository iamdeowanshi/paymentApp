package com.tecsol.batua.user.module.onboard.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.Config;
import com.tecsol.batua.user.data.model.User.Pin;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;
import com.tecsol.batua.user.module.onboard.presenter.PinLoginPresenter;
import com.tecsol.batua.user.module.onboard.presenter.PinLoginViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PinLoginActivity extends BaseActivity implements PinLoginViewInteractor{

    @Inject PreferenceUtil preferenceUtil;
    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;
    @Inject PinLoginPresenter pinLoginPresenter;

    @Bind(R.id.edt_first_pin) EditText edtFirstPin;
    @Bind(R.id.edt_second_pin) EditText edtSecondPin;
    @Bind(R.id.edt_third_pin) EditText edtThirdPin;
    @Bind(R.id.edt_fourth_pin) EditText edtFourthPin;
    @Bind(R.id.pin_login_progressBar) ProgressBar setPinProgress;

    private String firstPin;
    private String secondPin;
    private String thirdPin;
    private String fourthPin;
    private int pin;
    private User user;

    @OnClick(R.id.btn_enter)
    void login(){
        viewUtil.hideKeyboard(this);
        Pin pin = new Pin();
        pin.setUserId(user.getId());
        pin.setDeviceId(preferenceUtil.readString(preferenceUtil.DEVICE_ID, ""));
        if (getPin() !=0){
            pin.setPin(getPin());
            pinLoginPresenter.loginByPin(pin, user.getAccessToken());
            return;
        }

        bakery.snackShort(getContentView(), "Please enter a valid PIN");
    }

    @OnClick(R.id.txt_forgot_pin)
    void getForgotPin(){
        Config.OTP_REQUEST_ACTIVITY = Config.FORGOT_PIN_ACTIVITY;
        startActivity(MobileNumberActivity.class, null);
    }

    @OnTextChanged(R.id.edt_first_pin)
    void getFirstPin(CharSequence firstPin){
        if (firstPin.toString().length()==1) {
            edtSecondPin.requestFocus();
        }
    }

    @OnTextChanged(R.id.edt_second_pin)
    void getSecondPin(CharSequence secondPin){
        if (secondPin.toString().length()== 1) {
            edtThirdPin.requestFocus();
        }
    }

    @OnTextChanged(R.id.edt_third_pin)
    void getThirdPin(CharSequence thirdPin){
        if (thirdPin.toString().length()== 1) {
            edtFourthPin.requestFocus();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_pin_login);

        Injector.component().inject(this);
        pinLoginPresenter.attachViewInteractor(this);

        user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
    }

    @Override
    public void onPinLoginSuccess(User user) {
        startActivity(HomeActivity.class, null);
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
