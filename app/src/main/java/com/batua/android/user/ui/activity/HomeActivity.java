package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.batua.android.user.R;
import com.batua.android.user.ui.fragment.NavigationFragment;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.dashboard.view.adapter.MerchantListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.merchant_list_recycler_view) RecyclerView merchantListRecyclerView;

    private ActionBarDrawerToggle toggle;
    private MerchantListAdapter merchantListAdapter;
    private List<Merchant> merchantDetailList = new ArrayList<>();
    private NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setToolBar();
        initializeNavigation();
        fillMerchantList();
    }

    private void initializeNavigation() {
        navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        navigationFragment.initializeDrawer(drawer);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
    }

    private void fillMerchantList() {
        merchantListAdapter = new MerchantListAdapter(merchantDetailList);
        LinearLayoutManager llayout = new LinearLayoutManager(this);
        merchantListRecyclerView.setLayoutManager(llayout);
        merchantListRecyclerView.setAdapter(merchantListAdapter);
    }

}

