package com.batua.android.merchant.module.onboard.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.merchant.R;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.onboard.presenter.OtpPresenter;
import com.batua.android.merchant.module.onboard.presenter.OtpViewInteractor;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements OtpViewInteractor {

    @Inject OtpPresenter presenter;
    @Inject Bakery bakery;
    @Inject ViewUtil viewUtil;

    @Bind(R.id.edt_number) EditText edtNumber;
    @Bind(R.id.progress) ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Injector.component().inject(this);

        presenter.attachViewInteractor(this);
    }

    @OnClick(R.id.btn_send_otp)
    void onSendClick() {
        viewUtil.hideKeyboard(this);
        presenter.sendOtp(edtNumber.getText().toString());
    }

    @Override
    public void onOtpSent() {
        Bundle bundle = new Bundle();
        bundle.putString("Mobile", edtNumber.getText().toString());
        startActivity(OtpActivity.class, null);
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

}
