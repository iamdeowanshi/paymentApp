package com.batua.android.merchant.module.dashboard.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.batua.android.merchant.data.model.Merchant.Merchant;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by febinp on 02/03/16.
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String ACTIVE = "Active";
    private static final String DRAFTED = "Drafted";
    private static final String PENDING = "Pending for approval";

    private List<Merchant> merchantList;
    private List<Merchant> activeList = new ArrayList<>();
    private List<Merchant> pendingList = new ArrayList<>();
    private List<Merchant> draftedList = new ArrayList<>();

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

}
