package com.batua.android.merchant.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.batua.android.merchant.ui.fragment.MerchantBankInfoFragment;
import com.batua.android.merchant.ui.fragment.MerchantBasicInfoFragment;
import com.batua.android.merchant.ui.fragment.MerchantLocationInfoFragment;

/**
 * Created by febinp on 02/03/16.
 */
public class AddMerchantFragmentAdapter extends FragmentPagerAdapter {
    public AddMerchantFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    private static int NUMBER_OF_ADD_MERCHANT_FRAGMENT = 3;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MerchantBasicInfoFragment();
            case 1:
                return new MerchantLocationInfoFragment();
            case 2:
                return new MerchantBankInfoFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_ADD_MERCHANT_FRAGMENT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Basic Info";
            case 1:
                return "Location Info";
            case 2:
                return "Bank Info";
        }

        return null;
    }

}