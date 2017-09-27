package com.batua.android.merchant.module.dashboard.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;

/**
 * Created by febinp on 01/03/16.
 */
public class ActiveMerchantFragment extends MerchantFragment{

    @Bind(R.id.merchant_active_status_recycler_view) RecyclerView merchantActiveRecyclerView;

    private List<Merchant> activeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activeList = Parcels.unwrap(this.getArguments().getParcelable("Merchant"));

        populateAdapter();
    }

    @Override
    RecyclerView getMerchantRecyclerView() {
        return merchantActiveRecyclerView;
    }

    List<Merchant> getActiveList() {
        return activeList;
    }

}
