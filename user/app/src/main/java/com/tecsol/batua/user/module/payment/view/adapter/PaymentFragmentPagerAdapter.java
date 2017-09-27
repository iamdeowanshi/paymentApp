package com.tecsol.batua.user.module.payment.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tecsol.batua.user.module.payment.view.fragment.CardOrNetBankingFragment;
import com.tecsol.batua.user.module.payment.view.fragment.WalletFragment;

/**
 * @author Aaditya Deowanshi.
 */
public class PaymentFragmentPagerAdapter extends FragmentPagerAdapter {

    private Activity activity;

    public PaymentFragmentPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WalletFragment();
            case 1:
                return new CardOrNetBankingFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Wallet";
            case 1:
                return "Card/Net Banking";
        }

        return null;
    }

}
