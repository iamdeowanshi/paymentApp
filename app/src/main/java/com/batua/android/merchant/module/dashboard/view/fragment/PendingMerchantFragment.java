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
public class PendingMerchantFragment extends MerchantFragment {

    private View view;
    private List<Merchant> pendingList;
    @Bind(com.batua.android.merchant.R.id.merchant_pending_status_recycler_view) RecyclerView merchantPendingRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_pending, null);;

        onViewCreated(view, null);
        pendingList = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));

        populateAdapter();

        return view;
    }

    @Override
    RecyclerView getMerchantRecyclerView() {
        return merchantPendingRecyclerView;
    }

    List<Merchant> getActiveList() {
        return pendingList;
    }
}
