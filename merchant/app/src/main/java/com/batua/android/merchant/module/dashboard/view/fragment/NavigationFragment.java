package com.batua.android.merchant.module.dashboard.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.batua.android.merchant.R;
import com.batua.android.merchant.data.model.Merchant.User;
import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseFragment;
import com.batua.android.merchant.module.common.util.InternetUtil;
import com.batua.android.merchant.module.common.util.PreferenceUtil;
import com.batua.android.merchant.module.dashboard.presenter.LogoutPresenter;
import com.batua.android.merchant.module.dashboard.presenter.LogoutViewInteractor;
import com.batua.android.merchant.module.dashboard.view.activity.HomeActivity;
import com.batua.android.merchant.module.onboard.view.activity.LoginActivity;
import com.batua.android.merchant.module.profile.view.activity.ProfileActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class NavigationFragment extends BaseFragment implements LogoutViewInteractor {

    @Bind(R.id.txt_display_name) TextView txtDisplayName;
    @Bind(R.id.img_profile) ImageView imageDp;

    @Inject LogoutPresenter logoutPresenter;
    @Inject PreferenceUtil preferenceUtil;

    private DrawerLayout drawer;
    private String deviceId;
    private User user;

    @OnClick(R.id.logout)
    void logOut(){
        if (!InternetUtil.hasInternetConnection(getActivity())){
            ((HomeActivity)getActivity()).showNoInternetTitleDialog(getActivity());
        } else{
            logoutPresenter.logout(deviceId, user.getId());
            drawer.closeDrawers();
        }
    }

    @OnClick(R.id.profile_bg_relative_layout)
    void navigateToProfile(){
        startActivity(ProfileActivity.class, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_header_main, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Injector.component().inject(this);
        logoutPresenter.attachViewInteractor(this);

        user = (User) preferenceUtil.read(preferenceUtil.USER, User.class);
        deviceId = preferenceUtil.readString(preferenceUtil.DEVICE_ID, "");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                initializeProfile();
            }
        });
        //initializeProfile();
    }

    @Override
    public void onLogout() {
        preferenceUtil.remove(preferenceUtil.USER);
        ((HomeActivity)getActivity()).startActivityClearTop(LoginActivity.class, null);
    }

    public void initializeDrawer(DrawerLayout drawer){
        this.drawer = drawer;
    }

    private void initializeProfile(){
        if (user!=null) {
            Picasso.with(getActivity()).load(user.getProfileImageUrl()).placeholder(R.drawable.profile_pic_container).into(imageDp);
            txtDisplayName.setText(user.getName());
        }
    }

}
