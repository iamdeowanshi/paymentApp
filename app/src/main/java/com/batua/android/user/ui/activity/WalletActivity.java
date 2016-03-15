package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.ui.adapter.WalletTransactionFragmentPagerAdapter;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class WalletActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.wallet_transactions_tab_layout) TabLayout walletTransactionsTabLayout;
    @Bind(R.id.wallet_transactions_viewpager) ViewPager walletTransactionsViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transactions);

        setToolBar();
        loadFragments();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.arrow_back);
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
