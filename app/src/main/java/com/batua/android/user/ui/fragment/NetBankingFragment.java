package com.batua.android.user.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseFragment;
import com.batua.android.user.ui.activity.PaymentSuccessActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Aaditya Deowanshi.
 */
public class NetBankingFragment extends BaseFragment {

    @Bind(R.id.spinner_bank) Spinner spinnerBank;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_net_banking, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.merchant_bank, R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_list);
        spinnerBank.setAdapter(adapter);
    }


    @OnClick(R.id.btn_make_payment)
    public void onClick() {
        startActivity(PaymentSuccessActivity.class, null);
    }

}
