package com.tecsol.batua.user.module.payment.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.batua.android.user.R;
import com.tecsol.batua.user.module.base.BaseActivity;

import butterknife.Bind;

public class PaymentFailureActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_failure);
        setToolBar();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
