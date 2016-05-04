package com.tecsol.batua.user.module.onboard.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.Otp;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.OtpViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MobileNumberActivity extends BaseActivity implements OtpViewInteractor{

    @Inject OtpPresenter otpPresenter;
    @Inject ViewUtil viewUtil;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_number) EditText edtNumber;
    @Bind(R.id.progressBar2) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Injector.component().inject(this);
        otpPresenter.attachViewInteractor(this);
    }

    @OnClick(R.id.btn_send_otp)
    void onSendClick() {
        viewUtil.hideKeyboard(this);
        Otp otp = new Otp();
        try{
            otp.setPhone(Long.valueOf(edtNumber.getText().toString()));

        }catch (NumberFormatException e) {
            bakery.snackShort(getContentView(), "Invalid Number");
        }
        otp.setUserId(((User)preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
        otpPresenter.sendSignUpOtp(otp);
    }

    @Override
    public void onOtpSent() {
        Bundle bundle = new Bundle();
        Long phone = 0l;
        try{
            phone = Long.valueOf(edtNumber.getText().toString());
        }catch (NumberFormatException e) {
            bakery.snackShort(getContentView(), "Invalid Number");
            return;
        }
        if (phone!=0l) {
            bundle.putLong("Mobile", phone);
            startActivity(OtpActivity.class, bundle);
        }
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
        progressBar.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }
}
