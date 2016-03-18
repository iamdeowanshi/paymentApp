package com.batua.android.user.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.batua.android.user.ui.fragment.LoginFragment;
import com.batua.android.user.ui.fragment.SignUpFragment;

/**
 * @author Aaditya Deowanshi.
 */
public class LoginFragmentPagerAdpater extends FragmentPagerAdapter {

    private Activity activity;

    public LoginFragmentPagerAdpater(FragmentManager fm, Activity activity) {
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
                return new LoginFragment();
            case 1:
                return new SignUpFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "LogIn";
            case 1:
                return "Sign-Up";
        }

        return null;
    }

}
