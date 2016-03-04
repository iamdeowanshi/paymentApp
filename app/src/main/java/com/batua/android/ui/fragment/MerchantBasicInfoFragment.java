package com.batua.android.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;
import com.batua.android.listener.NextClickedListener;
import com.batua.android.ui.custom.LoadSpinner;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBasicInfoFragment extends BaseFragment {

    private static int BASIC_INFO_POSITION = 0;
    @Bind(R.id.spinner_merchant_category) Spinner spinnerMerchantCategory;

    private View view;
    private NextClickedListener nextClickedListener;

    public MerchantBasicInfoFragment() {

    }

    @OnClick(R.id.txt_load_next)
    public void loadNextClicked(){
        nextClickedListener.nextClicked(BASIC_INFO_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_merchant_basic_info, null);

        onViewCreated(view, null);

        LoadSpinner.loadSpinner(getContext(), R.array.merchant_category, spinnerMerchantCategory);

        return view;
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener){
        this.nextClickedListener = nextClickedListener;
    }

}
