package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;
import com.batua.android.user.util.ViewUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class PaymentSuccessActivity extends BaseActivity {

    @Inject ViewUtil viewUtil;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.promo_layout) RelativeLayout promoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        injectDependencies();

        setToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payment_status, menu);

        viewUtil.keyboardListener(this, new ViewUtil.KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                togglePromoLayout(isOpen);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_skip) {
            startActivity(HomeActivity.class, null);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_share)
    void onWalletClick() {
        startActivity(HomeActivity.class, null);
        finish();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void togglePromoLayout(boolean isKeyboardVisible) {
        RelativeLayout.LayoutParams promoLayoutLayoutParams = (RelativeLayout.LayoutParams) promoLayout.getLayoutParams();

        if (isKeyboardVisible) {
            promoLayoutLayoutParams.setMargins(0,10,0,0);
            promoLayoutLayoutParams.addRule(RelativeLayout.BELOW, R.id.top_layout);
            promoLayoutLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL,0);
            promoLayout.setLayoutParams(promoLayoutLayoutParams);

            return;
        }

        promoLayoutLayoutParams.setMargins(0,0,0,0);
        promoLayoutLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        promoLayout.setLayoutParams(promoLayoutLayoutParams);
    }

}
