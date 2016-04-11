package com.batua.android.merchant.module.profile.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.batua.android.merchant.injection.Injector;
import com.batua.android.merchant.module.base.BaseActivity;
import com.batua.android.merchant.module.common.util.ImageUtil;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.io.File;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author Arnold Laishram.
 */
public class EditProfileActivity extends BaseActivity implements ImageUtil.ImageUtilCallback {

    @Inject ImageUtil imageUtil;

    @Bind(com.batua.android.merchant.R.id.toolbar) Toolbar toolbar;
    @Bind(com.batua.android.merchant.R.id.img_profile) CircularImageView imgProfile;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.batua.android.merchant.R.layout.activity_edit_profile);
        Injector.component().inject(this);

        imageUtil.setImageUtilCallback(this);

        setToolBar();
    }

    @OnClick(com.batua.android.merchant.R.id.img_profile)
    void onImageClick() {
        imageUtil.getImage(this);
    }

    private void setToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title = (TextView) toolbar.findViewById(com.batua.android.merchant.R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUtil.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(Uri uri, File file) {
        Glide.with(this).load(uri).into(imgProfile);
    }

}
