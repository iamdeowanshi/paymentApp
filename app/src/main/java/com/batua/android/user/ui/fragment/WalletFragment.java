package com.batua.android.user.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseFragment;
import com.batua.android.user.ui.activity.PaymentSuccessActivity;

import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class WalletFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet, null);
    }

    @OnClick(R.id.btn_make_payment)
    public void onClick() {
        startActivity(PaymentSuccessActivity.class, null);
    }

}
