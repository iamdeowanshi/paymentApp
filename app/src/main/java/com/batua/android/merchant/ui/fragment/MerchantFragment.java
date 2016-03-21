package com.batua.android.merchant.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.merchant.app.base.BaseFragment;
import com.batua.android.merchant.data.model.MerchantStatusModel;
import com.batua.android.merchant.ui.adapter.MerchantStatusAdapter;

import java.util.List;

/**
 * @author Aaditya Deowanshi.
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
