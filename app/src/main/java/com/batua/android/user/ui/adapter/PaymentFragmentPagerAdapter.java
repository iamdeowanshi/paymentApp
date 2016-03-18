package com.batua.android.user.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.batua.android.user.ui.fragment.CardFragment;
import com.batua.android.user.ui.fragment.LoginFragment;
import com.batua.android.user.ui.fragment.NetBankingFragment;
import com.batua.android.user.ui.fragment.SignUpFragment;
import com.batua.android.user.ui.fragment.WalletFragment;
import com.batua.android.user.util.ViewUtil;

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
