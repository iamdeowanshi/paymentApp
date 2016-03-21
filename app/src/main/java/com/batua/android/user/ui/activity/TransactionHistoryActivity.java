package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.data.model.TransactionHistory;
import com.batua.android.user.ui.adapter.TransactionHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class TransactionHistoryActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.transaction_history_recycler_view) RecyclerView transactionHistoryRecyclerView;

    private TransactionHistoryAdapter transactionHistoryAdapter;
    private List<TransactionHistory> transactionHistoryList =  new ArrayList<TransactionHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        setToolBar();
        populateAdapter();
    }

    private void populateAdapter() {
        transactionHistoryList.add(new TransactionHistory("200", "15/03/2016", "89760987436357", "Debit Card", "Pizza Hut", "RTYUIGH7436357"));
        transactionHistoryList.add(new TransactionHistory("200", "15/03/2016", "89760987436357", "Credit Card", "Health and Glow", "RTYUIGH7436357"));

        transactionHistoryAdapter = new TransactionHistoryAdapter(transactionHistoryList, this);
        LinearLayoutManager llayout = new LinearLayoutManager(this);
        transactionHistoryRecyclerView.setLayoutManager(llayout);
        transactionHistoryRecyclerView.setAdapter(transactionHistoryAdapter);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
