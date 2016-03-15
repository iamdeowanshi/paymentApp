package com.batua.android.user.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.batua.android.user.app.base.BaseFragment;
import com.batua.android.user.data.model.WalletTransaction;
import com.batua.android.user.ui.adapter.WalletTransactionAdapter;

import java.util.List;

/**
 * @author Arnold Laishram.
 */
abstract class WalletTransactionFragment extends BaseFragment {

    private WalletTransactionAdapter walletTransactionAdapter;

    abstract RecyclerView getWalletTransactionRecyclerView();

    abstract List<WalletTransaction> getWalletTransactionList();

    protected void populateAdapter() {
        walletTransactionAdapter = new WalletTransactionAdapter(getWalletTransactionList());
        LinearLayoutManager llayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        getWalletTransactionRecyclerView().setLayoutManager(llayout);
        getWalletTransactionRecyclerView().setAdapter(walletTransactionAdapter);
    }

    public WalletTransactionAdapter getLeaderBoardAdapter() {
        return walletTransactionAdapter;
    }

}
