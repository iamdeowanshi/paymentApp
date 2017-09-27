package com.tecsol.batua.user.module.wallet.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tecsol.batua.user.module.wallet.view.fragment.CashBackFragment;
import com.tecsol.batua.user.module.wallet.view.fragment.PaymentsFragment;
import com.tecsol.batua.user.module.wallet.view.fragment.RechargedFragment;

/**
 * @author Arnold Laishram.
 */
public class WalletTransactionFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int FRAGMENT_PAGER_COUNT = 3;

    public WalletTransactionFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return FRAGMENT_PAGER_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RechargedFragment();
            case 1:
                return new PaymentsFragment();
            case 2:
                return new CashBackFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Recharged";
            case 1:
                return "Payments";
            case 2:
                return "Cashback";
        }

        return null;
    }

}
