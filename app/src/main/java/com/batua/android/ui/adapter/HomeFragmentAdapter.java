package com.batua.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.batua.android.ui.fragment.ActiveMerchantFragment;
import com.batua.android.ui.fragment.DraftedMerchantFragment;
import com.batua.android.ui.fragment.PendingMerchantFragment;

/**
 * Created by febinp on 02/03/16.
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter{
        public HomeFragmentAdapter(FragmentManager fm) {
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
                    return "Active";
                case 1:
                    return "Pending";
                case 2:
                    return "Drafted";
            }
            return null;
        }

}
