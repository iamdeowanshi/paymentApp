package com.batua.android.user.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.batua.android.user.R;
import com.batua.android.user.data.model.WalletTransaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class PaymentsFragment extends WalletTransactionFragment {

    @Bind(R.id.wallet_payment_recycler_view) RecyclerView rechargedRecyclerView;

    private View view;
    private List<WalletTransaction> walletTransactionList = new ArrayList<WalletTransaction>();

    @Override
    RecyclerView getWalletTransactionRecyclerView() {
        return rechargedRecyclerView;
    }

    @Override
    List<WalletTransaction> getWalletTransactionList() {
        walletTransactionList.add(new WalletTransaction("200", "15/03/2016", "89760987436357", "Wallet", null));
        walletTransactionList.add(new WalletTransaction("300", "14/03/2016", "89760987435367", "Wallet", null));

        return walletTransactionList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wallet_payment, null);

        onViewCreated(view, null);
        populateAdapter();

        return view;
    }
    
}
