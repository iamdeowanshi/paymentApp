package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBankInfoFragment extends BaseFragment {

    public MerchantBankInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_merchant_bank_info, null);
    }
}
