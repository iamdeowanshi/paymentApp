package com.batua.android.merchant.module.merchant.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantViewInteractor;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;
import com.batua.android.merchant.module.merchant.view.adapter.MerchantFragmentPagerAdapter;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class AddMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener{

    @Inject MerchantPresenter presenter;

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.add_or_merchant_tab_layout) TabLayout addMerchantTabLayout;
    @Bind(com.batua.android.merchant.R.id.add_or__merchant_viewpager) ViewPager addMerchantViewPager;

    private TextView title;
    private MerchantRequest merchantRequest = new MerchantRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_add__or_edit_merchant);
        Injector.component().inject(this);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case com.batua.android.merchant.R.id.action_save:
                startActivity(MerchantDetailsActivity.class,null);
                presenter.addMerchant(null);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        title = (TextView)toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        title.setText(com.batua.android.merchant.R.string.add_merchant_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadFragments() {
        addMerchantViewPager.setAdapter(new MerchantFragmentPagerAdapter(getSupportFragmentManager(), this, "Add", merchantRequest));
        addMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                addMerchantTabLayout.setupWithViewPager(addMerchantViewPager);
            }
        });
    }

}
