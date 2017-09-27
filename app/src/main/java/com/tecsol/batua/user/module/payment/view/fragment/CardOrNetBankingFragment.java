package com.tecsol.batua.user.module.payment.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.batua.android.user.R;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.payment.view.activity.PaymentSuccessActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class CardOrNetBankingFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_or_net_banking, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick(R.id.btn_make_payment)
    public void onClick() {
        startActivity(PaymentSuccessActivity.class, null);
    }

}
