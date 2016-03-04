package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;
import com.batua.android.ui.custom.LoadSpinner;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBankInfoFragment extends BaseFragment {

    @Bind(R.id.spinner_bank) Spinner spinnerBank;

    private View view;

    public MerchantBankInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_merchant_bank_info, null);

        onViewCreated(view, null);

        LoadSpinner.loadSpinner(getContext(), R.array.merchant_bank, spinnerBank);

        return view;
    }

}
