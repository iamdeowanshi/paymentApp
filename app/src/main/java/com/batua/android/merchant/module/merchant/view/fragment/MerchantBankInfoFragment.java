package com.batua.android.merchant.module.merchant.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.merchant.util.SpinnerLoad;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantBankInfoFragment extends BaseFragment {

    private static int BANK_INFO_POSITION = 2;

    @Bind(com.batua.android.merchant.R.id.spinner_bank) Spinner spinnerBank;

    private View view;
    private PreviousClickedListener previousClickedListener;

    @OnClick(com.batua.android.merchant.R.id.txt_load_previous)
    public void onPreviousClicked(){
        previousClickedListener.previousClicked(BANK_INFO_POSITION);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_merchant_bank_info, null);

        onViewCreated(view, null);

        SpinnerLoad.loadSpinner(getContext(), com.batua.android.merchant.R.array.merchant_bank, spinnerBank);

        return view;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case com.batua.android.merchant.R.id.action_save:
                startActivity(MerchantDetailsActivity.class,null);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener){
        this.previousClickedListener = previousClickedListener;
    }

}
