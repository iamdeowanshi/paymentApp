package com.batua.android.merchant.module.merchant.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.batua.android.merchant.module.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class MerchantDetailsActivity extends BaseActivity {

    @Bind(com.batua.android.merchant.R.id.toolbar_title) TextView title;
    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_merchant_details);

        setToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.menu_merchant_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.batua.android.merchant.R.id.action_edit:
                startActivity(EditMerchantActivity.class, null);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(com.batua.android.merchant.R.id.view_images)
    void onViewImagesClick() {
        startActivity(GalleryImagesActivity.class, null);
        finish();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        title = (TextView) toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        title.setText("Pizza Hut JP Nagar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
