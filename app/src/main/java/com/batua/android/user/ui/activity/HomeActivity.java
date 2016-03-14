package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.data.model.MerchantDetail;
import com.batua.android.user.ui.adapter.MerchantListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author Arnold Laishram.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.merchant_list_recycler_view) RecyclerView merchantListRecyclerView;

    private ActionBarDrawerToggle toggle;
    private MerchantListAdapter merchantListAdapter;
    private List<MerchantDetail> merchantDetailList = new ArrayList<MerchantDetail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showProfile();

        setToolBar();

        fillMerchantList();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    private void showProfile() {
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);

        ImageView profileimage = (ImageView)headerLayout.findViewById(R.id.img_profile);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

        merchantDetailList.add(new MerchantDetail("Pizza Hut", 5.0f, 10, "JP Nagar Bangalore", "1km"));
        merchantDetailList.add(new MerchantDetail("Health and Glow", 3.0f, 10, "JP Nagar Bangalore", "2km"));

        merchantListAdapter = new MerchantListAdapter(merchantDetailList);
        LinearLayoutManager llayout = new LinearLayoutManager(this);
        merchantListRecyclerView.setLayoutManager(llayout);
        merchantListRecyclerView.setAdapter(merchantListAdapter);

    }

}
