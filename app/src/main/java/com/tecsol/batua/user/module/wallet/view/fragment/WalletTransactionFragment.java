package com.tecsol.batua.user.module.wallet.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tecsol.batua.user.data.model.WalletTransaction;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.wallet.view.adapter.WalletTransactionAdapter;

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
