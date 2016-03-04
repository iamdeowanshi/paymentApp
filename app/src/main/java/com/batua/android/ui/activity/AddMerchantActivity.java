package com.batua.android.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.listener.NextClickedListener;
import com.batua.android.listener.PreviousClickedListener;
import com.batua.android.ui.adapter.AddMerchantFragmentAdapter;
import com.batua.android.ui.fragment.MerchantBankInfoFragment;
import com.batua.android.ui.fragment.MerchantBasicInfoFragment;
import com.batua.android.ui.fragment.MerchantLocationInfoFragment;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by febinp on 02/03/16.
 */
public class AddMerchantActivity extends BaseActivity implements NextClickedListener, PreviousClickedListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.add_or_merchant_tab_layout) TabLayout addMerchantTabLayout;
    @Bind(R.id.add_or__merchant_viewpager) ViewPager addMerchantViewPager;

    @Inject MerchantBasicInfoFragment merchantBasicInfoFragment;
    @Inject MerchantLocationInfoFragment merchantLocationInfoFragment;
    @Inject MerchantBankInfoFragment merchantBankInfoFragment;

    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__or_edit_merchant);

        injectDependencies();

        setToolBar();

        //setListener();

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
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadFragments() {
        addMerchantViewPager.setAdapter(new AddMerchantFragmentAdapter(getSupportFragmentManager(), this , "Add"));
        addMerchantTabLayout.post(new Runnable() {
            @Override
            public void run() {
                addMerchantTabLayout.setupWithViewPager(addMerchantViewPager);
            }
        });
    }

    private void setListener() {
        merchantBasicInfoFragment.setNextClickedListener(AddMerchantActivity.this);
        merchantLocationInfoFragment.setNextClickedListener(AddMerchantActivity.this);
        merchantLocationInfoFragment.setPreviousClickedListener(AddMerchantActivity.this);
        merchantBankInfoFragment.setPreviousClickedListener(AddMerchantActivity.this);
    }
}
