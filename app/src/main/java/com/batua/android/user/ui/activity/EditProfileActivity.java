package com.batua.android.user.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.batua.android.user.R;
import com.batua.android.user.app.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class EditProfileActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setToolBar();
    }

    @OnClick(R.id.btn_save_profile)
    void saveProfile() {
        startActivity(ProfileActivity.class, null);
        finish();
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
