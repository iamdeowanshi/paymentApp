package com.batua.android.merchant.module.merchant.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;


import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;
import com.batua.android.merchant.module.merchant.view.adapter.AddMerchantFragmentPagerAdapter;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class EditMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener {

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.add_or_merchant_tab_layout) TabLayout editMerchantTabLayout;
    @Bind(com.batua.android.merchant.R.id.add_or__merchant_viewpager) ViewPager editMerchantViewPager;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_add__or_edit_merchant);

        setToolBar();
        loadFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.main_menu, menu);

        menu.findItem(com.batua.android.merchant.R.id.action_edit).setVisible(false);
        menu.findItem(com.batua.android.merchant.R.id.action_add_merchant).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void nextClicked(int position) {
        editMerchantViewPager.setCurrentItem(position + 1);
    }

    @Override
    public void previousClicked(int position) {
        editMerchantViewPager.setCurrentItem(position - 1);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView)toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        title.setText(com.batua.android.merchant.R.string.edit_merchant_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadFragments() {
        editMerchantViewPager.setAdapter(new AddMerchantFragmentPagerAdapter(getSupportFragmentManager(),this,"Edit"));
        editMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                editMerchantTabLayout.setupWithViewPager(editMerchantViewPager);
            }
        });
    }

}
