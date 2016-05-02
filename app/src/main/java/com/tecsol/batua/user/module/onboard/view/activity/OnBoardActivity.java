package com.tecsol.batua.user.module.onboard.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.batua.android.user.R;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.onboard.view.fragment.LoginFragment;
import com.tecsol.batua.user.module.onboard.view.fragment.LoginFragmentPagerAdpater;
import com.tecsol.batua.user.module.onboard.view.fragment.SignUpFragment;

import javax.inject.Inject;

import butterknife.Bind;

public class OnBoardActivity extends BaseActivity {

    @Inject ViewUtil viewUtil;

    @Bind(R.id.home_tab_layout) TabLayout onBoardTabLayout;
    @Bind(R.id.home_viewpager) ViewPager onBoardViewpager;
    @Bind(R.id.img_logo) ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Injector.component().inject(this);

        loadFragments();
        setListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            for (Fragment fragment: getSupportFragmentManager().getFragments()) {
                if (fragment.toString().startsWith("L")) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        } else if (requestCode == 1){
            for (Fragment fragment: getSupportFragmentManager().getFragments()) {
                if (fragment.toString().startsWith("S")) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
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
        onBoardViewpager.setAdapter(new LoginFragmentPagerAdpater(getSupportFragmentManager(), this));
        onBoardTabLayout.post(new Runnable() {
            @Override
            public void run() {
                onBoardTabLayout.setupWithViewPager(onBoardViewpager);
            }
        });
    }

    public ViewPager getOnBoardViewpager(){
        return onBoardViewpager;
    }

}
