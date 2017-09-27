package com.batua.android.merchant.module.merchant.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.batua.android.merchant.data.model.Merchant.Merchant;
import com.batua.android.merchant.data.model.Merchant.MerchantRequest;
import com.batua.android.merchant.module.merchant.view.activity.AddMerchantActivity;
import com.batua.android.merchant.module.merchant.view.activity.EditMerchantActivity;
import com.batua.android.merchant.module.merchant.view.fragment.MerchantBankInfoFragment;
import com.batua.android.merchant.module.merchant.view.fragment.MerchantBasicInfoFragment;
import com.batua.android.merchant.module.merchant.view.fragment.MerchantLocationInfoFragment;

import org.parceler.Parcels;

/**
 * Created by febinp on 02/03/16.
 */
public class MerchantFragmentPagerAdapter extends FragmentPagerAdapter {

    private static int NUMBER_OF_ADD_MERCHANT_FRAGMENT = 3;

    private AppCompatActivity activity;
    private String type;
    private Bundle bundle;

    public MerchantFragmentPagerAdapter(FragmentManager fm, AppCompatActivity activity, String type, Merchant merchant) {
        super(fm);
        this.activity = activity;
        this.type = type;

        bundle = new Bundle();
        bundle.putParcelable("Merchant", Parcels.wrap(merchant));
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

    private MerchantBasicInfoFragment loadMerchantBasicInfoFragment() {
        if (type.equals("Add")) {
            MerchantBasicInfoFragment merchantBasicInfoFragment = new MerchantBasicInfoFragment();
            merchantBasicInfoFragment.setNextClickedListener((AddMerchantActivity) activity);
            merchantBasicInfoFragment.setArguments(bundle);

            return merchantBasicInfoFragment;
        }

        MerchantBasicInfoFragment merchantBasicInfoFragment = new MerchantBasicInfoFragment();
        merchantBasicInfoFragment.setNextClickedListener((EditMerchantActivity) activity);
        merchantBasicInfoFragment.setArguments(bundle);

        return merchantBasicInfoFragment;
    }

    private MerchantLocationInfoFragment loadMerchantLocationInfoFragment() {
        if (type.equals("Add")) {
            MerchantLocationInfoFragment merchantLocationInfoFragment = new MerchantLocationInfoFragment();
            merchantLocationInfoFragment.setNextClickedListener((AddMerchantActivity) activity);
            merchantLocationInfoFragment.setPreviousClickedListener((AddMerchantActivity) activity);
            merchantLocationInfoFragment.setArguments(bundle);

            return merchantLocationInfoFragment;
        }

        MerchantLocationInfoFragment merchantLocationInfoFragment = new MerchantLocationInfoFragment();
        merchantLocationInfoFragment.setNextClickedListener((EditMerchantActivity) activity);
        merchantLocationInfoFragment.setPreviousClickedListener((EditMerchantActivity) activity);
        merchantLocationInfoFragment.setArguments(bundle);

        return merchantLocationInfoFragment;
    }

    private MerchantBankInfoFragment loadMerchantBankInfoFragment() {
        if (type.equals("Add")) {
            MerchantBankInfoFragment merchantBankInfoFragment = new MerchantBankInfoFragment();
            merchantBankInfoFragment.setPreviousClickedListener((AddMerchantActivity) activity);
            merchantBankInfoFragment.setArguments(bundle);

            return merchantBankInfoFragment;
        }

        MerchantBankInfoFragment merchantBankInfoFragment = new MerchantBankInfoFragment();
        merchantBankInfoFragment.setPreviousClickedListener((EditMerchantActivity) activity);
        merchantBankInfoFragment.setArguments(bundle);

        return merchantBankInfoFragment;
    }

}