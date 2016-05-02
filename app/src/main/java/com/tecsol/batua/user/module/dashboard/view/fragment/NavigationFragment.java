package com.tecsol.batua.user.module.dashboard.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.batua.android.user.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.tecsol.batua.user.data.model.Navigation;
import com.tecsol.batua.user.data.model.User.User;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseFragment;
import com.tecsol.batua.user.module.common.util.PreferenceUtil;
import com.tecsol.batua.user.module.support.activity.ContactUsActivity;
import com.tecsol.batua.user.module.profile.view.activity.ProfileActivity;
import com.tecsol.batua.user.module.transaction.view.activity.TransactionHistoryActivity;
import com.tecsol.batua.user.module.wallet.view.activity.WalletActivity;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class NavigationFragment extends BaseFragment {

    @Inject PreferenceUtil preferenceUtil;

    @Bind(R.id.txt_wallet_amount) TextView txtWalletAmount;
    @Bind(R.id.img_profile) CircularImageView imgProfile;

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

        Injector.component().inject(this);

        User user = (User)preferenceUtil.read(preferenceUtil.USER, User.class);
        if (user!=null){
            Picasso.with(getContext()).load(user.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).into(imgProfile);
        }

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
        shareApp();
    }

    private void shareApp() {
        //TODO:  Custom share message
        String message = "Check my new payment wallet- Batua";

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Batua");
        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(i, "Check new payment app"));
    }

    public void initializeDrawer(DrawerLayout drawer){
        this.drawer = drawer;
    }

}
