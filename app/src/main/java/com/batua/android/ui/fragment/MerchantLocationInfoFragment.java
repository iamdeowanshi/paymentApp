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
public class MerchantLocationInfoFragment extends BaseFragment{

    @Bind(R.id.spinner_city) Spinner spinnerCity;

    private View view;

    public MerchantLocationInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_merchant_location_info, null);

        onViewCreated(view,null);

        LoadSpinner.loadSpinner(getContext(), R.array.merchant_city, spinnerCity);

        return view;
    }

}
