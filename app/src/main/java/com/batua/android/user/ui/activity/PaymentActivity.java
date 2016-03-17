package com.batua.android.user.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.ui.adapter.LoginFragmentPagerAdpater;
import com.batua.android.user.ui.adapter.PaymentFragmentPagerAdapter;

import butterknife.Bind;

public class PaymentActivity extends BaseActivity {

    @Bind(R.id.payment_tab_layout) TabLayout paymentTabLayout;
    @Bind(R.id.payment_viewpager) ViewPager paymentViewpager;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.toolbar_title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setToolBar();

        loadFragments();
    }

    private void loadFragments() {
        paymentViewpager.setAdapter(new PaymentFragmentPagerAdapter(getSupportFragmentManager(), this));
        paymentTabLayout.post(new Runnable() {
            @Override
            public void run() {
                paymentTabLayout.setupWithViewPager(paymentViewpager);
            }
        });
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title.setText("Payment");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
