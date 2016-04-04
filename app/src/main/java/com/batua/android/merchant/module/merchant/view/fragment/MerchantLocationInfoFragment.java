package com.batua.android.merchant.module.merchant.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;
import com.batua.android.merchant.module.merchant.view.activity.MerchantDetailsActivity;

import butterknife.OnClick;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantLocationInfoFragment extends BaseFragment {

    private static int LOCATION_INFO_POSITION = 1;

    private View view;
    private NextClickedListener nextClickedListener;
    private PreviousClickedListener previousClickedListener;

    @OnClick(com.batua.android.merchant.R.id.txt_load_next)
    public void onNextClicked(){
        nextClickedListener.nextClicked(LOCATION_INFO_POSITION);
    }

    @OnClick(com.batua.android.merchant.R.id.txt_load_previous)
    public void onPreviousClicked(){
        previousClickedListener.previousClicked(LOCATION_INFO_POSITION);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_merchant_location_info, null);

        onViewCreated(view, null);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case com.batua.android.merchant.R.id.action_save:
                startActivity(MerchantDetailsActivity.class,null);
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setNextClickedListener(NextClickedListener nextClickedListener){
        this.nextClickedListener = nextClickedListener;
    }

    public void setPreviousClickedListener(PreviousClickedListener previousClickedListener){
        this.previousClickedListener = previousClickedListener;
    }

}
