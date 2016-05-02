package com.tecsol.batua.user.module.payment.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tecsol.batua.user.module.payment.view.fragment.CardFragment;
import com.tecsol.batua.user.module.payment.view.fragment.NetBankingFragment;
import com.tecsol.batua.user.module.payment.view.fragment.WalletFragment;

/**
 * @author Aaditya Deowanshi.
 */
public class PaymentFragmentPagerAdapter extends FragmentPagerAdapter {

    private Activity activity;

    public PaymentFragmentPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WalletFragment();
            case 1:
                return new CardFragment();
            case 2:
                return new NetBankingFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Wallet";
            case 1:
                return "Card";
            case 2:
                return "Net Banking";
        }

        return null;
    }

}
