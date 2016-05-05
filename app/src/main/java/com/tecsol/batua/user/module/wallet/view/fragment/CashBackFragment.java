package com.tecsol.batua.user.module.wallet.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.tecsol.batua.user.data.model.User.WalletTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class CashBackFragment extends WalletTransactionFragment {

    @Bind(com.batua.android.user.R.id.wallet_cashback_recycler_view)
    RecyclerView rechargedRecyclerView;

    private View view;
    private List<WalletTransaction> walletTransactionList = new ArrayList<WalletTransaction>();

    @Override
    RecyclerView getWalletTransactionRecyclerView() {
        return rechargedRecyclerView;
    }

    @Override
    List<WalletTransaction> getWalletTransactionList() {
        walletTransactionList.add(new WalletTransaction("200", "15/03/2016", "89760987436357", "Debit Card", "Pizza Hut"));
        walletTransactionList.add(new WalletTransaction("300", "14/03/2016", "89760987435367", "Wallet", "Health and Glow"));

        return walletTransactionList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(com.batua.android.user.R.layout.fragment_wallet_cashback, null);

        onViewCreated(view, null);
        populateAdapter();

        return view;
    }

}
