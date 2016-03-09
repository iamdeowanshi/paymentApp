package com.batua.android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.batua.android.ui.activity.AddMerchantActivity;
import com.batua.android.ui.activity.EditMerchantActivity;
import com.batua.android.ui.fragment.MerchantBankInfoFragment;
import com.batua.android.ui.fragment.MerchantBasicInfoFragment;
import com.batua.android.ui.fragment.MerchantLocationInfoFragment;

/**
 * Created by febinp on 02/03/16.
 */
public class AddMerchantFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUMBER_OF_ADD_MERCHANT_FRAGMENT = 3;

    private AppCompatActivity activity;
    private String type;
    public AddMerchantFragmentPagerAdapter(FragmentManager fm, AppCompatActivity activity, String type) {
        super(fm);
        this.activity = activity;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return loadMerchantBasicInfoFragment();
            case 1:
                return loadMerchantLocationInfoFragment();
            case 2:
                return loadMerchantBankInfoFragment();
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

    private MerchantBasicInfoFragment loadMerchantBasicInfoFragment(){
        if (type.equals("Add")) {
            MerchantBasicInfoFragment merchantBasicInfoFragment = new MerchantBasicInfoFragment();
            merchantBasicInfoFragment.setNextClickedListener((AddMerchantActivity)activity);

            return merchantBasicInfoFragment;
        }
        MerchantBasicInfoFragment merchantBasicInfoFragment = new MerchantBasicInfoFragment();
        merchantBasicInfoFragment.setNextClickedListener((EditMerchantActivity)activity);

        return merchantBasicInfoFragment;

    }

    private MerchantLocationInfoFragment loadMerchantLocationInfoFragment(){
        if (type.equals("Add")) {
            MerchantLocationInfoFragment merchantLocationInfoFragment = new MerchantLocationInfoFragment();
            merchantLocationInfoFragment.setNextClickedListener((AddMerchantActivity) activity);
            merchantLocationInfoFragment.setPreviousClickedListener((AddMerchantActivity) activity);

            return merchantLocationInfoFragment;
        }
        MerchantLocationInfoFragment merchantLocationInfoFragment = new MerchantLocationInfoFragment();
        merchantLocationInfoFragment.setNextClickedListener((EditMerchantActivity) activity);
        merchantLocationInfoFragment.setPreviousClickedListener((EditMerchantActivity) activity);

        return merchantLocationInfoFragment;
    }

    private MerchantBankInfoFragment loadMerchantBankInfoFragment(){
        if (type.equals("Add")) {
            MerchantBankInfoFragment merchantBankInfoFragment = new MerchantBankInfoFragment();
            merchantBankInfoFragment.setPreviousClickedListener((AddMerchantActivity) activity);

            return merchantBankInfoFragment;
        }
        MerchantBankInfoFragment merchantBankInfoFragment = new MerchantBankInfoFragment();
        merchantBankInfoFragment.setPreviousClickedListener((EditMerchantActivity) activity);

        return merchantBankInfoFragment;
    }

}