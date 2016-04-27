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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.Bakery;
import com.batua.android.merchant.module.common.util.InternetUtil;
import com.batua.android.merchant.module.common.util.PermissionUtil;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.dashboard.presenter.LogoutPresenter;
import com.batua.android.merchant.module.dashboard.presenter.LogoutViewInteractor;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListPresenter;
import com.batua.android.merchant.module.dashboard.presenter.MerchantListViewInteractor;
import com.batua.android.merchant.module.dashboard.view.adapter.HomeFragmentPagerAdapter;
import com.batua.android.merchant.module.dashboard.view.fragment.NavigationFragment;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.onboard.view.activity.LoginActivity;
import com.batua.android.merchant.module.profile.view.activity.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import butterknife.OnPageChange;
import butterknife.OnTextChanged;
import timber.log.Timber;

/**
 * Created by febinp on 28/10/15.
 */
public class HomeActivity extends BaseActivity implements MerchantListViewInteractor {

    private static final String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int LOCATION_REQUEST_CODE = 4;
    private static int SELECTED_PAGE = 2;

    @Inject MerchantListPresenter merchantListPresenter;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.progress) ProgressBar progressBar;
    @Bind(R.id.home_tab_layout) TabLayout homeTabLayout;
    @Bind(R.id.home_viewpager) ViewPager homeViewPager;

    private ActionBarDrawerToggle toggle;
    private User user;
    private List<Merchant> unFilteredMerchantList;
    private List<Merchant> filteredMerchantList;
    private NavigationFragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Injector.component().inject(this);
        merchantListPresenter.attachViewInteractor(this);

        user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);

        if (user == null) {
            startActivityClearTop(LoginActivity.class, null);
            finish();
        }

        setToolBar();
        initializeNavigation();

        if (!InternetUtil.hasInternetConnection(this)){
            showNoInternetTitleDialog(this);

            return;
        }

        merchantListPresenter.getMerchant("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_save).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        merchantListPresenter.getMerchant("");
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
        this.unFilteredMerchantList = merchants;
        this.filteredMerchantList = merchants;
        loadFragments();
    }

    @OnTextChanged(R.id.txt_search)
    public void searchMerchant(CharSequence filterText){
        if (!filterText.toString().isEmpty()) {
            filteredMerchantList = new ArrayList<>();
            for (Merchant merchant : unFilteredMerchantList) {
                if (merchant.getName().toLowerCase().contains(filterText.toString().toLowerCase())
                        || merchant.getShortCode().toLowerCase().contains(filterText.toString().toLowerCase())) {
                    filteredMerchantList.add(merchant);
                    loadFragments();
                }
            }

            return;
        }

        filteredMerchantList = unFilteredMerchantList;
        loadFragments();
    }

    @OnPageChange(R.id.home_viewpager)
    public void onPageSelected(int position){
        SELECTED_PAGE = position;
    }

    private void loadFragments() {

        homeViewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), filteredMerchantList));
        homeViewPager.setCurrentItem(SELECTED_PAGE);
        homeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        homeTabLayout.post(new Runnable() {
            @Override
            public void run() {
                homeTabLayout.setupWithViewPager(homeViewPager);
            }
        });
    }

    private void initializeNavigation() {
        navigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer);
        navigationFragment.initializeDrawer(drawer);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.batua.android.merchant.R.string.navigation_drawer_open, com.batua.android.merchant.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);
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
