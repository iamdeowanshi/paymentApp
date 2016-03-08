package com.batua.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.R;
import com.batua.android.app.base.BaseFragment;
import com.batua.android.data.model.MerchantStatusModel;
import com.batua.android.ui.activity.MerchantDetailsActivity;
import com.batua.android.ui.adapter.MerchantStatusAdapter;
import com.batua.android.ui.custom.PopulateMerchantStatusAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by febinp on 01/03/16.
 */
public class ActiveMerchantFragment extends MerchantFragment{

    @Bind(R.id.merchant_active_status_recycler_view) RecyclerView merchantActiveRecyclerView;

    private View view;
    private List<MerchantStatusModel> merchantStatusModelList = new ArrayList<MerchantStatusModel>();

    /*public ActiveMerchantFragment() {
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_active, null);

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
