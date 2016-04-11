package com.batua.android.merchant.module.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.view.adapter.MerchantFragmentPagerAdapter;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class EditMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener {

    @Inject MerchantPresenter presenter;

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.add_or_merchant_tab_layout) TabLayout editMerchantTabLayout;
    @Bind(com.batua.android.merchant.R.id.add_or__merchant_viewpager) ViewPager editMerchantViewPager;

    private TextView title;
    private Merchant merchant;
    private MerchantRequest merchantRequest = new MerchantRequest();
    private MerchantFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_add__or_edit_merchant);
        Injector.component().inject(this);

        merchant = Parcels.unwrap(getIntent().getParcelableExtra("Merchant"));

        setToolBar();
        loadFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.main_menu, menu);

        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_add_merchant).setVisible(false);

        if (merchant.getStatus() == "Pending for Approval") {
            menu.findItem(R.id.action_save).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
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

        title = (TextView)toolbar.findViewById(R.id.toolbar_title);
        title.setText(R.string.edit_merchant_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadFragments() {
        adapter = new MerchantFragmentPagerAdapter(getSupportFragmentManager(),this,"Edit", merchant);
        editMerchantViewPager.setAdapter(adapter);
        editMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                editMerchantTabLayout.setupWithViewPager(editMerchantViewPager);
            }
        });
    }

    public MerchantRequest getMerchantRequest() {
        return merchantRequest;
    }

}
