package com.tecsol.batua.user.module.payment.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.batua.android.user.R;
import com.tecsol.batua.user.injection.Injector;
import com.tecsol.batua.user.module.base.BaseActivity;
import com.tecsol.batua.user.module.common.util.Bakery;
import com.tecsol.batua.user.module.common.util.ViewUtil;
import com.tecsol.batua.user.module.dashboard.view.activity.HomeActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class PaymentSuccessActivity extends BaseActivity {

    @Inject
    ViewUtil viewUtil;
    @Inject
    Bakery bakery;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.scroll_view) ScrollView scroll;
    @Bind(R.id.promo_layout) RelativeLayout promoLayout;
    @Bind(R.id.btn_submit) Button submit;
    @Bind(R.id.rating_bar) RatingBar ratingBar;
    @Bind(R.id.edt_review) EditText edtReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        Injector.component().inject(this);

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

    @OnClick(R.id.btn_submit)
    void onWalletClick() {
        if (isValidateReview()) {
            startActivity(HomeActivity.class, null);
            finish();
        }
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean isValidateReview() {
        if (ratingBar.getRating() == 0.0) {
            bakery.toastShort( "Rating can not be zero");

            return false;
        }

        return true;
    }

    private void togglePromoLayout(boolean isKeyboardVisible) {
        RelativeLayout.LayoutParams promoLayoutLayoutParams = (RelativeLayout.LayoutParams) promoLayout.getLayoutParams();

        if (isKeyboardVisible) {
            promoLayoutLayoutParams.setMargins(0,10,0,0);
            promoLayout.setLayoutParams(promoLayoutLayoutParams);

            return;
        }

        promoLayoutLayoutParams.setMargins(0,0,0,0);
        promoLayout.setLayoutParams(promoLayoutLayoutParams);
    }

}
