package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.ui.adapter.LoginFragmentPagerAdpater;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.home_tab_layout) TabLayout loginTabLayout;
    @Bind(R.id.home_viewpager) ViewPager loginViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadFragments();
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
