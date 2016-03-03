package com.batua.android.ui.activity;

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
import android.widget.TextView;

import com.batua.android.R;
import com.batua.android.app.base.BaseActivity;
import com.batua.android.ui.adapter.HomeFragmentAdapter;

import butterknife.Bind;

/**
 * Created by febinp on 28/10/15.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;

    @Bind(R.id.home_tab_layout) TabLayout homeTabLayout;
    @Bind(R.id.home_viewpager) ViewPager homeViewPager;

    private TextView title;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showProfile();

        setToolBar();

        loadFragments();
    }

    private void showProfile() {
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView)toolbar.findViewById(R.id.toolbar_title);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch(id) {
            case R.id.nav_logout:
                startActivity(LoginActivity.class, null);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_merchant:
                startActivity(AddMerchantActivity.class, null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void loadFragments() {
        homeViewPager.setAdapter(new HomeFragmentAdapter((getSupportFragmentManager())));
        homeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        homeTabLayout.post(new Runnable() {
            @Override
            public void run() {
                homeTabLayout.setupWithViewPager(homeViewPager);
            }
        });
    }




}
