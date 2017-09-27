package com.batua.android.merchant.module.dashboard.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.merchant.data.model.Merchant.Merchant;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;

/**
 * Created by febinp on 01/03/16.
 */
public class DraftedMerchantFragment extends MerchantFragment{

    private View view;
    private List<Merchant> draftedList;

    @Bind(com.batua.android.merchant.R.id.merchant_drafted_status_recycler_view) RecyclerView merchantDraftedRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_draft, null);;

        onViewCreated(view, null);
        draftedList = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));

        populateAdapter();
        
        return view;
    }

    @Override
    RecyclerView getMerchantRecyclerView() {
        return merchantDraftedRecyclerView;
    }

    List<Merchant> getActiveList() {
        return draftedList;
    }
}
