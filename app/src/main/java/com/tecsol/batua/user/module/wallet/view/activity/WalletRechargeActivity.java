package com.tecsol.batua.user.module.wallet.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.wallet.view.adapter.WalletRechargePagerAdapter;

import butterknife.Bind;

public class WalletRechargeActivity extends BaseActivity {

    @Bind(com.batua.android.user.R.id.wallet_tab_layout)
    TabLayout walletTabLayout;
    @Bind(com.batua.android.user.R.id.wallet_viewpager)
    ViewPager walletViewpager;
    @Bind(com.batua.android.user.R.id.toolbar)
    Toolbar toolbar;
    @Bind(com.batua.android.user.R.id.toolbar_title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.user.R.layout.activity_wallet_recharge);

        setToolBar();

        loadFragments();
    }

    private void loadFragments() {
        walletViewpager.setAdapter(new WalletRechargePagerAdapter(getSupportFragmentManager(), this));
        walletViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                final InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getContentView().getWindowToken(), 0);
            }
        });

        walletTabLayout.post(new Runnable() {
            @Override
            public void run() {
                walletTabLayout.setupWithViewPager(walletViewpager);
            }
        });
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText("Wallet Recharge");
    }

}
