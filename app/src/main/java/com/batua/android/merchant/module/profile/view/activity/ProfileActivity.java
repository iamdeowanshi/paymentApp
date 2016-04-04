package com.batua.android.merchant.module.profile.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.batua.android.merchant.module.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by febinp on 03/03/16.
 */
public class ProfileActivity extends BaseActivity {

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_profile);

        setToolBar();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(com.batua.android.merchant.R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.batua.android.merchant.R.menu.main_menu, menu);

        menu.findItem(com.batua.android.merchant.R.id.action_save).setVisible(false);
        menu.findItem(com.batua.android.merchant.R.id.action_add_merchant).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case com.batua.android.merchant.R.id.action_edit:
                startActivity(EditProfileActivity.class, null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
