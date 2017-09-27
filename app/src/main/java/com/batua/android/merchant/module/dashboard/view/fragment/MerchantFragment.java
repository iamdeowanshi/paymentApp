package com.batua.android.merchant.module.dashboard.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.dashboard.view.adapter.MerchantStatusAdapter;

import java.util.List;

/**
 * @author Arnold Laishram.
 */
public abstract class MerchantFragment extends BaseFragment {

    private MerchantStatusAdapter merchantStatusAdapter;

    abstract RecyclerView getMerchantRecyclerView();

    abstract List<Merchant> getActiveList();

    protected void populateAdapter() {
        merchantStatusAdapter = new MerchantStatusAdapter(getActiveList());
        LinearLayoutManager llayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        getMerchantRecyclerView().setLayoutManager(llayout);
        getMerchantRecyclerView().setAdapter(merchantStatusAdapter);
    }


}
