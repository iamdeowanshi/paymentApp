package com.batua.android.merchant.module.dashboard.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.batua.android.merchant.module.common.view.custom.LoadingView;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenter;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListViewInteractor;
import com.batua.android.merchant.module.dashboard.view.adapter.HomeFragmentPagerAdapter;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.onboard.view.activity.LoginActivity;
import com.batua.android.merchant.module.profile.view.activity.ProfileActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import timber.log.Timber;

/**
 * Created by febinp on 28/10/15.
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MerchantListViewInteractor{

    private static final String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int LOCATION_REQUEST_CODE = 4;

    @Inject MerchantListPresenter presenter;
    @Inject Bakery bakery;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.progress) ProgressBar progressBar;


    @Bind(R.id.home_tab_layout) TabLayout homeTabLayout;
    @Bind(R.id.home_viewpager) ViewPager homeViewPager;

    private TextView title;
    private ActionBarDrawerToggle toggle;
    private List<Merchant> merchantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_home);
        Injector.component().inject(this);
        presenter.attachViewInteractor(this);

        showProfile();

        setToolBar();

        presenter.getMerchant("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);

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
                checkLocationPermission();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLoadingView() {
        final Dialog loadDialog = new Dialog(this);
        loadDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        loadDialog.setContentView(getLayoutInflater().inflate(R.layout.view_loading, null));
        loadDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMerchant("");
    }

    private void loadFragments() {
        homeViewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), merchantList));
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
                startActivity(ProfileActivity.class, null);
            }
        });

    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView)toolbar.findViewById(R.id.toolbar_title);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.batua.android.merchant.R.string.navigation_drawer_open, com.batua.android.merchant.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(com.batua.android.merchant.R.drawable.menu);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void merchantList(List<Merchant> merchants) {
        this.merchantList = merchants;
        loadFragments();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, LOCATION_PERMISSION[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, LOCATION_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions();
        } else {
            startActivity(AddMerchantActivity.class, null);
        }
    }

    private void requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, LOCATION_PERMISSION[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, LOCATION_PERMISSION[1])) {
            Timber.d("request");
            // Display a SnackBar with an explanation and a button to trigger the request.
            bakery.snack(getContentView(), "Location permission is required to continue!", Snackbar.LENGTH_INDEFINITE, "Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(HomeActivity.this, LOCATION_PERMISSION, LOCATION_REQUEST_CODE);
                }
            });

        } else {
            // Permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, LOCATION_PERMISSION, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    startActivity(AddMerchantActivity.class, null);
                } else {
                    // Permission Denied
                    showLocationPermissionsSnackbar();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showLocationPermissionsSnackbar() {
        Snackbar.make(getContentView(), "Location permission is required to continue!", Snackbar.LENGTH_LONG)
                .setAction("ALLOW", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkLocationPermission();
                    }
                });
    }

}
