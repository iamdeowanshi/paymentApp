package com.tecsol.batua.user.module.wallet.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tecsol.batua.user.module.payment.view.fragment.CardFragment;
import com.tecsol.batua.user.module.payment.view.fragment.NetBankingFragment;

/**
 * @author Aaditya Deowanshi.
 */
public class WalletRechargePagerAdapter extends FragmentPagerAdapter {

    private Activity activity;

    public WalletRechargePagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CardFragment();
            case 1:
                return new NetBankingFragment();
        }

        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Card";
            case 1:
                return "Net Banking";
        }

        return null;
    }

}
