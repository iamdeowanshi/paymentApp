package com.batua.android.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.ui.adapter.AddMerchantFragmentAdapter;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class AddMerchantActivity extends BaseActivity{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.add_merchant_tab_layout) TabLayout addMerchantTabLayout;
    @Bind(R.id.add_merchant_viewpager) ViewPager addMerchantViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merchant);

        setToolBar();

        loadFragments();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        addMerchantViewPager.setAdapter(new AddMerchantFragmentAdapter((getSupportFragmentManager())));
        addMerchantTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        addMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                addMerchantTabLayout.setupWithViewPager(addMerchantViewPager);
            }
        });
    }



}
