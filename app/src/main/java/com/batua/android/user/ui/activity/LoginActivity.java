package com.batua.android.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.ui.adapter.LoginFragmentPagerAdpater;
import com.batua.android.user.util.ViewUtil;
import com.batua.android.user.util.social.AuthResult;
import com.batua.android.user.util.social.SocialAuth;
import com.batua.android.user.util.social.SocialAuthCallback;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {

    @Inject ViewUtil viewUtil;

    @Bind(R.id.home_tab_layout) TabLayout loginTabLayout;
    @Bind(R.id.home_viewpager) ViewPager loginViewpager;
    @Bind(R.id.img_logo) ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        injectDependencies();

        loadFragments();
        setListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setListeners() {
        viewUtil.keyboardListener(this, new ViewUtil.KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    imgLogo.setVisibility(View.GONE);

                    return;
                }

                imgLogo.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadFragments() {
        loginViewpager.setAdapter(new LoginFragmentPagerAdpater(getSupportFragmentManager(), this));
        loginTabLayout.post(new Runnable() {
            @Override
            public void run() {
                loginTabLayout.setupWithViewPager(loginViewpager);
            }
        });
    }

}
