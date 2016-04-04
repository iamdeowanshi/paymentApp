package com.batua.android.merchant.module.dashboard.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by febinp on 02/03/16.
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private static int NUMBER_OF_HOME_FRAGMENT = 3;

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ActiveMerchantFragment();
                case 1:
                    return new PendingMerchantFragment();
                case 2:
                    return new DraftedMerchantFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return NUMBER_OF_HOME_FRAGMENT;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Active" + "(9)";
                case 1:
                    return "Pending";
                case 2:
                    return "Drafted";
            }
            return null;
        }

}
