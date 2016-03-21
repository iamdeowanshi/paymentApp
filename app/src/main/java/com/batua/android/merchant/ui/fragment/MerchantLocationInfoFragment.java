package com.batua.android.merchant.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.batua.android.merchant.R;
import com.batua.android.merchant.app.base.BaseFragment;
import com.batua.android.merchant.listener.NextClickedListener;
import com.batua.android.merchant.listener.PreviousClickedListener;
import com.batua.android.merchant.ui.custom.LoadSpinner;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantLocationInfoFragment extends BaseFragment{

    private static int LOCATION_INFO_POSITION = 1;

    @Bind(R.id.spinner_city) Spinner spinnerCity;

    private View view;
    private NextClickedListener nextClickedListener;
    private PreviousClickedListener previousClickedListener;

    public MerchantLocationInfoFragment() {

    }

    @OnClick(R.id.txt_load_next)
    public void onNextClicked(){
        nextClickedListener.nextClicked(LOCATION_INFO_POSITION);
    }

    @OnClick(R.id.txt_load_previous)
    public void onPreviousClicked(){
        previousClickedListener.previousClicked(LOCATION_INFO_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_merchant_location_info, null);

        onViewCreated(view,null);

        LoadSpinner.loadSpinner(getContext(), R.array.merchant_city, spinnerCity);

        return view;
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener){
        this.nextClickedListener = nextClickedListener;
    }

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener){
        this.previousClickedListener = previousClickedListener;
    }

}
