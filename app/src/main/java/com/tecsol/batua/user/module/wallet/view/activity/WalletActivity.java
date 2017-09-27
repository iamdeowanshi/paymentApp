package com.tecsol.batua.user.module.wallet.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.wallet.view.adapter.WalletTransactionFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class WalletActivity extends BaseActivity {

    @Bind(com.batua.android.user.R.id.toolbar)
    Toolbar toolbar;
    @Bind(com.batua.android.user.R.id.wallet_transactions_tab_layout)
    TabLayout walletTransactionsTabLayout;
    @Bind(com.batua.android.user.R.id.wallet_transactions_viewpager)
    ViewPager walletTransactionsViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_wallet_transactions);

        setToolBar();
        loadFragments();
    }

    @OnClick(com.batua.android.user.R.id.btn_add)
    void onAddClick() {
        startActivity(WalletRechargeActivity.class, null);
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

    private void loadFragments() {
        walletTransactionsViewpager.setAdapter(new WalletTransactionFragmentPagerAdapter(getSupportFragmentManager()));
        walletTransactionsTabLayout.post(new Runnable() {
            @Override
            public void run() {
                walletTransactionsTabLayout.setupWithViewPager(walletTransactionsViewpager);
            }
        });
    }

}
