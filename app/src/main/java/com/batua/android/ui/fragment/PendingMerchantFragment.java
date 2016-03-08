package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;
import com.batua.android.data.model.MerchantStatusModel;
import com.batua.android.ui.custom.PopulateMerchantStatusAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by febinp on 01/03/16.
 */
public class PendingMerchantFragment extends BaseFragment {

    private View view;
    private List<MerchantStatusModel> merchantStatusModelList = new ArrayList<MerchantStatusModel>();

    @Bind(R.id.merchant_pending_status_recycler_view) RecyclerView merchantPendingRecyclerView;

    public PendingMerchantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pending, null);;

        onViewCreated(view, null);

        merchantStatusModelList.add(new MerchantStatusModel("Pizza Hut", "Jayanagar, Bangalore", "PZ4", "Pending"));
        merchantStatusModelList.add(new MerchantStatusModel("Pizza Hut","Kormanagala, Bangalore","PZ5","Pending"));

        PopulateMerchantStatusAdapter.populateAdapter(getContext(), merchantStatusModelList, merchantPendingRecyclerView);

        return view;
    }
}
