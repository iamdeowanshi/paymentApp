package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;
import com.batua.android.listener.PreviousClickedListener;
import com.batua.android.ui.custom.LoadSpinner;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBankInfoFragment extends BaseFragment {

    private static int BANK_INFO_POSITION = 2;

    @Bind(R.id.spinner_bank) Spinner spinnerBank;

    private View view;
    private PreviousClickedListener previousClickedListener;

    public MerchantBankInfoFragment() {

    }

    @OnClick(R.id.txt_load_previous)
    public void onPreviousClicked(){
        previousClickedListener.previousClicked(BANK_INFO_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_merchant_bank_info, null);

        onViewCreated(view, null);

        LoadSpinner.loadSpinner(getContext(), R.array.merchant_bank, spinnerBank);

        return view;
    }

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener){
        this.previousClickedListener = previousClickedListener;
    }

}
