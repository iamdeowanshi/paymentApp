package com.batua.android.merchant.module.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.ViewUtil;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;
import com.batua.android.merchant.module.merchant.presenter.MerchantPresenter;
import com.batua.android.merchant.module.merchant.presenter.MerchantViewInteractor;
import com.batua.android.merchant.module.merchant.view.adapter.MerchantFragmentPagerAdapter;
import com.batua.android.merchant.module.merchant.view.listener.NextClickedListener;
import com.batua.android.merchant.module.merchant.view.listener.PreviousClickedListener;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class EditMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener, MerchantViewInteractor {

    @Inject MerchantPresenter presenter;
    @Inject ViewUtil viewUtil;

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.progress) ProgressBar progressBar;
    @Bind(com.batua.android.merchant.R.id.add_or_merchant_tab_layout) TabLayout editMerchantTabLayout;
    @Bind(com.batua.android.merchant.R.id.add_or__merchant_viewpager) ViewPager editMerchantViewPager;

    private TextView title;
    private Merchant merchant;
    private MerchantRequest merchantRequest;
    private MerchantFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_add__or_edit_merchant);
        Injector.component().inject(this);
        presenter.attachViewInteractor(this);

        merchant = Parcels.unwrap(getIntent().getParcelableExtra("Merchant"));
        merchantRequest = new MerchantRequest(merchant);

        setToolBar();
        loadFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.main_menu, menu);

        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_add_merchant).setVisible(false);

        if (merchant.getStatus().toString().equalsIgnoreCase("Pending for approval")) {
            menu.findItem(R.id.action_save).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                merchantRequest.setCreatedSalesId(3);
                merchantRequest.setId(merchant.getId());
                merchantRequest.setStatus(merchant.getStatus());
                viewUtil.hideKeyboard(this);
                presenter.updateMerchant(merchantRequest);
                break;
        }

        return super.onOptionsItemSelected(item);
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

    @Override
    public void showMerchant(Merchant response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("MerchantDetail", Parcels.wrap(response));
        startActivity(MerchantDetailsActivity.class, bundle);
        finish();
    }

    @Override
    public void onNetworkCallProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        progressBar.setVisibility(View.GONE);
    }

}
