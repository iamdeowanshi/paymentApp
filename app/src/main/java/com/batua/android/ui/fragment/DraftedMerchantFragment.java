package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.R;
import com.batua.android.data.model.MerchantStatusModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by febinp on 01/03/16.
 */
public class DraftedMerchantFragment extends MerchantFragment{

    private View view;
    private List<MerchantStatusModel> merchantStatusModelList = new ArrayList<MerchantStatusModel>();

    @Bind(R.id.merchant_drafted_status_recycler_view) RecyclerView merchantDraftedRecyclerView;

    public DraftedMerchantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_draft, null);;

        onViewCreated(view, null);

        populateAdapter();
        
        return view;
    }

    @Override
    RecyclerView getMerchantRecyclerView() {
        return merchantDraftedRecyclerView;
    }

    @Override
    List<MerchantStatusModel> getMerchantList() {
        merchantStatusModelList.add(new MerchantStatusModel("Pizza Hut", "Jayanagar, Bangalore", "PZ4", "Drafted"));

        return merchantStatusModelList;
    }
}
