package com.batua.android.merchant.module.dashboard.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.module.dashboard.view.fragment.ActiveMerchantFragment;
import com.batua.android.merchant.module.dashboard.view.fragment.DraftedMerchantFragment;
import com.batua.android.merchant.module.dashboard.view.fragment.PendingMerchantFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by febinp on 02/03/16.
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final String ACTIVE = "Active";
    private static final String DRAFTED = "Drafted";
    private static final String PENDING = "Pending for approval";

    private static List<Merchant> merchantList;
    private static List<Merchant> activeList = new ArrayList<>();
    private static List<Merchant> pendingList = new ArrayList<>();
    private static List<Merchant> draftedList = new ArrayList<>();

    private int active;
    private int drafted;
    private int pending;

    public HomeFragmentPagerAdapter(FragmentManager fm, List<Merchant> merchantList) {
        super(fm);
        this.merchantList = merchantList;
        setMerchantList();
    }

    private static int NUMBER_OF_HOME_FRAGMENT = 3;

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ActiveMerchantFragment();
                bundle.putParcelable("Merchant", Parcels.wrap(activeList));
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                fragment = new PendingMerchantFragment();
                bundle.putParcelable("Merchant", Parcels.wrap(pendingList));
                fragment.setArguments(bundle);
                return fragment;
            case 2:
                fragment = new DraftedMerchantFragment();
                bundle.putParcelable("Merchant", Parcels.wrap(draftedList));
                fragment.setArguments(bundle);
                return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_HOME_FRAGMENT;
    }

    private void setMerchantList() {
        activeList = new ArrayList<>();
        draftedList = new ArrayList<>();
        pendingList = new ArrayList<>();

        if (merchantList!=null) {
            for (Merchant merchant  :merchantList) {
                switch (merchant.getStatus()) {
                    case ACTIVE:
                        activeList.add(merchant);
                        break;
                    case DRAFTED:
                        draftedList.add(merchant);
                        break;
                    case PENDING:
                        pendingList.add(merchant);
                }
            }

            active = activeList.size();
            drafted = draftedList.size();
            pending = pendingList.size();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Active" + "("+ active + ")";
            case 1:
                return "Pending" + "("+ pending + ")";
            case 2:
                return "Drafted" + "("+ drafted + ")";
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
