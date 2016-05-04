package com.tecsol.batua.user.module.dashboard.view.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.batua.android.user.R;
import com.tecsol.batua.user.data.model.Merchant.Merchant;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.common.view.activity.LocationFetchActivity;
import com.tecsol.batua.user.module.dashboard.presenter.HomePresenter;
import com.tecsol.batua.user.module.dashboard.presenter.HomeViewInteractor;
import com.tecsol.batua.user.module.dashboard.view.adapter.MerchantListAdapter;
import com.tecsol.batua.user.module.dashboard.view.fragment.NavigationFragment;
import com.tecsol.batua.user.module.onboard.view.activity.OnBoardActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnTextChanged;

/**
 * @author Arnold Laishram.
 */
public class HomeActivity extends LocationFetchActivity implements HomeViewInteractor{

    private final int NO_MERCHANT_FILTER = 0;

    @Inject HomePresenter homePresenter;
    @Inject Bakery bakery;
    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.merchant_list_recycler_view) RecyclerView merchantListRecyclerView;
    @Bind(R.id.progressBar3) ProgressBar progressBar;
    @Bind(R.id.txt_search) EditText edtSearch;

    private ActionBarDrawerToggle toggle;
    private MerchantListAdapter merchantListAdapter;
    private List<Merchant> filteredMerchantDetailList = new ArrayList<>();
    private List<Merchant> unFilteredMerchantDetailList = new ArrayList<>();
    private NavigationFragment navigationFragment;
    private User user;

    @OnTextChanged(R.id.txt_search)
    void searchMerchant(CharSequence searchMerchant){
        if (!searchMerchant.toString().isEmpty()) {
            filteredMerchantDetailList = new ArrayList<>();
            for (Merchant merchant : unFilteredMerchantDetailList) {
                if (merchant.getName().toLowerCase().contains(searchMerchant.toString().toLowerCase())) {
                    filteredMerchantDetailList.add(merchant);
                }
            }
            fillMerchantList();
            return;
        }

        edtSearch.setCursorVisible(false);
        filteredMerchantDetailList = unFilteredMerchantDetailList;
        fillMerchantList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Injector.component().inject(this);
        homePresenter.attachViewInteractor(this);

        setToolBar();
        initializeNavigation();

        user = (User)preferenceUtil.read(preferenceUtil.USER, User.class);
        onNetworkCallProgress();
    }


    @Override
    public void locationDetected(Location location) {
        onNetworkCallCompleted();
        if (user!=null) {
            homePresenter.getMerchants(user.getId(), NO_MERCHANT_FILTER, location.getLatitude(), location.getLongitude());
            return;
        }
        startActivity(OnBoardActivity.class, null);
    }

    @Override
    public void onSuccess(List<Merchant> merchantList) {
        this.filteredMerchantDetailList = merchantList;
        this.unFilteredMerchantDetailList = merchantList;
        fillMerchantList();
    }

    @Override
    public void onNetworkCallProgress() {
        merchantListRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkCallCompleted() {
        merchantListRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkCallError(Throwable e) {
        merchantListRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        if (e.getMessage()== null || e.getMessage().isEmpty()){
            return;
        }
        bakery.snackShort(getContentView(), e.getMessage());
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
        merchantListAdapter = new MerchantListAdapter(filteredMerchantDetailList);
        LinearLayoutManager llayout = new LinearLayoutManager(this);
        merchantListRecyclerView.setLayoutManager(llayout);
        merchantListRecyclerView.setAdapter(merchantListAdapter);
    }

}

