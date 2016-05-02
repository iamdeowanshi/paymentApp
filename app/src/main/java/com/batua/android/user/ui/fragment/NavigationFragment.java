package com.batua.android.user.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.data.model.Navigation;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.profile.view.activity.ProfileActivity;
import com.tecsol.batua.user.module.support.activity.ContactUsActivity;
import com.tecsol.batua.user.module.transaction.view.activity.TransactionHistoryActivity;
import com.tecsol.batua.user.module.wallet.view.activity.WalletActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class NavigationFragment extends BaseFragment {

    @Bind(R.id.txt_wallet_amount) TextView txtWalletAmount;

    private DrawerLayout drawer;
    private Navigation navigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigation = new Navigation("\u20B9 2000.00");
        txtWalletAmount.setText(navigation.getWalletAmount());
    }

    @OnClick(R.id.nav_header_layout)
    void navigateToProfile(){
        startActivity(ProfileActivity.class, null);
        drawer.closeDrawers();
    }

    @OnClick(R.id.nav_my_wallet)
    void navigateToMyWallet(){
        startActivity(WalletActivity.class, null );
        drawer.closeDrawers();
    }

    @OnClick(R.id.nav_transaction_history)
    void navigateToTransactionHistory(){
        startActivity(TransactionHistoryActivity.class, null );
        drawer.closeDrawers();
    }

    @OnClick(R.id.nav_contact_us)
    void navigateToContactUs(){
        startActivity(ContactUsActivity.class, null );
        drawer.closeDrawers();
    }

    @OnClick(R.id.nav_rate_app)
    void navigateToRateTheApp(){

    }

    @OnClick(R.id.nav_refer_app)
    void navigateToReferTheApp(){

    }

    public void initializeDrawer(DrawerLayout drawer){
        this.drawer = drawer;
    }

}
