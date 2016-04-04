package com.batua.android.merchant.module.dashboard.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.batua.android.merchant.data.model.MerchantStatusModel;
import com.batua.android.merchant.module.base.BaseFragment;

import java.util.List;

/**
 * @author Arnold Laishram.
 */
public abstract class MerchantFragment extends BaseFragment {

    private MerchantStatusAdapter merchantStatusAdapter;

    abstract RecyclerView getMerchantRecyclerView();

    abstract List<MerchantStatusModel> getMerchantList();

    protected void populateAdapter() {
        merchantStatusAdapter = new MerchantStatusAdapter(getMerchantList());
        LinearLayoutManager llayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        getMerchantRecyclerView().setLayoutManager(llayout);
        getMerchantRecyclerView().setAdapter(merchantStatusAdapter);
    }

    public MerchantStatusAdapter getMerchantStatusAdapter() {
        return merchantStatusAdapter;
    }

}
