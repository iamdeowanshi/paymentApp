package com.batua.android.merchant.module.merchant.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
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
public class AddMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener, MerchantViewInteractor {

    @Inject MerchantPresenter presenter;
    @Inject Bakery bakery;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.add_or_merchant_tab_layout) TabLayout addMerchantTabLayout;
    @Bind(R.id.add_or__merchant_viewpager) ViewPager addMerchantViewPager;

    private TextView title;
    private Merchant merchant ;
    private MerchantFragmentPagerAdapter adapter;
    private MerchantRequest merchantRequest = new MerchantRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_add__or_edit_merchant);
        Injector.component().inject(this);
        presenter.attachViewInteractor(this);

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
            case R.id.action_save:
                onSaveClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(HomeActivity.class, null);
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
        adapter = new MerchantFragmentPagerAdapter(getSupportFragmentManager(), this, "Add", merchant);
        addMerchantViewPager.setAdapter(adapter);
        addMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                addMerchantTabLayout.setupWithViewPager(addMerchantViewPager);
            }
        });
    }

    public MerchantRequest getMerchantRequest() {
        return merchantRequest;
    }

    private boolean validateData() {
        if (merchantRequest.getName() == null || merchantRequest.getName().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getShortCode() == null || merchantRequest.getShortCode().isEmpty()) {
            return false;
        }

        if (merchantRequest.getPhone() == null || merchantRequest.getPhone().isEmpty()) {
            return false;
        }

        if ( merchantRequest.getFee() == null || merchantRequest.getFee() == 0) {
            return false;
        }

        return true;
    }

    private void onSaveClick() {
        if (validateData()) {
            merchantRequest.setStatus("Drafted");
            merchantRequest.setCreatedSalesId(3);
            presenter.addMerchant(merchantRequest);
            return;
        }

        bakery.snackShort(getContentView(), "Mandatory Basic information are missing");
    }

    @Override
    public void showMerchant(Merchant response) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Merchant", Parcels.wrap(response));
        startActivity(MerchantDetailsActivity.class, bundle);
        finish();
    }

}
