package com.batua.android.merchant.module.dashboard.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;

/**
 * Created by febinp on 01/03/16.
 */
public class ActiveMerchantFragment extends MerchantFragment{

    @Bind(R.id.merchant_active_status_recycler_view) RecyclerView merchantActiveRecyclerView;

    private View view;
    private List<Merchant> activeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(com.batua.android.merchant.R.layout.fragment_active, null);

        onViewCreated(view, null);
        activeList = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));
        populateAdapter();

        return view;
    }

    @Override
    RecyclerView getMerchantRecyclerView() {
        return merchantActiveRecyclerView;
    }

    List<Merchant> getActiveList() {
        return activeList;
    }

}
