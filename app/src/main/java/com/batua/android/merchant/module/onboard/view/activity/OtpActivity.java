package com.batua.android.merchant.module.onboard.view.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.merchant.R;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.onboard.presenter.OtpPresenter;
import com.batua.android.merchant.module.onboard.presenter.OtpViewInteractor;
import com.batua.android.merchant.module.onboard.presenter.VerifyOtpPresenter;
import com.batua.android.merchant.module.onboard.presenter.VerifyOtpViewIteractor;
import com.batua.android.merchant.module.onboard.service.IncomingSmsListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class OtpActivity extends BaseActivity implements OtpViewInteractor, VerifyOtpViewIteractor, IncomingSmsListener.OnSmsReceivedListener{

    @Inject Bakery bakery;
    @Inject OtpPresenter otpPresenter;
    @Inject VerifyOtpPresenter verifyOtpPresenter;

    @Bind(R.id.edt_otp) EditText edtOtp;
    @Bind(R.id.progress) ProgressBar progress;

    private String mobileNo;
    private IncomingSmsListener incomingSmsListener = new IncomingSmsListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        ButterKnife.bind(this);
        Injector.component().inject(this);
        otpPresenter.attachViewInteractor(this);
        verifyOtpPresenter.attachViewInteractor(this);
        mobileNo = getIntent().getStringExtra("Mobile");

        startSmsListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(incomingSmsListener, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            this.unregisterReceiver(incomingSmsListener);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            this.unregisterReceiver(incomingSmsListener);
        } catch (IllegalArgumentException e) {
//            If receiver has already been disabled
        }
    }

    @Override
    public void onSmsReceived(String sender, String message) {
        bakery.toastLong(message);
    }

    @Override
    public void onOtpSent() {
        bakery.snackShort(getContentView(), "Otp has bees successfully");
    }

    @Override
    public void onNetworkCallProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        bakery.snackShort(getContentView(), e.getMessage());
    }

    @Override
    public void onVerificationSuccess(String id) {
        Bundle bundle = new Bundle();
        bundle.putInt("UserId", Integer.parseInt(id));
        startActivity(ResetPasswordActivity.class, bundle);
        finish();
    }

    @Override
    public void onVerificationFailure() {
        bakery.snackShort(getContentView(), "Otp do not match, re-enter otp");
    }

    @OnClick(R.id.txt_resend_otp)
    void onResendClick() {
        otpPresenter.sendOtp(mobileNo);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        verifyOtpPresenter.verifyOtp(mobileNo, edtOtp.getText().toString());
    }

    private void startSmsListener() {
        incomingSmsListener.setOnSmsReceivedListener(this);
    }

}
