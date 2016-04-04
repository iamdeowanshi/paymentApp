package com.batua.android.merchant.module.dashboard.view;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.onboard.view.activity.LoginActivity;
import com.batua.android.merchant.module.profile.view.activity.ProfileActivity;

import butterknife.Bind;

/**
 * Created by febinp on 28/10/15.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.drawer_layout) DrawerLayout drawer;
    @Bind(com.batua.android.merchant.R.id.nav_view) NavigationView navigationView;

    @Bind(com.batua.android.merchant.R.id.home_tab_layout) TabLayout homeTabLayout;
    @Bind(com.batua.android.merchant.R.id.home_viewpager) ViewPager homeViewPager;

    private TextView title;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_home);

        showProfile();

        setToolBar();

        loadFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.main_menu, menu);

        menu.findItem(com.batua.android.merchant.R.id.action_edit).setVisible(false);
        menu.findItem(com.batua.android.merchant.R.id.action_save).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch(id) {
            case com.batua.android.merchant.R.id.nav_logout:
                startActivity(LoginActivity.class, null);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case com.batua.android.merchant.R.id.action_add_merchant:
                startActivity(AddMerchantActivity.class, null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void loadFragments() {
        homeViewPager.setAdapter(new HomeFragmentPagerAdapter((getSupportFragmentManager())));
        homeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        homeTabLayout.post(new Runnable() {
            @Override
            public void run() {
                homeTabLayout.setupWithViewPager(homeViewPager);
            }
        });
    }

    private void showProfile() {
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(com.batua.android.merchant.R.layout.nav_header_main);

        ImageView profileimage = (ImageView)headerLayout.findViewById(com.batua.android.merchant.R.id.img_profile);

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ProfileActivity.class,null);
            }
        });

    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView)toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.batua.android.merchant.R.string.navigation_drawer_open, com.batua.android.merchant.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(com.batua.android.merchant.R.drawable.menu);
    }

}
