package com.batua.android.merchant.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.app.base.BaseActivity;
import com.batua.android.merchant.listener.NextClickedListener;
import com.batua.android.merchant.listener.PreviousClickedListener;
import com.batua.android.merchant.ui.adapter.AddMerchantFragmentPagerAdapter;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class AddMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.add_or_merchant_tab_layout) TabLayout addMerchantTabLayout;
    @Bind(R.id.add_or__merchant_viewpager) ViewPager addMerchantViewPager;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__or_edit_merchant);

        setToolBar();

        loadFragments();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_add_merchant).setVisible(false);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void nextClicked(int position) {
        addMerchantViewPager.setCurrentItem(position + 1);
    }

    @Override
    public void previousClicked(int position) {
        addMerchantViewPager.setCurrentItem(position - 1);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView)toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.add_merchant_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadFragments() {
        addMerchantViewPager.setAdapter(new AddMerchantFragmentPagerAdapter(getSupportFragmentManager(), this, "Add"));
        addMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                addMerchantTabLayout.setupWithViewPager(addMerchantViewPager);
            }
        });
    }

}
