package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.ui.adapter.LoginFragmentPagerAdpater;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.home_tab_layout) TabLayout loginTabLayout;
    @Bind(R.id.home_viewpager) ViewPager loginViewpager;
    @Bind(R.id.img_logo) ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadFragments();

        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
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
