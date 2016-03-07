package com.batua.android.app.di;

import com.batua.android.ui.fragment.MerchantBankInfoFragment;
import com.batua.android.ui.fragment.MerchantBasicInfoFragment;
import com.batua.android.ui.fragment.MerchantLocationInfoFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Arnold Laishram.
 */

@Module(
        complete = false,
        library = true
)

public class FragmentModule {

    @Singleton
    @Provides
    public MerchantBasicInfoFragment provideMerchantBasicInfoFragment(){
        return new MerchantBasicInfoFragment();
    }

    @Singleton
    @Provides
    public MerchantLocationInfoFragment provideMerchantLocationInfoFragment(){
        return new MerchantLocationInfoFragment();
    }

    @Singleton
    @Provides
    public MerchantBankInfoFragment provideMerchantBankInfoFragment(){
        return new MerchantBankInfoFragment();
    }
}
