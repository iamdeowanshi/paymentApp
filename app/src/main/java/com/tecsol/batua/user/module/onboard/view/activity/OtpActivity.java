package com.tecsol.batua.user.module.onboard.view.activity;

import android.app.ProgressDialog;
import android.content.IntentFilter;
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
import com.tecsol.batua.user.module.onboard.presenter.OtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.OtpViewInteractor;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpPresenter;
import com.tecsol.batua.user.module.onboard.presenter.VerifyOtpViewIteractor;
import com.tecsol.batua.user.module.onboard.service.IncomingSmsListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class OtpActivity extends BaseActivity implements VerifyOtpViewIteractor, OtpViewInteractor, IncomingSmsListener.OnSmsReceivedListener {

    @Inject Bakery bakery;

    @Inject OtpPresenter otpPresenter;
    @Inject VerifyOtpPresenter verifyOtpPresenter;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.edt_otp) EditText edtOtp;
    @Bind(R.id.progressBar) ProgressBar progress;

    private long mobileNo;
    private IncomingSmsListener incomingSmsListener = new IncomingSmsListener();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Injector.component().inject(this);
        otpPresenter.attachViewInteractor(this);
        verifyOtpPresenter.attachViewInteractor(this);

        mobileNo = getIntent().getLongExtra("Mobile", 0l);

        startSmsListener();
        showReadingOtpProgress();
    }

    @OnClick(R.id.txt_otp)
    void onResendClick() {
        Otp otp = new Otp();
        otp.setPhone(mobileNo);
        otp.setUserId(((User)preferenceUtil.read(preferenceUtil.USER, User.class)).getId());
        otpPresenter.sendSignUpOtp(otp);
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        Otp otp = new Otp();
        try {
            otp.setOtp(Long.parseLong(edtOtp.getText().toString()));
        }catch (NumberFormatException e){
            bakery.snackShort(getContentView(), "Invalid OTP");
            return;
        }
        otp.setPhone(mobileNo);
        verifyOtpPresenter.verifySignUpOtp(otp);
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


    // overidden method of VerifyOtpViewIteractor
    @Override
    public void onVerificationSuccess() {
        startActivity(OnBoardActivity.class, null);
        finish();
    }

    @Override
    public void onVerificationFailure() {

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
        progress.setVisibility(View.GONE);
        bakery.snackShort(getContentView(), e.getMessage());
    }

    // overidden method of OtpViewInteractor
    @Override
    public void onOtpSent() {
        bakery.snackShort(getContentView(), "Otp has been successfully");
    }

    // overidden method of OnSmsReceivedListener
    @Override
    public void onSmsReceived(String sender, String message) {
        hideReadingOtp();
        edtOtp.setText(message.replaceAll("[^0-9]", ""));
    }

    private void startSmsListener() {
        incomingSmsListener.setOnSmsReceivedListener(this);
    }

    private void showReadingOtpProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reading OTP" + "\n" + "Please wait...");
        progressDialog.show();
    }

    private void hideReadingOtp(){
        if (progressDialog!=null) {
            progressDialog.dismiss();
        }
    }

}
