package com.batua.android.merchant.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.batua.android.merchant.ui.fragment.MonthlyLeaderBoardFragment;
import com.batua.android.merchant.ui.fragment.WeeklyLeaderBoardFragment;

/**
 * @author Arnold Laishram.
 */
public class LeaderBoardFragmentPagerAdapter extends FragmentPagerAdapter {

    public LeaderBoardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private static int NUMBER_OF_LEADER_BOARD_FRAGMENT = 2;

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WeeklyLeaderBoardFragment();
            case 1:
                return new MonthlyLeaderBoardFragment();
        }
        return null;
    }

    @Override
    public int getCount() {

        return NUMBER_OF_LEADER_BOARD_FRAGMENT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Weekly";
            case 1:
                return "Monthly";
        }
        return null;
    }
}
