package com.batua.android.merchant.module.dashboard.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.merchant.data.model.MerchantStatusModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by febinp on 01/03/16.
 */
public class ActiveMerchantFragment extends MerchantFragment{

    @Bind(com.batua.android.merchant.R.id.merchant_active_status_recycler_view) RecyclerView merchantActiveRecyclerView;

    private View view;
    private List<MerchantStatusModel> merchantStatusModelList = new ArrayList<MerchantStatusModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_active, null);

        onViewCreated(view, null);
        populateAdapter();

        return view;
    }

    @Override
    RecyclerView getMerchantRecyclerView() {
        return merchantActiveRecyclerView;
    }

    @Override
    List<MerchantStatusModel> getMerchantList() {
        merchantStatusModelList.add(new MerchantStatusModel("Pizza Hut", "JP Nagar, Bangalore", "PZ1", "Active"));
        merchantStatusModelList.add(new MerchantStatusModel("Pizza Hut", "Kormanagala, Bangalore", "PZ2", "Active"));
        merchantStatusModelList.add(new MerchantStatusModel("Pizza Hut", "Jayanagar, Bangalore", "PZ3", "Active"));

        return merchantStatusModelList;
    }

}
